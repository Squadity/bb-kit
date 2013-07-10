package net.bolbat.gear.common.ioc;

/**
 * Basic {@link Manager} module exception.
 * 
 * @author Alexandr Bolbat
 */
public class ManagerException extends Exception {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -1342846822544378793L;

	/**
	 * Default constructor.
	 */
	public ManagerException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public ManagerException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public ManagerException(final Throwable cause) {
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
	public ManagerException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
