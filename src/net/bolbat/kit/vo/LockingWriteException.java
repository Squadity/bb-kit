package net.bolbat.kit.vo;

/**
 * {@link Locking} write runtime exception.
 * 
 * @author Alexandr Bolbat
 */
public class LockingWriteException extends LockingException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 9081963691621261740L;

	/**
	 * Public constructor.
	 * 
	 * @param state
	 *            current object {@link LockingState}
	 */
	public LockingWriteException(final LockingState state) {
		super("Write is not allowed. State[" + state + "]");
	}

}
