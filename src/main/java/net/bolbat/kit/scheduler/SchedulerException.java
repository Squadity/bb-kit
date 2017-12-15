package net.bolbat.kit.scheduler;

/**
 * {@link Scheduler} general exception.
 *
 * @author ivanbatura
 */
public class SchedulerException extends Exception {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -2668716096985536670L;

	/**
	 * Public constructor.
	 *
	 * @param message
	 * 		exception message
	 */
	public SchedulerException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 *
	 * @param cause
	 * 		exception cause
	 */
	public SchedulerException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Public constructor.
	 *
	 * @param message
	 * 		exception message
	 * @param cause
	 * 		exception cause
	 */
	public SchedulerException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
