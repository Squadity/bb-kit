package net.bolbat.kit.orchestrator.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import net.bolbat.kit.config.ConfigurationListener;
import net.bolbat.kit.orchestrator.OrchestrationConfig;
import net.bolbat.kit.orchestrator.OrchestrationConfig.ExecutorConfig;
import net.bolbat.kit.orchestrator.OrchestrationConfig.LimitsConfig;
import net.bolbat.utils.concurrency.lock.IdBasedLock;
import net.bolbat.utils.concurrency.lock.IdBasedLockManager;
import net.bolbat.utils.concurrency.lock.SafeIdBasedLockManager;

/**
 * Execution runtime information.
 * 
 * @author Alexandr Bolbat
 */
public class ExecutionInfo implements ConfigurationListener {

	/**
	 * {@link IdBasedLockManager} instance.
	 */
	private static final IdBasedLockManager<String> LOCK_MANAGER = new SafeIdBasedLockManager<>();

	/**
	 * Execution unique identifier.
	 */
	private String id;

	/**
	 * Execution name.
	 */
	private String name;

	/**
	 * Class {@link ExecutionInfo}, not <code>null</code> for {@link ExecutionInfo} based on method scope.
	 */
	private ExecutionInfo classInfo;

	/**
	 * Disable orchestration.
	 */
	private boolean disabled = true;

	/**
	 * Execution should work in own isolated scope.
	 */
	private boolean ownScope = false;

	/**
	 * Execution should use own limits configuration.
	 */
	private boolean ownLimits = false;

	/**
	 * Execution should use own executor configuration.
	 */
	private boolean ownExecutor = false;

	/**
	 * Execution {@link OrchestrationConfig}, <code>null</code> if no any orchestration configuration.
	 */
	private OrchestrationConfig config;

	/**
	 * Current executions amount.
	 */
	private final AtomicInteger executions = new AtomicInteger(0);

	/**
	 * Actual configuration: is orchestration enabled.
	 */
	private transient boolean isOrchestrated;

	/**
	 * Actual configuration: {@link OrchestrationConfig}.
	 */
	private transient OrchestrationConfig actualConfig;

	/**
	 * Actual configuration: {@link LimitsConfig}.
	 */
	private transient LimitsConfig actualLimitsConfig;

	/**
	 * Actual configuration: {@link ExecutorConfig}.
	 */
	private transient ExecutorConfig actualExecutorConfig;

	/**
	 * Actual configuration: executor identifier.
	 */
	private transient String actualExecutorId;

	/**
	 * Actual configuration: executor name.
	 */
	private transient String actualExecutorName;

	/**
	 * Actual configuration: current executions.
	 */
	private transient AtomicInteger actualExecutions;

	public String getId() {
		return id;
	}

	public void setId(final String aId) {
		this.id = aId;
	}

	public String getName() {
		return name;
	}

	public void setName(final String aName) {
		this.name = aName;
	}

	public ExecutionInfo getClassInfo() {
		return classInfo;
	}

	public void setClassInfo(final ExecutionInfo aClassInfo) {
		this.classInfo = aClassInfo;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(final boolean aDisabled) {
		this.disabled = aDisabled;
	}

	public boolean isOwnScope() {
		return ownScope;
	}

	public void setOwnScope(final boolean aOwnScope) {
		this.ownScope = aOwnScope;
	}

	public boolean isOwnLimits() {
		return ownLimits;
	}

	public void setOwnLimits(final boolean aOwnLimits) {
		this.ownLimits = aOwnLimits;
	}

	public boolean isOwnExecutor() {
		return ownExecutor;
	}

	public void setOwnExecutor(final boolean aOwnExecutor) {
		this.ownExecutor = aOwnExecutor;
	}

	public OrchestrationConfig getConfig() {
		return config;
	}

	public void setConfig(final OrchestrationConfig aConfig) {
		this.config = aConfig;
	}

	public AtomicInteger getExecutions() {
		return executions;
	}

	/**
	 * Is orchestration enabled for current {@link ExecutionInfo}.
	 * 
	 * @return <code>true</code> if orchestrated or <code>false</code>
	 */
	public boolean isOrchestrated() {
		return isOrchestrated;
	}

	/**
	 * Get 'actual' {@link OrchestrationConfig}.<br>
	 * Based on current method and class configuration including annotation overriding rules.
	 * 
	 * @return {@link OrchestrationConfig}
	 */
	public OrchestrationConfig getActualConfig() {
		return actualConfig;
	}

	/**
	 * Get 'actual' {@link LimitsConfig}.<br>
	 * Based on current method and class configuration including annotation overriding rules.
	 * 
	 * @return {@link LimitsConfig}
	 */
	public LimitsConfig getActualLimitsConfig() {
		return actualLimitsConfig;
	}

	/**
	 * Get 'actual' {@link ExecutorConfig}.<br>
	 * Based on current method and class configuration including annotation overriding rules.
	 * 
	 * @return {@link ExecutorConfig}
	 */
	public ExecutorConfig getActualExecutorConfig() {
		return actualExecutorConfig;
	}

	/**
	 * Get 'actual' executor identifier (the same as execution identifier).<br>
	 * Based on current method and class configuration including annotation overriding rules.
	 * 
	 * @return {@link String}
	 */
	public String getActualExecutorId() {
		return actualExecutorId;
	}

	/**
	 * Get 'actual' executor name.<br>
	 * Based on current method and class configuration including annotation overriding rules.
	 * 
	 * @return {@link String}
	 */
	public String getActualExecutorName() {
		return actualExecutorName;
	}

	/**
	 * Get 'actual' executions amount.<br>
	 * Based on current method and class configuration including annotation overriding rules.
	 * 
	 * @return {@link AtomicInteger}
	 */
	public AtomicInteger getActualExecutions() {
		return actualExecutions;
	}

	/**
	 * Get 'actual' {@link ExecutorService} instance.<br>
	 * Based on current method and class configuration including annotation overriding rules.
	 * 
	 * @return {@link ExecutorService}
	 */
	public ExecutorService getActualExecutor() {
		final String actualExecutorId = getActualExecutorId();
		ExecutorService service = ExecutionCaches.getExecutor(actualExecutorId);
		if (service != null)
			return service;

		final IdBasedLock<String> lock = LOCK_MANAGER.obtainLock(actualExecutorId);
		lock.lock();
		try {
			service = ExecutionCaches.getExecutor(actualExecutorId);
			if (service != null) // double check
				return service;

			service = ExecutionUtils.create(getActualExecutorConfig(), getActualExecutorId(), getActualExecutorName());
			ExecutionCaches.cacheExecutor(actualExecutorId, service);
			return service;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Initialize actual configuration.
	 */
	public void initActualConfiguration() {
		isOrchestrated = !disabled;
		isOrchestrated = isOrchestrated && (ownScope || (classInfo != null && classInfo.isOrchestrated()));
		actualConfig = ownScope ? config : classInfo.getConfig();
		actualLimitsConfig = ownScope || ownLimits ? config.getLimitsConfig() : classInfo.getConfig().getLimitsConfig();
		actualExecutorConfig = ownScope || ownExecutor ? config.getExecutorConfig() : classInfo.getConfig().getExecutorConfig();
		actualExecutorId = ownScope || ownExecutor ? id : classInfo.getId();
		actualExecutorName = ownScope || ownExecutor ? name : classInfo.getName();
		actualExecutions = ownScope || ownExecutor ? executions : classInfo.getExecutions();

		registerForConfigurationChanges();
	}

	/**
	 * Register for listening configuration changes.
	 */
	public void registerForConfigurationChanges() {
		if (config != null && config.getSource() == OrchestrationConfig.Source.CONFIGURE_ME)
			config.registerListener(this);
	}

	/**
	 * Unregister from listening configuration changes.
	 */
	public void unregisterFromConfigurationChanges() {
		if (config != null && config.getSource() == OrchestrationConfig.Source.CONFIGURE_ME)
			config.unregisterListener(this);
	}

	/**
	 * Action what should be executed if configuration is changed.
	 */
	@Override
	public void configurationChanged() {
		ExecutionCaches.shutdownExecutor(getActualExecutorId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ExecutionInfo))
			return false;
		final ExecutionInfo other = (ExecutionInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
