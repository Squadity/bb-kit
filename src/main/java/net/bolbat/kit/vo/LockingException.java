package net.bolbat.kit.vo;

/**
 * {@link Locking} runtime exception.
 * 
 * @author Alexandr Bolbat
 */
public class LockingException extends IllegalStateException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -6498832181051523312L;

	/**
	 * Default constructor.
	 */
	public LockingException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public LockingException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public LockingException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 * @param cause
	 *            exception cause
	 */
	public LockingException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
