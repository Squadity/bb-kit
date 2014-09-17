package net.bolbat.kit.scheduler;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.bolbat.utils.lang.StringUtils;
import org.configureme.ConfigurationManager;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;
import org.configureme.annotations.DontConfigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Scheduled configuration.
 *
 * @author ivanbatura
 */
@ConfigureMe
public class SchedulerConfiguration implements Serializable {

	/**
	 * Generated SerialVersionUID.
	 */
	@DontConfigure
	private static final long serialVersionUID = 2279985778539670636L;

	/**
	 * {@link Logger} instance.
	 */
	@DontConfigure
	private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerConfiguration.class);

	/**
	 * Configurations cache.
	 */
	@DontConfigure
	private static final Map<String, SchedulerConfiguration> CACHE = new HashMap<String, SchedulerConfiguration>();

	/**
	 * Instance creation lock.
	 */
	@DontConfigure
	private static final Object CACHE_LOCK = new Object();

	/**
	 * Default scheduler instance id.
	 */
	@DontConfigure
	private static final String DEFAULT_SCHEDULER_INSTANCE_ID = "AUTO";

	/**
	 * Default scheduler skip update check.
	 */
	@DontConfigure
	private static final boolean DEFAULT_SCHEDULER_SKIP_UPDATE_CHECK = true;

	/**
	 * Default thread pool class.
	 */
	@DontConfigure
	private static final String DEFAULT_THREAD_POOL_CLASS = "org.quartz.simpl.SimpleThreadPool";

	/**
	 * Default thread pool count.
	 */
	@DontConfigure
	private static final int DEFAULT_THREAD_POOL_TREAD_COUNT = 1;

	/**
	 * Default thread pool count.
	 */
	@DontConfigure
	private static final String DEFAULT_THREAD_JOB_STORE_CLASS = "org.quartz.simpl.RAMJobStore";

	/**
	 * Scheduler instance name.
	 * Default NULL.
	 */
	@Configure
	private String schedulerInstanceName = null;

	/**
	 * Scheduler instance id.
	 */
	@Configure
	private String schedulerInstanceId = DEFAULT_SCHEDULER_INSTANCE_ID;

	/**
	 * Scheduler skip update check.
	 */
	@Configure
	private boolean schedulerSkipUpdateCheck = DEFAULT_SCHEDULER_SKIP_UPDATE_CHECK;

	/**
	 * Thread pool class name.
	 */
	@Configure
	private String threadPoolClass = DEFAULT_THREAD_POOL_CLASS;

	/**
	 * Thread pool amount.
	 */
	@Configure
	private int threadPoolCount = DEFAULT_THREAD_POOL_TREAD_COUNT;

	/**
	 * Thread job store class name.
	 */
	@Configure
	private String threadJobStoreClass = DEFAULT_THREAD_JOB_STORE_CLASS;

	/**
	 * Default constructor.
	 */
	private SchedulerConfiguration(final String configurationName) {
		try {
			if (StringUtils.isEmpty(configurationName))
				ConfigurationManager.INSTANCE.configure(this);
			else
				ConfigurationManager.INSTANCE.configureBeanAs(this, configurationName);
			// CHECKSTYLE:OFF
		} catch (final RuntimeException e) {
			// CHECKSTYLE:ON
			LOGGER.warn("Configuration fail[" + e.getMessage() + "]. Relaying on defaults.");
		}

		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Configured with[" + this.toString() + "]");
	}

	/**
	 * Get configuration instance.
	 *
	 * @param configuration
	 * 		configuration name, can't be empty
	 * @return {@link SchedulerConfiguration} instance
	 */
	public static SchedulerConfiguration getInstance(final String configuration) {
		final String cacheKey = String.valueOf(configuration);
		final SchedulerConfiguration cached = CACHE.get(cacheKey);
		if (cached != null)
			return cached;

		synchronized (CACHE_LOCK) {
			final SchedulerConfiguration secondCheck = CACHE.get(cacheKey);
			if (secondCheck != null)
				return secondCheck;

			final SchedulerConfiguration config = new SchedulerConfiguration(configuration);
			CACHE.put(cacheKey, config);
			return config;
		}
	}

	public String getSchedulerInstanceName() {
		return schedulerInstanceName;
	}

	public void setSchedulerInstanceName(String schedulerInstanceName) {
		this.schedulerInstanceName = schedulerInstanceName;
	}

	public String getSchedulerInstanceId() {
		return schedulerInstanceId;
	}

	public void setSchedulerInstanceId(String schedulerInstanceId) {
		this.schedulerInstanceId = schedulerInstanceId;
	}

	public boolean isSchedulerSkipUpdateCheck() {
		return schedulerSkipUpdateCheck;
	}

	public void setSchedulerSkipUpdateCheck(boolean schedulerSkipUpdateCheck) {
		this.schedulerSkipUpdateCheck = schedulerSkipUpdateCheck;
	}

	public String getThreadPoolClass() {
		return threadPoolClass;
	}

	public void setThreadPoolClass(String threadPoolClass) {
		this.threadPoolClass = threadPoolClass;
	}

	public int getThreadPoolCount() {
		return threadPoolCount;
	}

	public void setThreadPoolCount(int threadPoolCount) {
		this.threadPoolCount = threadPoolCount;
	}

	public String getThreadJobStoreClass() {
		return threadJobStoreClass;
	}

	public void setThreadJobStoreClass(String threadJobStoreClass) {
		this.threadJobStoreClass = threadJobStoreClass;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append("[schedulerInstanceName=").append(schedulerInstanceName);
		sb.append(", schedulerInstanceId=").append(schedulerInstanceId);
		sb.append(", schedulerSkipUpdateCheck=").append(schedulerSkipUpdateCheck);
		sb.append(", threadPoolClass=").append(threadPoolClass);
		sb.append(", threadPoolCount=").append(threadPoolCount);
		sb.append(", threadJobStoreClass=").append(threadJobStoreClass);
		sb.append(']');
		return sb.toString();
	}
}
