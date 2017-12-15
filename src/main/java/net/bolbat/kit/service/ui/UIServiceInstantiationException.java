package net.bolbat.kit.service.ui;

/**
 * {@link UIServiceInstantiationException} exception, can be used on {@link UIService} implementation instantiation phase.
 * 
 * @author Alexandr Bolbat
 */
public class UIServiceInstantiationException extends UIServiceRuntimeException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -7062713063376441637L;

	/**
	 * Default constructor.
	 */
	public UIServiceInstantiationException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public UIServiceInstantiationException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public UIServiceInstantiationException(final Throwable cause) {
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
	public UIServiceInstantiationException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
