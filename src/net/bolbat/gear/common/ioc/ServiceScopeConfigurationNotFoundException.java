package net.bolbat.gear.common.ioc;

/**
 * {@link Manager} module exception. Module throw it if requested service not configured for given scope/scopes.
 * 
 * @author Alexandr Bolbat
 */
public class ServiceScopeConfigurationNotFoundException extends ManagerException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -3155052483493039142L;

	/**
	 * Default constructor.
	 */
	public ServiceScopeConfigurationNotFoundException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public ServiceScopeConfigurationNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public ServiceScopeConfigurationNotFoundException(final Throwable cause) {
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
	public ServiceScopeConfigurationNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
