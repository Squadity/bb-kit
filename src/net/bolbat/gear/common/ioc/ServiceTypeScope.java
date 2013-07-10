package net.bolbat.gear.common.ioc;

/**
 * Service type scope.
 * 
 * @author Alexandr Bolbat
 */
public enum ServiceTypeScope implements Scope {

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
	public static final ServiceTypeScope DEFAULT = BUSINESS_SERVICE;

	/**
	 * Scope id.
	 */
	private final String id;

	/**
	 * Private constructor.
	 */
	private ServiceTypeScope() {
		this.id = ServiceTypeScope.class.getName() + "." + name();
	}

	@Override
	public String getId() {
		return id;
	}

	/**
	 * Get {@link ServiceTypeScope} by id. Default scope will be returned if scope with given id not found.
	 * 
	 * @param aId
	 *            scope id
	 * @return {@link ServiceTypeScope}
	 */
	public static final ServiceTypeScope get(final String aId) {
		for (ServiceTypeScope scope : ServiceTypeScope.values())
			if (scope.getId().equalsIgnoreCase(aId))
				return scope;

		return DEFAULT;
	}

}
