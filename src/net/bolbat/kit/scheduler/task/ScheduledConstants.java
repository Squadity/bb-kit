package net.bolbat.kit.scheduler.task;

/**
 * Scheduler job constants.
 *
 * @author ivanbatura
 */
public final class ScheduledConstants {
	/**
	 * Configured {@link Processor}.
	 */
	public static final String LOADER = "loader";

	/**
	 * Configured {@link Processor}.
	 */
	public static final String PROCESSOR = "processor";

	/**
	 * Configured {@link ProcessingMode}.
	 */
	public static final String PROCESSING_MODE = "processing_mode";

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private ScheduledConstants() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}
}
