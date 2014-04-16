package net.bolbat.kit.scheduler;

/**
 * Type for schedule configuration.
 *
 * @author ivanbatura
 */
public enum SchedulerConfigurationType {
	/**
	 * JSON configure me  file configuration.
	 */
	CONFIGURE_ME,
	/**
	 * Default quartz property file configuration.
	 */
	PROPERTY;
	/**
	 * Default type.
	 */
	public static final SchedulerConfigurationType DEFAULT = PROPERTY;
}
