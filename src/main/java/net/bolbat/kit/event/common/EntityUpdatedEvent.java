package net.bolbat.kit.event.common;

import java.io.Serializable;

/**
 * Entity updated event.
 *
 * @param <Updated>
 * 		- entity type
 * @author h3llka
 */
public class EntityUpdatedEvent<Updated extends Serializable> implements Serializable {
	/**
	 * Basic serial version UID.
	 */
	private static final long serialVersionUID = 6998272145202196761L;
	/**
	 * {@link Updated} before update.
	 */
	private final Updated oldEntity;
	/**
	 * {@link Updated} updated.
	 */
	private final Updated newEntity;

	/**
	 * Constructor.
	 *
	 * @param oldEntity
	 * 		before update
	 * @param newEntity
	 * 		updated
	 */
	protected EntityUpdatedEvent(final Updated oldEntity, final Updated newEntity) {
		if (oldEntity == null)
			throw new IllegalArgumentException("oldEntity is null");
		if (newEntity == null)
			throw new IllegalArgumentException("newEntity is null");
		this.oldEntity = oldEntity;
		this.newEntity = newEntity;
	}

	public Updated getOldEntity() {
		return oldEntity;
	}

	public Updated getNewEntity() {
		return newEntity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		@SuppressWarnings ("unchecked")
		EntityUpdatedEvent<Updated> that = (EntityUpdatedEvent<Updated>) o;

		if (newEntity != null ? !newEntity.equals(that.newEntity) : that.newEntity != null) return false;
		if (oldEntity != null ? !oldEntity.equals(that.oldEntity) : that.oldEntity != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		final int multiplier = 31;
		int result = oldEntity != null ? oldEntity.hashCode() : 0;
		result = multiplier * result + (newEntity != null ? newEntity.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append("[oldEntity=").append(oldEntity);
		sb.append(", newEntity=").append(newEntity);
		sb.append(']');
		return sb.toString();
	}
}
