package net.bolbat.gear.common.ioc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.bolbat.gear.common.Module;
import net.bolbat.gear.common.service.Service;
import net.bolbat.gear.common.service.ServiceFactory;
import net.bolbat.gear.common.service.ServiceInstantiationException;
import net.bolbat.gear.common.service.ServiceLocator;
import net.bolbat.gear.common.service.ServiceLocatorConfiguration;
import net.bolbat.utils.reflect.Instantiator;

/**
 * Module for managing services configuration.
 * 
 * @author Alexandr Bolbat
 */
public final class Manager implements Module {

	/**
	 * Services configuration storage.
	 */
	private static final Map<String, ServiceScopeConfiguration<?, ?>> STORAGE = new ConcurrentHashMap<String, ServiceScopeConfiguration<?, ?>>();

	/**
	 * Default scope.
	 */
	public static final Scope DEFAULT_SCOPE = new ServiceCustomScope("[SYSTEM_DEFAULT_SCOPE]");

	/**
	 * Private constructor for preventing class instantiation.
	 */
	private Manager() {
		throw new IllegalAccessError("Can't instantiate.");
	}

	/**
	 * Register service.
	 * 
	 * @param service
	 *            service interface
	 * @param serviceFactory
	 *            service factory
	 * @param scopes
	 *            service scopes, default scopes will be selected if no one given
	 */
	public static <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final Class<SF> serviceFactory, final Scope... scopes) {
		SF factory = Instantiator.instantiate(serviceFactory);
		register(service, factory, null, scopes);
	};

	/**
	 * Register service.
	 * 
	 * @param service
	 *            service interface
	 * @param serviceLocator
	 *            service locator
	 * @param configuration
	 *            service locator configuration, can be <code>null</code>
	 * @param scopes
	 *            service scopes, default scopes will be selected if no one given
	 */
	public static <S extends Service, SL extends ServiceLocator<S>> void register(final Class<S> service, final Class<SL> serviceLocator,
			final ServiceLocatorConfiguration configuration, final Scope... scopes) {
		SL factory = Instantiator.instantiate(serviceLocator);
		register(service, factory, configuration, scopes);
	};

	/**
	 * Register service.
	 * 
	 * @param service
	 *            service interface
	 * @param serviceFactory
	 *            service factory
	 * @param configuration
	 *            service locator configuration, used if service factory instance of service locator, can be <code>null</code>
	 * @param scopes
	 *            service scopes, default scopes will be selected if no one given
	 */
	public static <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final SF serviceFactory,
			final ServiceLocatorConfiguration configuration, final Scope... scopes) {
		if (service == null)
			throw new IllegalArgumentException("[service] argument are null");

		if (serviceFactory == null)
			throw new IllegalArgumentException("[serviceFactory] argument are null");

		ServiceLocatorConfiguration aConfiguration = configuration;
		if (aConfiguration == null)
			aConfiguration = new ServiceLocatorConfiguration();

		Scope[] aScopes = scopes;
		if (aScopes == null || aScopes.length == 0)
			aScopes = new Scope[] { DEFAULT_SCOPE };

		ServiceScopeConfiguration<S, SF> serviceScopeConfiguration = new ServiceScopeConfiguration<S, SF>(service, serviceFactory, aConfiguration, aScopes);
		String key = service.getName() + "_" + ScopeUtil.scopesToString(scopes);
		STORAGE.put(key, serviceScopeConfiguration);
	}

	/**
	 * Get service.
	 * 
	 * @param service
	 *            service interface
	 * @param scopes
	 *            service scopes, default scopes will be selected if no one given
	 * @return service instance
	 * @throws ManagerException
	 */
	public static <S extends Service> S get(final Class<S> service, final Scope... scopes) throws ManagerException {
		if (service == null)
			throw new IllegalArgumentException("[service] argument are null");

		Scope[] aScopes = scopes;
		if (aScopes == null || aScopes.length == 0)
			aScopes = new Scope[] { DEFAULT_SCOPE };

		String key = service.getName() + "_" + ScopeUtil.scopesToString(aScopes);

		@SuppressWarnings("unchecked")
		ServiceScopeConfiguration<S, ServiceFactory<S>> configuration = (ServiceScopeConfiguration<S, ServiceFactory<S>>) STORAGE.get(key);

		if (configuration == null)
			throw new ServiceScopeConfigurationNotFoundException();

		if (configuration.getInstance() != null)
			return configuration.getInstance();

		return getInternally(key);
	}

	/**
	 * Get service internally.
	 * 
	 * @param key
	 *            service scope configuration storage key
	 * @return service instance
	 * @throws ManagerException
	 */
	private static synchronized <S extends Service> S getInternally(final String key) throws ManagerException {
		@SuppressWarnings("unchecked")
		ServiceScopeConfiguration<S, ServiceFactory<S>> configuration = (ServiceScopeConfiguration<S, ServiceFactory<S>>) STORAGE.get(key);

		if (configuration.getInstance() != null)
			return configuration.getInstance();

		ServiceFactory<S> factory = configuration.getServiceFactory();
		S instance = null;

		// locator approach instantiation
		if (factory instanceof ServiceLocator<?>)
			try {
				instance = ((ServiceLocator<S>) factory).locate(configuration.getConfiguration());
			} catch (ServiceInstantiationException e) {
				throw new ManagerException("Can't locate service", e);
			}

		// factory approach instantiation
		else
			try {
				instance = factory.create();
			} catch (ServiceInstantiationException e) {
				throw new ManagerException("Can't instantiate service", e);
			}

		// updating scope configuration with already obtained service instance
		configuration.setInstance(instance);
		return instance;
	}

	/**
	 * Tear down {@link Manager} state.
	 */
	public static synchronized void tearDown() {
		STORAGE.clear();
	}

}
