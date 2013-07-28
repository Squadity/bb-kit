package net.bolbat.kit.api;

import net.bolbat.kit.service.ServiceInstantiationException;

/**
 * {@link APIInstantiationException} exception, can be used on {@link API} implementation instantiation phase.
 * 
 * @author Alexandr Bolbat
 */
public class APIInstantiationException extends ServiceInstantiationException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -7062713063376441637L;

	/**
	 * Default constructor.
	 */
	public APIInstantiationException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public APIInstantiationException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public APIInstantiationException(final Throwable cause) {
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
	public APIInstantiationException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
