package net.bolbat.kit.service.ui;

import net.bolbat.kit.service.ServiceRuntimeException;

/**
 * Basic {@link UIService} runtime exception.
 * 
 * @author Alexandr Bolbat
 */
public class UIServiceRuntimeException extends ServiceRuntimeException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 2893804308365594004L;

	/**
	 * Default constructor.
	 */
	public UIServiceRuntimeException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public UIServiceRuntimeException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public UIServiceRuntimeException(final Throwable cause) {
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
	public UIServiceRuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
