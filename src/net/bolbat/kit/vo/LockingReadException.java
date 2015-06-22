package net.bolbat.kit.vo;

/**
 * {@link Locking} read runtime exception.
 * 
 * @author Alexandr Bolbat
 */
public class LockingReadException extends LockingException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -2222624212283584645L;

	/**
	 * Public constructor.
	 * 
	 * @param state
	 *            current object {@link LockingState}
	 */
	public LockingReadException(final LockingState state) {
		super("Read is not allowed. State[" + state + "]");
	}

}
