package net.bolbat.kit.service;

/**
 * Basic {@link Service} runtime exception.
 * 
 * @author Alexandr Bolbat
 */
public class ServiceRuntimeException extends RuntimeException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -4068076226349642855L;

	/**
	 * Default constructor.
	 */
	public ServiceRuntimeException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public ServiceRuntimeException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public ServiceRuntimeException(final Throwable cause) {
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
	public ServiceRuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
