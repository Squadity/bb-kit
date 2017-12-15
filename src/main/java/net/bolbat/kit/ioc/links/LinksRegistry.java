package net.bolbat.kit.ioc.links;

import net.bolbat.kit.ioc.scope.CompositeScope;
import net.bolbat.kit.ioc.scope.Scope;
import net.bolbat.kit.service.Service;

/**
 * Links registry interface.
 * 
 * @author Alexandr Bolbat
 */
public interface LinksRegistry {

	/**
	 * Get linked scopes.
	 * 
	 * @param key
	 *            link key
	 * @return linked scopes
	 */
	Scope[] get(String key);

	/**
	 * Make link between default and target scope.
	 * 
	 * @param service
	 *            service
	 * @param target
	 *            target scope, can be {@link CompositeScope}
	 */
	<S extends Service> void link(Class<S> service, Scope target);

	/**
	 * Make link between scopes.
	 * 
	 * @param service
	 *            service
	 * @param source
	 *            source scope, can be {@link CompositeScope}
	 * @param target
	 *            target scope, can be {@link CompositeScope}
	 */
	<S extends Service> void link(Class<S> service, Scope source, Scope target);

	/**
	 * Clear service links.
	 */
	<S extends Service> void clear(Class<S> service);

	/**
	 * Clear services links.
	 */
	void clear();

}
