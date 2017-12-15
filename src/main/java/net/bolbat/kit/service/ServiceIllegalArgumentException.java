package net.bolbat.kit.service;

/**
 * Service runtime exception for cases with illegal arguments.
 * 
 * @author Alexandr Bolbat
 */
public class ServiceIllegalArgumentException extends IllegalArgumentException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 3082671281012593772L;

	/**
	 * Default constructor.
	 */
	public ServiceIllegalArgumentException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public ServiceIllegalArgumentException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param argumentName
	 *            argument name
	 * @param argumentValue
	 *            argument value
	 */
	public ServiceIllegalArgumentException(final String argumentName, final Object argumentValue) {
		super("Argument[" + argumentName + "] value[" + argumentValue + "] is illegal.");
	}

}
