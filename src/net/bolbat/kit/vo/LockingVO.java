package net.bolbat.kit.vo;

/**
 * Value object for handling locking state.<br>
 * Can be used to control object immutability.<br>
 * Example: {@link LockingEntityVO}.
 * 
 * @author Alexandr Bolbat
 */
public class LockingVO implements Locking {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = 6937292885330225838L;

	/**
	 * {@link LockingState}.
	 */
	private LockingState state = LockingState.DEFAULT;

	@Override
	public LockingState getLockingState() {
		return state;
	}

	@Override
	public boolean isReadAllowed() {
		return getLockingState().equals(LockingState.BOTH) || getLockingState().equals(LockingState.READABLE);
	}

	@Override
	public boolean isWriteAllowed() {
		return getLockingState().equals(LockingState.BOTH) || getLockingState().equals(LockingState.WRITABLE);
	}

	/**
	 * Check is read operation, {@link IllegalStateException} will be thrown if read not allowed.
	 */
	protected void checkRead() {
		if (!isReadAllowed())
			throw new IllegalStateException("Operation not allowed");
	}

	/**
	 * Check is write operation, {@link IllegalStateException} will be thrown if write not allowed.
	 */
	protected void checkWrite() {
		if (!isWriteAllowed())
			throw new IllegalStateException("Operation not allowed");
	}

	/**
	 * Lock object with given configuration.
	 * 
	 * @param reads
	 *            is reads allowed
	 * @param writes
	 *            is writes allowed
	 */
	protected void lock(final boolean reads, final boolean writes) {
		if (reads && writes) {
			state = LockingState.BOTH;
			return;
		}

		if (reads && !writes) {
			state = LockingState.READABLE;
			return;
		}

		if (!reads && writes) {
			state = LockingState.WRITABLE;
			return;
		}

		state = LockingState.NO_ACCESS;
	}

	/**
	 * Unlock object for any operation.
	 */
	protected void unlock() {
		lock(true, true);
	}

}
