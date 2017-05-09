package net.bolbat.kit.ioc;

import net.bolbat.kit.ioc.scope.CompositeScope;
import net.bolbat.kit.ioc.scope.Scope;
import net.bolbat.kit.ioc.scope.ScopeUtil;
import net.bolbat.kit.service.Service;

/**
 * {@link Manager} utilities.
 * 
 * @author Alexandr Bolbat
 */
public final class ManagerUtils {

	/**
	 * Key delimiter.
	 */
	public static final String DELIMITER = "_";

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private ManagerUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Resolve service key.
	 * 
	 * @param service
	 *            service
	 * @param scopes
	 *            scopes
	 * @return {@link String} key
	 */
	public static <S extends Service> String resolveKey(final Class<S> service, final Scope... scopes) {
		return service.getName() + DELIMITER + ScopeUtil.scopesToString(scopes);
	}

	/**
	 * Resolve service key.
	 * 
	 * @param service
	 *            service
	 * @param scopes
	 *            scopes
	 * @return {@link String} key
	 */
	public static <S extends Service> String resolveKey(final Class<S> service, final Scope scope) {
		final String id = scope instanceof CompositeScope ? scope.getId() : ScopeUtil.scopesToString(scope);
		return service.getName() + DELIMITER + id;
	}

}
