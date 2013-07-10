package net.bolbat.gear.common.service;

/**
 * {@link ServiceInstantiationException} exception, can be used on {@link Service} implementation instantiation phase.
 * 
 * @author Alexandr Bolbat
 */
public class ServiceInstantiationException extends Exception {

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

}
