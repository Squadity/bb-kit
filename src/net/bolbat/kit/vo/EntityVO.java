package net.bolbat.kit.vo;

import java.io.Serializable;

/**
 * General entity data.
 * 
 * @author Alexandr Bolbat
 */
public class EntityVO implements Serializable, Cloneable {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = -208501342925713221L;

	/**
	 * Entity creation timestamp.
	 */
	private long created;

	/**
	 * Entity update timestamp.
	 */
	private long updated;

	public long getCreated() {
		return created;
	}

	public void setCreated(final long aCreated) {
		this.created = aCreated;
	}

	public long getUpdated() {
		return updated;
	}

	public void setUpdated(final long aUpdated) {
		this.updated = aUpdated;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(", created=").append(created);
		builder.append(", updated=").append(updated);
		return builder.toString();
	}

	@Override
	protected EntityVO clone() {
		try {
			return EntityVO.class.cast(super.clone());
		} catch (CloneNotSupportedException e) {
			throw new AssertionError("Can't clone [" + this + "]");
		}
	}

}
