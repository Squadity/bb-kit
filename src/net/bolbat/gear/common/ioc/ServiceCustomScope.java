package net.bolbat.gear.common.ioc;

/**
 * Service custom scope implementation, any custom scope can be represented by this class.
 * 
 * @author Alexandr Bolbat
 */
public class ServiceCustomScope implements Scope {

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
	public ServiceCustomScope(final String aId) {
		this.id = ServiceCustomScope.class.getName() + "." + String.valueOf(aId);
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
		if (!(obj instanceof ServiceCustomScope))
			return false;
		ServiceCustomScope other = (ServiceCustomScope) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
