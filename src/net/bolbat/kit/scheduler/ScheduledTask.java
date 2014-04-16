package net.bolbat.kit.scheduler;

import java.util.Map;

/**
 * Scheduled task.
 *
 * @author ivanbatura
 */
public interface ScheduledTask {
	/**
	 * Get scheduled job class.
	 *
	 * @return {@link java.lang.Class} that extends {@link ScheduledJob}
	 */
	Class<? extends ScheduledJob> getJobClass();

	/**
	 * Get parameters.
	 *
	 * @return {@link Map} with various parameters.
	 */
	Map<String, Object> getParameters();

	/**
	 * File config name.
	 *
	 * @return file config name
	 */
	String getConfig();

	/**
	 * Get config type.
	 *
	 * @return {@link SchedulerConfigurationType }
	 */
	SchedulerConfigurationType getConfigType();
}
