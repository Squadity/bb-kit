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
	public String toString() {
		final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append("[oldEntity=").append(oldEntity);
		sb.append(", newEntity=").append(newEntity);
		sb.append(']');
		return sb.toString();
	}
}
