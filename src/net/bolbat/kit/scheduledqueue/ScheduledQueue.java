package net.bolbat.kit.scheduledqueue;

/**
 * Interface for scheduled queue.
 *
 * @author ivanbatura
 */
public interface ScheduledQueue {

	/**
	 * Pause scheduled loading elements.
	 *
	 * @throws ScheduledQueueException
	 */
	void pause() throws ScheduledQueueException;

	/**
	 * Resume scheduled loading elements.
	 *
	 * @throws ScheduledQueueException
	 */
	void resume() throws ScheduledQueueException;

	/**
	 * Is scheduler started.
	 *
	 * @return true if started, otherwise false
	 */
	boolean isStarted() throws ScheduledQueueException;

	/**
	 * Is scheduler paused.
	 *
	 * @return true if paused, otherwise false
	 */
	boolean isPaused() throws ScheduledQueueException;

	/**
	 * Configure scheduler with new schedule based on cron configuration, paused scheduler resume it's work with new schedule.
	 *
	 * @param schedule
	 * 		cron based schedule
	 * @throws ScheduledQueueException
	 */
	void schedule(String schedule) throws ScheduledQueueException;

	/**
	 * Configure scheduler with new schedule based on repeat interval, paused scheduler resume it's work with new schedule.
	 *
	 * @param interval
	 * 		repeat interval in milliseconds
	 * @throws ScheduledQueueException
	 */
	void schedule(long interval) throws ScheduledQueueException;

	/**
	 * Set mode.
	 *
	 * @param mode
	 * 		processing mode
	 */
	void setMode(ProcessingMode mode);

	/**
	 * Stop scheduler (loading elements to the queue) and queue (processing elements), future use of this instance is not possible after this action.
	 */
	void tearDown();

}
