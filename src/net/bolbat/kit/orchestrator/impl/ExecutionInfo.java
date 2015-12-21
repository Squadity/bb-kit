package net.bolbat.kit.orchestrator.impl;

import static net.bolbat.utils.lang.StringUtils.isNotEmpty;
import static net.bolbat.utils.lang.Validations.checkArgument;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import net.bolbat.kit.config.ConfigurationListener;
import net.bolbat.kit.orchestrator.OrchestrationConfig;
import net.bolbat.kit.orchestrator.OrchestrationConfig.Source;
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
	 * Execution identifier.
	 */
	private final String id;

	/**
	 * Execution name.
	 */
	private final String name;

	/**
	 * {@link OrchestrationConfig} instance.
	 */
	private final OrchestrationConfig config;

	/**
	 * Currently running concurrent executions.
	 */
	private final AtomicInteger currentExecutions = new AtomicInteger();

	/**
	 * Default constructor.
	 * 
	 * @param aId
	 *            execution identifier
	 * @param aConfig
	 *            {@link OrchestrationConfig} instance
	 */
	public ExecutionInfo(final String aId, final String aName, final OrchestrationConfig aConfig) {
		checkArgument(isNotEmpty(aId), "aId argument is empty");
		checkArgument(aConfig != null, "aConfig argument is null");

		this.id = aId;
		this.name = aName;
		this.config = aConfig;

		registerToConfiguration();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public OrchestrationConfig getConfig() {
		return config;
	}

	public AtomicInteger getCurrentExecutions() {
		return currentExecutions;
	}

	/**
	 * Get {@link ExecutorService} instance.
	 * 
	 * @return {@link ExecutorService}
	 */
	public ExecutorService getExecutor() {
		ExecutorService service = ExecutionCaches.getExecutor(id);
		if (service != null)
			return service;

		final IdBasedLock<String> lock = LOCK_MANAGER.obtainLock(id);
		lock.lock();
		try {
			service = ExecutionCaches.getExecutor(id);
			if (service == null)
				service = initExecutor();
		} finally {
			lock.unlock();
		}

		return service;
	}

	/**
	 * Operation what should be executed if configuration changed.
	 */
	@Override
	public void configurationChanged() {
		initExecutor();
	}

	/**
	 * Initialize new {@link ExecutorService} instance and shutdown old one.
	 * 
	 * @return {@link ExecutorService}
	 */
	private ExecutorService initExecutor() {
		final IdBasedLock<String> lock = LOCK_MANAGER.obtainLock(id);
		lock.lock();
		try {
			final ExecutorService newService = ExecutionUtils.create(config, id, name);

			// closing old service instance
			final ExecutorService oldService = ExecutionCaches.cacheExecutor(id, newService);
			if (oldService != null)
				ExecutionUtils.shutdown(oldService, false, 0, TimeUnit.MILLISECONDS);

			return newService;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Register to configuration change listening.
	 */
	public void registerToConfiguration() {
		if (config.getSource() == Source.CONFIGURE_ME)
			config.unregisterListener(this);
	}

	/**
	 * Unregister from configuration change listening.
	 */
	public void unregisterFromConfiguration() {
		if (config.getSource() == Source.CONFIGURE_ME)
			config.unregisterListener(this);
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

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [id=").append(id);
		builder.append(", name=").append(name);
		builder.append(", config=").append(config);
		builder.append("]");
		return builder.toString();
	}

}
