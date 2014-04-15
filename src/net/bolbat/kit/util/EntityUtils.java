package net.bolbat.kit.util;

import net.bolbat.kit.vo.EntityVO;

/**
 * Utility for {@link EntityVO}.
 * 
 * @author Alexandr Bolbat
 */
public final class EntityUtils {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private EntityUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Set created and update fields to {@code newEntity} based on the {@code oldEntity}.
	 * 
	 * @param oldEntity
	 *            {@link EntityVO}
	 * @param newEntity
	 *            {@link EntityVO}
	 */
	public static void processUpdate(final EntityVO oldEntity, final EntityVO newEntity) {
		if (newEntity == null)
			throw new IllegalArgumentException("argument newEntity is null");

		final long operationTimestamp = System.currentTimeMillis();
		newEntity.setCreated(oldEntity != null ? oldEntity.getCreated() : operationTimestamp);
		newEntity.setUpdated(operationTimestamp);
	}

}
