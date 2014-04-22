package net.bolbat.kit.scheduler.task;

import net.bolbat.kit.scheduler.SchedulerException;

/**
 * {@link net.bolbat.kit.scheduler.Scheduler} exception, it can be thrown on any loading exception.
 *
 * @author ivanbatura
 */
public class LoadingException extends SchedulerException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 3757212518629624050L;

	/**
	 * Public constructor.
	 *
	 * @param message
	 * 		- exception message
	 */
	public LoadingException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 *
	 * @param cause
	 * 		- exception cause
	 */
	public LoadingException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Public constructor.
	 *
	 * @param message
	 * 		- exception message
	 * @param cause
	 * 		- exception cause
	 */
	public LoadingException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
