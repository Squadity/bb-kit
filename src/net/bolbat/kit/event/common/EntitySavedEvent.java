package net.bolbat.kit.event.common;

import java.io.Serializable;

/**
 * Entity saved event.
 *
 * @param <Saved>
 * 		-  saved (created or updated) entity type
 * @author h3llka
 */
public class EntitySavedEvent<Saved extends Serializable> implements Serializable {
	/**
	 * Basic serial version UID.
	 */
	private static final long serialVersionUID = 8380601278511664596L;
	/**
	 * {@link Saved} before update.
	 */
	private final Saved oldEntity;
	/**
	 * {@link Saved} updated.
	 */
	private final Saved newEntity;

	/**
	 * Constructor.
	 *
	 * @param oldEntity
	 * 		before update
	 * @param newEntity
	 * 		updated
	 */
	protected EntitySavedEvent(final Saved oldEntity, final Saved newEntity) {
		if (newEntity == null)
			throw new IllegalArgumentException("newEntity is null");
		this.oldEntity = oldEntity;
		this.newEntity = newEntity;
	}

	/**
	 * Constructor.
	 *
	 * @param newEntity
	 * 		updated or created entity
	 */
	protected EntitySavedEvent(final Saved newEntity) {
		this(null, newEntity);
	}

	public Saved getOldEntity() {
		return oldEntity;
	}

	public Saved getNewEntity() {
		return newEntity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		@SuppressWarnings ("unchecked")
		EntitySavedEvent<Saved> that = (EntitySavedEvent<Saved>) o;

		if (newEntity != null ? !newEntity.equals(that.newEntity) : that.newEntity != null) return false;
		if (oldEntity != null ? !oldEntity.equals(that.oldEntity) : that.oldEntity != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = oldEntity != null ? oldEntity.hashCode() : 0;
		final int multiplier = 31;
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
