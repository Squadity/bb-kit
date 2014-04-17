package net.bolbat.kit.scheduler.task;

import net.bolbat.kit.scheduler.SchedulerException;

/**
 * {@link net.bolbat.kit.scheduler.Scheduler} exception, it can be thrown by {@link net.bolbat.kit.scheduler.task.queue.QueueProcessor} on any process exception.
 * 
 * @author ivanbatura
 */
public class ProcessingException extends SchedulerException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -5872844208440952277L;

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            - exception message
	 */
	public ProcessingException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            - exception cause
	 */
	public ProcessingException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            - exception message
	 * @param cause
	 *            - exception cause
	 */
	public ProcessingException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
