package net.bolbat.kit.ioc.scope;

/**
 * Service distribution scope.
 * 
 * @author Alexandr Bolbat
 */
public enum DistributionScope implements Scope {

	/**
	 * Local service scope.
	 */
	LOCAL,

	/**
	 * Remote service scope.
	 */
	REMOTE;

	/**
	 * Default scope.
	 */
	public static final DistributionScope DEFAULT = LOCAL;

	/**
	 * Scope id.
	 */
	private final String id;

	/**
	 * Private constructor.
	 */
	DistributionScope() {
		this.id = name();
	}

	@Override
	public String getId() {
		return id;
	}

	/**
	 * Get {@link DistributionScope} by id. Default scope will be returned if scope with given id not found.
	 * 
	 * @param aId
	 *            scope id
	 * @return {@link DistributionScope}
	 */
	public static final DistributionScope get(final String aId) {
		for (DistributionScope scope : DistributionScope.values())
			if (scope.getId().equalsIgnoreCase(aId))
				return scope;

		return DEFAULT;
	}

}
