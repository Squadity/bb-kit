package net.bolbat.gear.common.api;

import net.bolbat.gear.common.service.ServiceException;

/**
 * Basic {@link API} exception.
 * 
 * @author Alexandr Bolbat
 */
public class APIException extends ServiceException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 4153123852623290569L;

	/**
	 * Default constructor.
	 */
	public APIException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public APIException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public APIException(final Throwable cause) {
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
	public APIException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
