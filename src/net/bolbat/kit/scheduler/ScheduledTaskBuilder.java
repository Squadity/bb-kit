package net.bolbat.kit.scheduler;

/**
 * Job builder.
 *
 * @author ivanbatura
 */
public interface ScheduledTaskBuilder {
	/**
	 * Build {@link ScheduledTask}.
	 *
	 * @return {@link ScheduledTask}
	 */
	ScheduledTask build();
}
