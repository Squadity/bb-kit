package net.bolbat.kit.scheduler;

/**
 * Job builder.
 *
 * @author ivanbatura
 */
public interface TaskBuilder {
	/**
	 * Build {@link TaskConfiguration}.
	 *
	 * @return {@link TaskConfiguration}
	 */
	TaskConfiguration build();
}
