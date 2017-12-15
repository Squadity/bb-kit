package net.bolbat.kit.ioc;

/**
 * Basic {@link Manager} module runtime exception.
 * 
 * @author Alexandr Bolbat
 */
public class ManagerRuntimeException extends RuntimeException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -8851478363242952091L;

	/**
	 * Default constructor.
	 */
	public ManagerRuntimeException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public ManagerRuntimeException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public ManagerRuntimeException(final Throwable cause) {
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
	public ManagerRuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
