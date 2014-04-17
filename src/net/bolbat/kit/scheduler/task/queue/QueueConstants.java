package net.bolbat.kit.scheduler.task.queue;

/**
 * Scheduler job constants.
 *
 * @author ivanbatura
 */
public final class QueueConstants {
	/**
	 * Configured {@link QueueProcessor}.
	 */
	public static final String LOADER = "loader";

	/**
	 * Configured {@link QueueProcessor}.
	 */
	public static final String PROCESSOR = "processor";

	/**
	 * Configured {@link ProcessingMode}.
	 */
	public static final String PROCESSING_MODE = "processing_mode";

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private QueueConstants() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}
}
