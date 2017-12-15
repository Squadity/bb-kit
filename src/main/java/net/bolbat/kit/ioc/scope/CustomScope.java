package net.bolbat.kit.ioc.scope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Custom {@link Scope} implementation, any custom scope can be represented by this class.
 * 
 * @author Alexandr Bolbat
 */
public class CustomScope implements Scope {

	/**
	 * Internal cache.
	 */
	private static final Map<String, CustomScope> CACHE = new ConcurrentHashMap<>();

	/**
	 * Scope unique id.
	 */
	private final String id;

	/**
	 * Default constructor.
	 * 
	 * @param aId
	 *            scope id
	 */
	private CustomScope(final String aId) {
		this.id = String.valueOf(aId);
	}

	/**
	 * Get {@link CustomScope} instance with given identifier, with lazy initialization and caching.
	 * 
	 * @param aId
	 *            scope identifier
	 * @return {@link CustomScope}
	 */
	public static CustomScope get(final String aId) {
		CustomScope scope = CACHE.get(aId);
		if (scope == null) {
			scope = new CustomScope(aId);
			CACHE.put(aId, scope);
		}

		return scope;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return getId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CustomScope))
			return false;
		CustomScope other = (CustomScope) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
