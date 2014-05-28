package net.bolbat.kit.cache;

/**
 * {@link LoadFunction} general exception.
 *
 * @author ivanbatura
 */
public class LoadException extends Exception {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -1982486300107160568L;

	/**
	 * Public constructor.
	 *
	 * @param message
	 * 		exception message
	 */
	public LoadException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 *
	 * @param cause
	 * 		exception cause
	 */
	public LoadException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Public constructor.
	 *
	 * @param message
	 * 		exception message
	 * @param cause
	 * 		exception cause
	 */
	public LoadException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
