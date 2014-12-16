package net.bolbat.kit.event.common;

import java.io.Serializable;

/**
 * Common entity created event.
 *
 * @param <Created>
 * 		-  created entity type
 * @author h3llka
 */
public class EntityCreatedEvent<Created extends Serializable> implements Serializable {

	/**
	 * Basic serial version UID.
	 */
	private static final long serialVersionUID = 8695079879899977002L;
	/**
	 * EntityCreatedEvent 'entity'.
	 */
	private final Created entity;

	/**
	 * Constructor.
	 *
	 * @param aEntity
	 * 		- created entity itself
	 */
	protected EntityCreatedEvent(final Created aEntity) {
		if (aEntity == null)
			throw new IllegalArgumentException("aEntity is null");
		this.entity = aEntity;
	}

	public Created getEntity() {
		return entity;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append("[entity=").append(entity);
		sb.append(']');
		return sb.toString();
	}
}
