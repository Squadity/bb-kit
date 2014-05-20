package net.bolbat.kit.scheduler.task.execution;

/**
 * Scheduler execution task constants.
 *
 * @author ivanbatura
 */
public final class ExecutionConstants {

	/**
	 * Configured {@link net.bolbat.kit.scheduler.task.execution.ExecutionProcessor}.
	 */
	public static final String PROCESSOR = "processor";

	/**
	 * Configured {@link net.bolbat.kit.scheduler.TaskParameters}.
	 */
	public static final String PARAMETERS = "parameters";

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private ExecutionConstants() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}
}
