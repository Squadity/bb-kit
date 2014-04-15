package net.bolbat.kit.scheduledqueue;

/**
 * {@link ScheduledQueue} processing mode.
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
