package net.bolbat.kit.service;

/**
 * {@link SampleService} exception.
 * 
 * @author Alexandr Bolbat
 */
public class SampleServiceException extends ServiceException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 3203342520435342442L;

	/**
	 * Default constructor.
	 */
	public SampleServiceException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public SampleServiceException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public SampleServiceException(final Throwable cause) {
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
	public SampleServiceException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
