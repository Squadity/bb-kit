package net.bolbat.kit.event.common;

import java.io.Serializable;

/**
 * Common entity deleted event.
 *
 * @param <Deleted>
 * 		-  deleted entity type
 * @author h3llka
 */
public class EntityDeletedEvent<Deleted extends Serializable> implements Serializable {
	/**
	 * Basic serial version UID.
	 */
	private static final long serialVersionUID = -2706928823439835952L;
	/**
	 * EntityCreatedEvent 'entity'.
	 */
	private final Deleted entity;

	/**
	 * Constructor.
	 *
	 * @param aEntity
	 * 		- created entity itself
	 */
	protected EntityDeletedEvent(final Deleted aEntity) {
		if (aEntity == null)
			throw new IllegalArgumentException("aEntity is null");
		this.entity = aEntity;
	}

	public Deleted getEntity() {
		return entity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		@SuppressWarnings ("unchecked")
		EntityDeletedEvent<Deleted> that = (EntityDeletedEvent<Deleted>) o;

		if (entity != null ? !entity.equals(that.entity) : that.entity != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return entity != null ? entity.hashCode() : 0;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append("[entity=").append(entity);
		sb.append(']');
		return sb.toString();
	}
}
