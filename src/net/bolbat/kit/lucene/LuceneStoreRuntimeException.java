package net.bolbat.kit.lucene;

/**
 * {@link LuceneStore} general exception.
 * 
 * @author Alexandr Bolbat
 */
public class LuceneStoreRuntimeException extends RuntimeException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 3899160558763886124L;

	/**
	 * Default constructor.
	 */
	public LuceneStoreRuntimeException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public LuceneStoreRuntimeException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public LuceneStoreRuntimeException(final Throwable cause) {
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
	public LuceneStoreRuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
