package net.bolbat.kit.config;

/**
 * Utility configuration exception.
 * 
 * @author Alexandr Bolbat
 */
public class ConfigurationException extends Exception {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -7353765691535542992L;

	/**
	 * Default constructor.
	 */
	public ConfigurationException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public ConfigurationException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public ConfigurationException(final Throwable cause) {
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
	public ConfigurationException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
