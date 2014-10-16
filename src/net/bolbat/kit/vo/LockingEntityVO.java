package net.bolbat.kit.vo;

/**
 * Same as {@link EntityVO} but with {@link Locking} functionality.
 * 
 * @author Alexandr Bolbat
 */
public class LockingEntityVO extends LockingVO {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = 8017410549298572855L;

	/**
	 * Entity creation timestamp.
	 */
	private long created;

	/**
	 * Entity update timestamp.
	 */
	private long updated;

	/**
	 * Get entity creation timestamp.<br>
	 * Read operation access check will be performed.
	 * 
	 * @return <code>long</code>
	 */
	public long getCreated() {
		checkRead();
		return created;
	}

	/**
	 * Set entity creation timestamp.<br>
	 * Write operation access check will be performed.
	 * 
	 * @param aCreated
	 *            entity creation timestamp
	 */
	public void setCreated(final long aCreated) {
		checkWrite();
		this.created = aCreated;
	}

	/**
	 * Get entity update timestamp.<br>
	 * Read operation access check will be performed.
	 * 
	 * @return <code>long</code>
	 */
	public long getUpdated() {
		checkRead();
		return updated;
	}

	/**
	 * Set entity update timestamp.<br>
	 * Write operation access check will be performed.
	 * 
	 * @param aUpdated
	 *            entity update timestamp
	 */
	public void setUpdated(final long aUpdated) {
		checkWrite();
		this.updated = aUpdated;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(", created=").append(created);
		builder.append(", updated=").append(updated);
		return builder.toString();
	}

	@Override
	public LockingEntityVO clone() {
		try {
			return LockingEntityVO.class.cast(super.clone());
		} catch (final CloneNotSupportedException e) {
			throw new AssertionError("Can't clone [" + this + "]");
		}
	}

}
