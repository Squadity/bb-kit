package net.bolbat.gear.common.ioc.scope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	 * Convert scopes array to string representation, it's unique for given scopes, scopes order doesn't matter.
	 * 
	 * @param scopes
	 *            scopes array
	 * @return {@link String} representation
	 */
	public static String scopesToString(final Scope... scopes) {
		if (scopes == null || scopes.length == 0)
			return "";

		List<String> scopesIds = new ArrayList<String>();
		for (Scope scope : scopes)
			if (scope != null)
				scopesIds.add(scope.getId());

		Collections.sort(scopesIds);

		StringBuilder sb = new StringBuilder("[");
		for (String scopeId : scopesIds) {
			if (sb.length() > 1)
				sb.append(",");

			sb.append(scopeId);
		}
		sb.append("]");

		return sb.toString();
	}

}
