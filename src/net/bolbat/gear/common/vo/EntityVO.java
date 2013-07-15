package net.bolbat.gear.common.vo;

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (created ^ (created >>> 32));
		result = prime * result + (int) (updated ^ (updated >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EntityVO))
			return false;
		EntityVO other = (EntityVO) obj;
		if (created != other.created)
			return false;
		if (updated != other.updated)
			return false;
		return true;
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
			throw new AssertionError("Can't clone [" + this + "]", e);
		}
	}

}
