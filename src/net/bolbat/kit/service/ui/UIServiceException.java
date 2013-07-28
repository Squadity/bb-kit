package net.bolbat.kit.service.ui;

import net.bolbat.kit.service.ServiceException;

/**
 * Basic {@link UIService} exception.
 * 
 * @author Alexandr Bolbat
 */
public class UIServiceException extends ServiceException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 4153123852623290569L;

	/**
	 * Default constructor.
	 */
	public UIServiceException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public UIServiceException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public UIServiceException(final Throwable cause) {
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
	public UIServiceException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
