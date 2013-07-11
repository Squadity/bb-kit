package net.bolbat.gear.common.ioc;

/**
 * {@link Manager} module exception. Module throw it if requested service not configured.
 * 
 * @author Alexandr Bolbat
 */
public class ConfigurationNotFoundException extends ManagerException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -3155052483493039142L;

	/**
	 * Default constructor.
	 */
	public ConfigurationNotFoundException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public ConfigurationNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public ConfigurationNotFoundException(final Throwable cause) {
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
	public ConfigurationNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
