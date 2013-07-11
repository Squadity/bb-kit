package net.bolbat.gear.common.ioc.scope;


/**
 * Service type scope.
 * 
 * @author Alexandr Bolbat
 */
public enum TypeScope implements Scope {

	/**
	 * Persistence services scope.
	 */
	PERSISTENCE_SERVICE,

	/**
	 * Business services scope.
	 */
	BUSINESS_SERVICE,

	/**
	 * API scope.
	 */
	API,

	/**
	 * API (external) scope.
	 */
	API_EXTERNAL;

	/**
	 * Default scope.
	 */
	public static final TypeScope DEFAULT = BUSINESS_SERVICE;

	/**
	 * Scope id.
	 */
	private final String id;

	/**
	 * Private constructor.
	 */
	private TypeScope() {
		this.id = name();
	}

	@Override
	public String getId() {
		return id;
	}

	/**
	 * Get {@link TypeScope} by id. Default scope will be returned if scope with given id not found.
	 * 
	 * @param aId
	 *            scope id
	 * @return {@link TypeScope}
	 */
	public static final TypeScope get(final String aId) {
		for (TypeScope scope : TypeScope.values())
			if (scope.getId().equalsIgnoreCase(aId))
				return scope;

		return DEFAULT;
	}

}
