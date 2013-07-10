package net.bolbat.gear.common.ioc;

/**
 * Service distribution scope.
 * 
 * @author Alexandr Bolbat
 */
public enum ServiceDistributionScope implements Scope {

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
	public static final ServiceDistributionScope DEFAULT = LOCAL;

	/**
	 * Scope id.
	 */
	private final String id;

	/**
	 * Private constructor.
	 */
	private ServiceDistributionScope() {
		this.id = ServiceDistributionScope.class.getName() + "." + name();
	}

	@Override
	public String getId() {
		return id;
	}

	/**
	 * Get {@link ServiceDistributionScope} by id. Default scope will be returned if scope with given id not found.
	 * 
	 * @param aId
	 *            scope id
	 * @return {@link ServiceDistributionScope}
	 */
	public static final ServiceDistributionScope get(final String aId) {
		for (ServiceDistributionScope scope : ServiceDistributionScope.values())
			if (scope.getId().equalsIgnoreCase(aId))
				return scope;

		return DEFAULT;
	}

}
