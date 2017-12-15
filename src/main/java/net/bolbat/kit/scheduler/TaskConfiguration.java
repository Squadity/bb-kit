package net.bolbat.kit.scheduler;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Scheduled task configuration.
 * NOTE: If TaskConfiguration is persistable all its persistable fields should implement {@link java.io.Serializable}.
 *
 * @author ivanbatura
 */
@JsonTypeInfo (use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface TaskConfiguration extends Serializable {
	/**
	 * Get scheduled job class.
	 *
	 * @return {@link java.lang.Class} that extends {@link Task}
	 */
	Class<? extends Task> getJobClass();

	/**
	 * Get parameters.
	 *
	 * @return {@link Map} with various parameters.
	 */
	TaskParameters getParameters();

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
