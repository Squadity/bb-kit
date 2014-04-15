package net.bolbat.kit.scheduledqueue;

import java.io.Serializable;

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
@ConfigureMe (name = "scheduled-configuration")
public class ScheduledConfiguration implements Serializable {

	/**
	 * Generated SerialVersionUID.
	 */
	@DontConfigure
	private static final long serialVersionUID = 2279985778539670636L;

	/**
	 * {@link org.slf4j.Logger} instance.
	 */
	@DontConfigure
	public static final Logger LOGGER = LoggerFactory.getLogger(ScheduledConfiguration.class);

	/**
	 * Instance creation lock.
	 */
	@DontConfigure
	private static final Object LOCK = new Object();

	/**
	 * Default scheduler instance id.
	 */
	@DontConfigure
	public static final String DEFAULT_SCHEDULER_INSTANCE_ID = "AUTO";

	/**
	 * Default scheduler skip update check.
	 */
	@DontConfigure
	public static final boolean DEFAULT_SCHEDULER_SKIP_UPDATE_CHECK = true;

	/**
	 * Default thread pool class.
	 */
	@DontConfigure
	public static final String DEFAULT_THREAD_POOL_CLASS = "org.quartz.simpl.SimpleThreadPool";

	/**
	 * Default thread pool count.
	 */
	@DontConfigure
	public static final int DEFAULT_THREAD_POOL_TREAD_COUNT = 1;

	/**
	 * Default thread pool count.
	 */
	@DontConfigure
	public static final String DEFAULT_THREAD_JOB_STORE_CLASS = "org.quartz.simpl.RAMJobStore";

	/**
	 * Configurations cache.
	 */
	@DontConfigure
	private static volatile ScheduledConfiguration instance;

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
	private ScheduledConfiguration() {
		try {
			ConfigurationManager.INSTANCE.configure(this);
			// CHECKSTYLE:OFF
		} catch (final RuntimeException e) {
			// CHECKSTYLE:ON
			LOGGER.warn("Configuration fail[" + e.getMessage() + "]. Relaying on defaults.");
		}

		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Configured with[" + this.toString() + "]");
	}

	/**
	 * Get configuration instance for given environment.
	 *
	 * @return {@link ScheduledConfiguration} instance
	 */
	public static ScheduledConfiguration getInstance() {
		if (instance == null)
			synchronized (LOCK) {
				if (instance == null)
					instance = new ScheduledConfiguration();
			}
		return instance;
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
