package net.bolbat.kit.scheduler;

/**
 * Interface for scheduled queue.
 *
 * @author ivanbatura
 */
public interface Scheduler {

	/**
	 * Pause scheduler elements.
	 *
	 * @throws SchedulerException
	 */
	void pause() throws SchedulerException;

	/**
	 * Resume scheduler elements.
	 *
	 * @throws SchedulerException
	 */
	void resume() throws SchedulerException;

	/**
	 * Is scheduler started.
	 *
	 * @return true if started, otherwise false
	 */
	boolean isStarted() throws SchedulerException;

	/**
	 * Is scheduler paused.
	 *
	 * @return true if paused, otherwise false
	 */
	boolean isPaused() throws SchedulerException;

	/**
	 * Configure scheduler with new schedule based on cron configuration, paused scheduler resume it's work with new schedule.
	 *
	 * @param schedule
	 * 		cron based schedule
	 * @throws SchedulerException
	 */
	void schedule(String schedule) throws SchedulerException;

	/**
	 * Configure scheduler with new schedule based on repeat interval, paused scheduler resume it's work with new schedule.
	 *
	 * @param interval
	 * 		repeat interval in milliseconds
	 * @throws SchedulerException
	 */
	void schedule(long interval) throws SchedulerException;

	/**
	 * Stop scheduler, future use of this instance is not possible after this action.
	 */
	void tearDown();

}
