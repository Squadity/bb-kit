package net.bolbat.kit.orchestrator.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import net.bolbat.kit.config.ConfigurationListener;
import net.bolbat.kit.orchestrator.OrchestrationConfig;
import net.bolbat.kit.orchestrator.OrchestrationConfig.ExecutorConfig;
import net.bolbat.kit.orchestrator.OrchestrationConfig.LimitsConfig;
import net.bolbat.kit.orchestrator.annotation.Orchestrate;
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

	private String id;

	private String name;

	private Source source = Source.CLASS;

	private State state = State.NOT_ORCHESTRATED;

	private ExecutionInfo classInfo;

	private Source limitsSource = Source.CLASS;

	private Source executorSource = Source.CLASS;

	private OrchestrationConfig config;

	private final transient AtomicInteger executions = new AtomicInteger(0);

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

	public Source getSource() {
		return source;
	}

	public void setSource(final Source aSource) {
		this.source = aSource;
	}

	public State getState() {
		return state;
	}

	public void setState(final State aState) {
		this.state = aState;
	}

	public ExecutionInfo getClassInfo() {
		return classInfo;
	}

	public void setClassInfo(final ExecutionInfo aClassInfo) {
		this.classInfo = aClassInfo;
	}

	public Source getLimitsSource() {
		return limitsSource;
	}

	public void setLimitsSource(final Source aLimitsSource) {
		this.limitsSource = aLimitsSource;
	}

	public Source getExecutorSource() {
		return executorSource;
	}

	public void setExecutorSource(final Source aExecutorSource) {
		this.executorSource = aExecutorSource;
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

	public boolean isOrchestrated() {
		if (source == Source.METHOD && State.NO_CONFIGURATION == state)
			return classInfo.isOrchestrated();

		return State.ORCHESTRATED == state;
	}

	public OrchestrationConfig getActualConfig() {
		if (source == Source.METHOD && limitsSource == Source.CLASS)
			return classInfo.getConfig();

		return config;
	}

	public LimitsConfig getActualLimitsConfig() {
		if (source == Source.METHOD && limitsSource == Source.CLASS)
			return classInfo.getConfig().getLimitsConfig();

		return config.getLimitsConfig();
	}

	public ExecutorConfig getActualExecutorConfig() {
		if (source == Source.METHOD && executorSource == Source.CLASS)
			return classInfo.getConfig().getExecutorConfig();

		return config.getExecutorConfig();
	}

	public String getActualExecutorId() {
		if (source == Source.METHOD && executorSource == Source.CLASS)
			return classInfo.getId();

		return id;
	}

	public String getActualExecutorName() {
		if (source == Source.METHOD && executorSource == Source.CLASS)
			return classInfo.getName();

		return name;
	}

	/**
	 * Get actual {@link ExecutorService} instance.
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

	public AtomicInteger getActualExecutions() {
		if (source == Source.METHOD && executorSource == Source.CLASS)
			return classInfo.getExecutions();

		return executions;
	}

	public void registerForConfigurationChanges() {
		if (config != null && config.getSource() == OrchestrationConfig.Source.CONFIGURE_ME)
			config.registerListener(this);
	}

	public void unregisterFromConfigurationChanges() {
		if (config != null && config.getSource() == OrchestrationConfig.Source.CONFIGURE_ME)
			config.unregisterListener(this);
	}

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

	/**
	 * Info sources.
	 * 
	 * @author Alexandr Bolbat
	 */
	public enum Source {

		/**
		 * Configured from class.
		 */
		CLASS,

		/**
		 * Configured from method.
		 */
		METHOD;

	}

	/**
	 * Info status.
	 * 
	 * @author Alexandr Bolbat
	 */
	public enum State {

		/**
		 * Orchestration is enabled.
		 */
		ORCHESTRATED,

		/**
		 * Orchestration is disabled.
		 */
		NOT_ORCHESTRATED,

		/**
		 * {@link Orchestrate} annotation is not present.
		 */
		NO_CONFIGURATION;

	}

}
