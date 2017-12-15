package net.bolbat.kit.vo;

/**
 * {@link EntityVO} constants.
 * 
 * @author Alexandr Bolbat
 */
public final class EntityVOConstants {

	/**
	 * Persistence field name for creation timestamp.
	 */
	public static final String P_F_CREATED = "created";

	/**
	 * Persistence field name for update timestamp.
	 */
	public static final String P_F_UPDATED = "updated";

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private EntityVOConstants() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

}
