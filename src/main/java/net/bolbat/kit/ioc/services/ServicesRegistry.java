package net.bolbat.kit.ioc.services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.bolbat.kit.ioc.scope.Scope;
import net.bolbat.kit.service.Configuration;
import net.bolbat.kit.service.Service;
import net.bolbat.kit.service.ServiceFactory;

/**
 * Services registry interface.
 * 
 * @author Alexandr Bolbat
 */
public interface ServicesRegistry {

	/**
	 * Get all services configurations.
	 * 
	 * @return collection with {@link ServiceConfiguration}
	 */
	Collection<ServiceConfiguration<?>> getAll();

	/**
	 * Get service configuration.
	 * 
	 * @param key
	 *            service key
	 * @return {@link ServiceConfiguration} or <code>null</code>
	 */
	<S extends Service> ServiceConfiguration<S> get(String key);

	/**
	 * Register service.
	 * 
	 * @param service
	 *            service interface
	 * @param factory
	 *            service factory
	 */
	<S extends Service, SF extends ServiceFactory<S>> void register(Class<S> service, Class<SF> factory);

	/**
	 * Register service.
	 * 
	 * @param service
	 *            service interface
	 * @param factory
	 *            service factory
	 * @param scopes
	 *            service scopes, default scopes will be selected if no one given
	 */
	<S extends Service, SF extends ServiceFactory<S>> void register(Class<S> service, Class<SF> factory, Scope... scopes);

	/**
	 * Register service.
	 * 
	 * @param service
	 *            service interface
	 * @param factory
	 *            service factory
	 * @param conf
	 *            service factory configuration, can be <code>null</code>
	 */
	<S extends Service, SF extends ServiceFactory<S>> void register(Class<S> service, Class<SF> factory, Configuration conf);

	/**
	 * Register service.
	 * 
	 * @param service
	 *            service interface
	 * @param factory
	 *            service factory
	 * @param conf
	 *            service factory configuration, can be <code>null</code>
	 * @param scopes
	 *            service scopes, default scopes will be selected if no one given
	 */
	<S extends Service, SF extends ServiceFactory<S>> void register(Class<S> service, Class<SF> factory, Configuration conf, Scope... scopes);

	/**
	 * Register service.
	 * 
	 * @param service
	 *            service interface
	 * @param factory
	 *            service factory instance
	 */
	<S extends Service, SF extends ServiceFactory<S>> void register(Class<S> service, SF factory);

	/**
	 * Register service.<br>
	 * Uses <code>ScopeUtil.scopesToArray(true,scopes)</code> upon registration.
	 * 
	 * @param service
	 *            service interface
	 * @param factory
	 *            service factory instance
	 * @param scopes
	 *            service scopes, default scopes will be selected if no one given
	 */
	<S extends Service, SF extends ServiceFactory<S>> void register(Class<S> service, SF factory, Scope... scopes);

	/**
	 * Register service.
	 * 
	 * @param service
	 *            service interface
	 * @param factory
	 *            service factory instance
	 * @param conf
	 *            service factory configuration, can be <code>null</code>
	 */
	<S extends Service, SF extends ServiceFactory<S>> void register(Class<S> service, SF factory, Configuration conf);

	/**
	 * Register service.<br>
	 * Uses <code>ScopeUtil.scopesToArray(true,scopes)</code> upon registration.
	 * 
	 * @param service
	 *            service interface
	 * @param factory
	 *            service factory instance
	 * @param conf
	 *            service factory configuration, can be <code>null</code>
	 * @param scopes
	 *            service scopes, default scopes will be selected if no one given
	 */
	<S extends Service, SF extends ServiceFactory<S>> void register(Class<S> service, SF factory, Configuration conf, final Scope... scopes);

	/**
	 * Register service.<br>
	 * {@link PostConstruct} will be ignored.<br>
	 * {@link PreDestroy} will be executed during <code>tearDown</code>.
	 * 
	 * @param service
	 *            service interface
	 * @param instance
	 *            service instance
	 */
	<S extends Service> void register(Class<S> service, S instance);

	/**
	 * Register service.<br>
	 * Uses <code>ScopeUtil.scopesToArray(true,scopes)</code> upon registration.<br>
	 * {@link PostConstruct} will be ignored.<br>
	 * {@link PreDestroy} will be executed during <code>tearDown</code>.
	 * 
	 * @param service
	 *            service interface
	 * @param instance
	 *            service instance
	 * @param scopes
	 *            service scopes, default scopes will be selected if no one given
	 */
	<S extends Service> void register(Class<S> service, S instance, Scope... scopes);

	/**
	 * Clear service configurations.
	 */
	void clear(String key);

	/**
	 * Clear services configurations.
	 */
	void clear();

}
