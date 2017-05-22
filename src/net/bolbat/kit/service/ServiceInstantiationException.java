package net.bolbat.kit.service;

/**
 * {@link ServiceInstantiationException} exception, can be used on {@link Service} implementation instantiation phase.
 * 
 * @author Alexandr Bolbat
 */
public class ServiceInstantiationException extends ServiceRuntimeException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 4795705372357225467L;

	/**
	 * Default constructor.
	 */
	public ServiceInstantiationException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public ServiceInstantiationException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public ServiceInstantiationException(final Throwable cause) {
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
	public ServiceInstantiationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 * @param cause
	 *            exception cause
	 * @param enableSuppression
	 *            whether or not suppression is enabled or disabled
	 * @param writableStackTrace
	 *            whether or not the stack trace should be writable
	 */
	public ServiceInstantiationException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
