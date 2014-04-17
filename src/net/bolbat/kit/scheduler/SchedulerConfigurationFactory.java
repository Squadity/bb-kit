package net.bolbat.kit.scheduler;

import java.util.Properties;
import java.util.UUID;

import net.bolbat.utils.logging.LoggingUtils;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration factory for quartz.
 *
 * @author ivanbatura
 */
public class SchedulerConfigurationFactory {
	/**
	 * {@link org.slf4j.Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerConfigurationFactory.class);

	/**
	 * Parameter for scheduler - instance name.
	 */
	private static final String PARAM_SCHEDULER_INSTANCE_NAME = "org.quartz.scheduler.instanceName";

	/**
	 * Parameter for scheduler - instance id generation .
	 */
	private static final String PARAM_SCHEDULER_INSTANCE_ID = "org.quartz.scheduler.instanceId";

	/**
	 * Parameter for scheduler - skip update check.
	 */
	private static final String PARAM_SCHEDULER_SKIP_UPDATE_CHECK = "org.quartz.scheduler.skipUpdateCheck";

	/**
	 * Parameter for scheduler - thread pool class.
	 */
	private static final String PARAM_THREAD_POOL_CLASS = "org.quartz.threadPool.class";

	/**
	 * Parameter for scheduler - thread pool tread count.
	 */
	private static final String PARAM_THREAD_POOL_TREAD_COUNT = "org.quartz.threadPool.threadCount";

	/**
	 * Parameter for scheduler - job store class.
	 */
	private static final String PARAM_THREAD_JOB_STORE_CLASS = "org.quartz.jobStore.class";

	/**
	 * Get default quartz configuration.
	 *
	 * @return {@link Properties} with configuration
	 */
	public static Scheduler getConfiguration(TaskConfiguration task) throws SchedulerException {
		if (task == null)
			throw new IllegalArgumentException("Parameter task is null");
		try {
			//get defaults
			if (task.getConfigType() == null)
				return new StdSchedulerFactory(getConfigureMeConfiguration(null)).getScheduler();

			switch (task.getConfigType()) {
				case CONFIGURE_ME:
					return new StdSchedulerFactory(getConfigureMeConfiguration(task.getConfig())).getScheduler();
				case PROPERTY:
					return new StdSchedulerFactory(task.getConfig()).getScheduler();
				default:
					return new StdSchedulerFactory(getConfigureMeConfiguration(task.getConfig())).getScheduler();
			}
		} catch (org.quartz.SchedulerException e) {
			String message = "getConfiguration(...) scheduler initialization fail.";
			LOGGER.error(LoggingUtils.FATAL, message, e);
			throw new SchedulerException(message, e);
		}
	}

	/**
	 * Get t quartz configuration.
	 *
	 * @param fileName
	 * 		configure me config
	 * 		can be NULL,  then default will be used
	 * @return {@link Properties} with configuration
	 */
	private static Properties getConfigureMeConfiguration(String fileName) {
		Properties properties = new Properties();
		SchedulerConfiguration config = SchedulerConfiguration.getInstance(fileName);
		properties.put(PARAM_SCHEDULER_INSTANCE_NAME, UUID.randomUUID());
		properties.put(PARAM_SCHEDULER_INSTANCE_ID, config.getSchedulerInstanceId());
		properties.put(PARAM_SCHEDULER_SKIP_UPDATE_CHECK, String.valueOf(config.isSchedulerSkipUpdateCheck()));
		properties.put(PARAM_THREAD_POOL_CLASS, config.getThreadPoolClass());
		properties.put(PARAM_THREAD_POOL_TREAD_COUNT, String.valueOf(config.getThreadPoolCount()));
		properties.put(PARAM_THREAD_JOB_STORE_CLASS, config.getThreadJobStoreClass());
		return properties;
	}
}
