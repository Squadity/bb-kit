package net.bolbat.kit.orchestrator.exception;

import net.bolbat.kit.orchestrator.Orchestrator;

/**
 * {@link Orchestrator} exception.
 * 
 * @author Alexandr Bolbat
 */
public class OrchestrationException extends RuntimeException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 5022109644719392137L;

	/**
	 * Default constructor.
	 */
	public OrchestrationException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public OrchestrationException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public OrchestrationException(final Throwable cause) {
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
	public OrchestrationException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
