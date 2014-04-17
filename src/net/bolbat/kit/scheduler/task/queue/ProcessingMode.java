package net.bolbat.kit.scheduler.task.queue;

/**
 * {@link net.bolbat.kit.scheduler.Scheduler} processing mode.
 * 
 * @author ivanbatura
 */
public enum ProcessingMode {

	/**
	 * Asynchronous mode.
	 */
	ASYNC,

	/**
	 * Synchronous mode.
	 */
	SYNC;

	/**
	 * Default mode.
	 */
	public static final ProcessingMode DEFAULT = SYNC;

}
