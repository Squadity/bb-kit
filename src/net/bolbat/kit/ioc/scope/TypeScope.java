package net.bolbat.kit.ioc.scope;

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
	 * {@link net.bolbat.kit.service.Service} scope.
	 */
	SERVICE,

	/**
	 * {@link net.bolbat.kit.service.ui.UIService} scope.
	 */
	UI_SERVICE,

	/**
	 * External API scope.
	 */
	API_EXTERNAL;

	/**
	 * Default scope.
	 */
	public static final TypeScope DEFAULT = SERVICE;

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
