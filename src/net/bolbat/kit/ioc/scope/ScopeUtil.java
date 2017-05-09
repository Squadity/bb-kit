package net.bolbat.kit.ioc.scope;

import static net.bolbat.kit.ioc.Manager.DEFAULT_SCOPE;
import static net.bolbat.utils.lang.StringUtils.EMPTY;
import static net.bolbat.utils.lang.StringUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@link Scope} related utility.
 * 
 * @author Alexandr Bolbat
 */
public final class ScopeUtil {

	/**
	 * Private constructor for preventing class instantiation.
	 */
	private ScopeUtil() {
		throw new IllegalAccessError("Can't instantiate.");
	}

	/**
	 * Convert scopes array to string representation, it's unique for given scopes, scopes order doesn't matter.<br>
	 * Uses <code>ScopeUtil.scopesToArray(false,scopes)</code> upon preparation.
	 * 
	 * @param scopes
	 *            scopes array
	 * @return {@link String} representation
	 */
	public static String scopesToString(final Scope... scopes) {
		if (scopes == null || scopes.length == 0)
			return EMPTY;

		final List<String> scopesIds = new ArrayList<>();
		for (final Scope scope : scopesToArray(false, scopes))
			if (scope != null && isNotEmpty(scope.getId()))
				scopesIds.add(scope.getId());

		if (scopesIds.isEmpty())
			return EMPTY;

		Collections.sort(scopesIds);

		final StringBuilder sb = new StringBuilder("[");
		for (final String scopeId : scopesIds) {
			if (sb.length() > 1)
				sb.append(",");

			sb.append(scopeId);
		}
		sb.append("]");

		return sb.toString();
	}

	/**
	 * Utility for converting scopes to array with required validation and aggregation.<br>
	 * All composite scopes would be expanded to flat structure.<br>
	 * All duplicated scopes would be skipped.
	 * 
	 * @param scopes
	 *            original scopes array
	 * @param addDefault
	 *            add default scope if original is empty
	 * @return aggregated scopes array
	 */
	public static Scope[] scopesToArray(final boolean addDefault, final Scope... scopes) {
		final List<Scope> aScopesList = new ArrayList<>();
		final Set<String> added = new HashSet<>();

		// handle composite scopes
		if (scopes != null && scopes.length > 0) {
			for (final Scope scope : scopes) {
				if (scope == null)
					continue;

				// scope if not composite
				if (!(scope instanceof CompositeScope)) {
					if (!(added.contains(scope.getId()))) {
						aScopesList.add(scope);
						added.add(scope.getId());
					}
					continue;
				}
				// scope is composite
				final CompositeScope composite = CompositeScope.class.cast(scope);
				for (final Scope cScope : composite.getScopes()) {
					if (cScope == null)
						continue;

					if (!(added.contains(cScope.getId()))) {
						aScopesList.add(cScope);
						added.add(cScope.getId());
					}
				}
			}
		}

		// handle default scope
		if (aScopesList.isEmpty() && addDefault)
			aScopesList.add(DEFAULT_SCOPE);

		return aScopesList.toArray(new Scope[aScopesList.size()]);
	}

}
