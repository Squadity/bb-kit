package net.bolbat.kit.config;

/**
 * Utility configuration runtime exception.
 * 
 * @author Alexandr Bolbat
 */
public class ConfigurationRuntimeException extends RuntimeException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -3615667569212300506L;

	/**
	 * Default constructor.
	 */
	public ConfigurationRuntimeException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public ConfigurationRuntimeException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public ConfigurationRuntimeException(final Throwable cause) {
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
	public ConfigurationRuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
