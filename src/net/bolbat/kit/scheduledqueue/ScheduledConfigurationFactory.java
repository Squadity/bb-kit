package net.bolbat.kit.scheduledqueue;

import java.util.Properties;
import java.util.UUID;

/**
 * Configuration factory for quartz.
 *
 * @author ivanbatura
 */
public class ScheduledConfigurationFactory {
	/**
	 * Parameter for scheduler - instance name.
	 */
	public static final String PARAM_SCHEDULER_INSTANCE_NAME = "org.quartz.scheduler.instanceName";

	/**
	 * Parameter for scheduler - instance id generation .
	 */
	public static final String PARAM_SCHEDULER_INSTANCE_ID = "org.quartz.scheduler.instanceId";

	/**
	 * Parameter for scheduler - skip update check.
	 */
	public static final String PARAM_SCHEDULER_SKIP_UPDATE_CHECK = "org.quartz.scheduler.skipUpdateCheck";

	/**
	 * Parameter for scheduler - thread pool class.
	 */
	public static final String PARAM_THREAD_POOL_CLASS = "org.quartz.threadPool.class";

	/**
	 * Parameter for scheduler - thread pool tread count.
	 */
	public static final String PARAM_THREAD_POOL_TREAD_COUNT = "org.quartz.threadPool.threadCount";

	/**
	 * Parameter for scheduler - job store class.
	 */
	public static final String PARAM_THREAD_JOB_STORE_CLASS = "org.quartz.jobStore.class";

	/**
	 * Get default quartz configuration.
	 *
	 * @return {@link Properties} with configuration
	 */
	public static Properties getConfiguration() {
		ScheduledConfiguration config = ScheduledConfiguration.getInstance();
		Properties properties = new Properties();
		properties.put(PARAM_SCHEDULER_INSTANCE_NAME, config.getSchedulerInstanceName() != null ? (config.getSchedulerInstanceName() + "_" + System.currentTimeMillis()) : UUID.randomUUID());
		properties.put(PARAM_SCHEDULER_INSTANCE_ID, config.getSchedulerInstanceId());
		properties.put(PARAM_SCHEDULER_SKIP_UPDATE_CHECK, String.valueOf(config.isSchedulerSkipUpdateCheck()));
		properties.put(PARAM_THREAD_POOL_CLASS, config.getThreadPoolClass());
		properties.put(PARAM_THREAD_POOL_TREAD_COUNT, String.valueOf(config.getThreadPoolCount()));
		properties.put(PARAM_THREAD_JOB_STORE_CLASS, config.getThreadJobStoreClass());
		return properties;
	}
}
