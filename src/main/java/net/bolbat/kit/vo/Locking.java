package net.bolbat.kit.vo;

import java.io.Serializable;

/**
 * Locking interface.
 * 
 * @author Alexandr Bolbat
 */
public interface Locking extends Serializable {

	/**
	 * Get {@link LockingState}.
	 * 
	 * @return {@link LockingState}
	 */
	LockingState getLockingState();

	/**
	 * Is read operation allowed.
	 * 
	 * @return <code>true</code> if allowed or <code>false</code>
	 */
	boolean isReadAllowed();

	/**
	 * Is write operation allowed.
	 * 
	 * @return <code>true</code> if allowed or <code>false</code>
	 */
	boolean isWriteAllowed();

}
