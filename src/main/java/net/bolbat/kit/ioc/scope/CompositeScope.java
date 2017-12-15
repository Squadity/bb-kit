package net.bolbat.kit.ioc.scope;

/**
 * Composite scope.
 * 
 * @author Alexandr Bolbat
 */
public class CompositeScope implements Scope {

	/**
	 * Source scopes.
	 */
	private final Scope[] scopes;

	@Override
	public String getId() {
		return ScopeUtil.scopesToString(scopes);
	}

	/**
	 * Default constructor.
	 * 
	 * @param scopes
	 *            source scopes
	 */
	private CompositeScope(final Scope... scopes) {
		this.scopes = scopes != null && scopes.length > 0 ? ScopeUtil.scopesToArray(false, scopes) : new Scope[0];
	}

	public Scope[] getScopes() {
		return scopes != null ? scopes.clone() : null;
	}

	/**
	 * Create {@link CompositeScope} from given scopes.<br>
	 * Uses <code>ScopeUtil.scopesToArray(false,scopes)</code> upon creation.
	 * 
	 * @param scopes
	 *            scopes
	 * @return {@link CompositeScope}
	 */
	public static CompositeScope get(final Scope... scopes) {
		return new CompositeScope(scopes);
	}

	@Override
	public String toString() {
		return getId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getId().hashCode();
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CompositeScope))
			return false;
		final CompositeScope other = (CompositeScope) obj;
		return getId().equals(other.getId());
	}

}
