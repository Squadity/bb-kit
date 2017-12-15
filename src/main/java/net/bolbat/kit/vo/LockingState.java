package net.bolbat.kit.vo;

/**
 * Locking states.
 * 
 * @author Alexandr Bolbat
 */
public enum LockingState {

	/**
	 * No any operation allowed.
	 */
	NO_ACCESS,

	/**
	 * Read operations allowed.
	 */
	READABLE,

	/**
	 * Write operations allowed.
	 */
	WRITABLE,

	/**
	 * Read and write operations allowed.
	 */
	BOTH;

	/**
	 * Default state.
	 */
	public static final LockingState DEFAULT = LockingState.BOTH;

}
