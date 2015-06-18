package net.bolbat.kit.ioc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import net.bolbat.kit.Module;
import net.bolbat.kit.ioc.scope.CompositeScope;
import net.bolbat.kit.ioc.scope.CustomScope;
import net.bolbat.kit.ioc.scope.Scope;
import net.bolbat.kit.ioc.scope.ScopeUtil;
import net.bolbat.kit.service.Configuration;
import net.bolbat.kit.service.Service;
import net.bolbat.kit.service.ServiceFactory;
import net.bolbat.utils.lang.ToStringUtils;
import net.bolbat.utils.reflect.ClassUtils;
import net.bolbat.utils.reflect.Instantiator;

/**
 * Module for managing services configuration.
 * 
 * @author Alexandr Bolbat
 */
public final class Manager implements Module {

	/**
	 * Synchronization lock.
	 */
	private static final Object LOCK = new Object();

	/**
	 * Services configuration storage.
	 */
	private static final Map<String, ScopeConfiguration<?, ?>> STORAGE = new ConcurrentHashMap<>();

	/**
	 * Scopes links configuration storage.
	 */
	private static final Map<String, Scope[]> LINKS = new ConcurrentHashMap<>();

	/**
	 * Key delimiter.
	 */
	private static final String DELIMITER = "_";

	/**
	 * Default scope.
	 */
	public static final Scope DEFAULT_SCOPE = CustomScope.get("SYSTEM_DEFAULT_SCOPE");

	/**
	 * Private constructor for preventing class instantiation.
	 */
	private Manager() {
		throw new IllegalAccessError("Can't instantiate.");
	}

	/**
	 * Make link between default and target scope.
	 * 
	 * @param service
	 *            service
	 * @param target
	 *            target scope, can be {@link CompositeScope}
	 */
	public static <S extends Service> void link(final Class<S> service, final Scope target) {
		if (service == null)
			throw new IllegalArgumentException("service argument is null.");
		if (target == null)
			throw new IllegalArgumentException("target argument is empty.");

		link(service, DEFAULT_SCOPE, target);
	}

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
	public static <S extends Service> void link(final Class<S> service, final Scope source, final Scope target) {
		if (service == null)
			throw new IllegalArgumentException("service argument is null.");
		if (source == null)
			throw new IllegalArgumentException("source argument is empty.");
		if (target == null)
			throw new IllegalArgumentException("target argument is empty.");
		if (source.getId().equalsIgnoreCase(target.getId()))
			throw new IllegalArgumentException("source[" + source + "] and target[" + target + "] scopes is equal.");

		final String sourceId = source instanceof CompositeScope ? source.getId() : ScopeUtil.scopesToString(source);
		final String sourceKey = service.getName() + DELIMITER + sourceId;
		synchronized (LOCK) {
			if (target instanceof CompositeScope)
				LINKS.put(sourceKey, CompositeScope.class.cast(target).getScopes());
			else {
				LINKS.put(sourceKey, new Scope[] { target });
			}
		}
	}

	/**
	 * Register service.
	 * 
	 * @param service
	 *            service interface
	 * @param factory
	 *            service factory
	 */
	public static <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final Class<SF> factory) {
		register(service, factory, DEFAULT_SCOPE);
	}

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
	public static <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final Class<SF> factory, final Scope... scopes) {
		register(service, factory, null, scopes);
	}

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
	public static <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final Class<SF> factory, final Configuration conf) {
		register(service, factory, conf, DEFAULT_SCOPE);
	}

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
	public static <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final Class<SF> factory, final Configuration conf,
			final Scope... scopes) {
		final SF instance = Instantiator.instantiate(factory);
		register(service, instance, conf, scopes);
	}

	/**
	 * Register service.
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
	public static <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final SF factory, final Configuration conf,
			final Scope... scopes) {
		if (service == null)
			throw new IllegalArgumentException("service argument is null.");
		if (factory == null)
			throw new IllegalArgumentException("serviceFactory argument is null.");

		final Scope[] aScopes = scopes != null && scopes.length > 0 ? scopes : new Scope[] { DEFAULT_SCOPE };
		final String key = service.getName() + DELIMITER + ScopeUtil.scopesToString(aScopes);
		final ScopeConfiguration<S, SF> serviceScopeConfiguration = new ScopeConfiguration<>(service, factory, conf, aScopes);

		synchronized (LOCK) {
			STORAGE.put(key, serviceScopeConfiguration);
		}
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
			throw new IllegalArgumentException("service argument is null.");

		final Scope[] aScopes = scopes != null && scopes.length > 0 ? scopes : new Scope[] { DEFAULT_SCOPE };
		final String key = service.getName() + DELIMITER + ScopeUtil.scopesToString(aScopes);

		@SuppressWarnings("unchecked")
		final ScopeConfiguration<S, ServiceFactory<S>> configuration = (ScopeConfiguration<S, ServiceFactory<S>>) STORAGE.get(key);

		if (configuration == null) {
			// trying to resolve scope link
			final Scope[] scope = LINKS.get(key);
			if (scope != null)
				return get(service, scope);

			// link not found
			throw new ConfigurationNotFoundException();
		}

		if (configuration.getInstance() != null)
			return configuration.getInstance();

		return getInternally(key, true);
	}

	/**
	 * Get service internally.
	 * 
	 * @param key
	 *            service scope configuration storage key
	 * @param postConstruct
	 *            is post-construct should be executed
	 * @return service instance
	 * @throws ManagerException
	 */
	private static <S extends Service> S getInternally(final String key, final boolean postConstruct) throws ManagerException {
		synchronized (LOCK) {
			@SuppressWarnings("unchecked")
			final ScopeConfiguration<S, ServiceFactory<S>> configuration = (ScopeConfiguration<S, ServiceFactory<S>>) STORAGE.get(key);

			if (configuration.getInstance() != null)
				return configuration.getInstance();

			final ServiceFactory<S> factory = configuration.getServiceFactory();

			try {
				final S instance = factory.create(configuration.getConfiguration());

				// updating scope configuration with already obtained service instance
				configuration.setInstance(instance);

				// execute post-construct
				if (postConstruct)
					ClassUtils.executePostConstruct(instance);

				return instance;
				// CHECKSTYLE:OFF
			} catch (final RuntimeException e) {
				// CHECKSTYLE:ON
				throw new ManagerException("Can't instantiate service", e);
			}
		}
	}

	/**
	 * Warm up {@link Manager} state.<br>
	 * For registered and not instantiated services 'post-construct' will be processed.
	 */
	public static void warmUp() {
		synchronized (LOCK) {
			final List<Object> instances = new ArrayList<>();
			for (final ScopeConfiguration<?, ?> conf : STORAGE.values()) {
				if (conf.getInstance() != null)
					continue;

				try {
					instances.add(getInternally(conf.toKey(), false));
				} catch (final ManagerException e) {
					throw new ManagerRuntimeException("Can't warm up", e);
				}
			}

			// execute post-construct
			for (final Object instance : instances)
				try {
					ClassUtils.executePostConstruct(instance);
					// CHECKSTYLE:OFF
				} catch (final RuntimeException e) {
					// CHECKSTYLE:ON
					throw new ManagerRuntimeException("Can't warm up", e);
				}
		}
	}

	/**
	 * Warm up {@link Manager} state for given service.<br>
	 * For registered and not instantiated services 'post-construct' will be processed.
	 * 
	 * @param service
	 *            service interface
	 */
	public static <S extends Service> void warmUp(final Class<S> service) {
		synchronized (LOCK) {
			final List<Object> instances = new ArrayList<>();
			for (final ScopeConfiguration<?, ?> conf : STORAGE.values()) {
				if (!conf.getService().equals(service) || conf.getInstance() != null)
					continue;

				try {
					instances.add(getInternally(conf.toKey(), false));
				} catch (final ManagerException e) {
					throw new ManagerRuntimeException("Can't warm up", e);
				}
			}

			// execute post-construct
			for (final Object instance : instances)
				try {
					ClassUtils.executePostConstruct(instance);
					// CHECKSTYLE:OFF
				} catch (final RuntimeException e) {
					// CHECKSTYLE:ON
					throw new ManagerRuntimeException("Can't warm up", e);
				}
		}
	}

	/**
	 * Tear down {@link Manager} state.<br>
	 * For registered and instantiated services 'pre-destroy' will be processed.
	 */
	public static void tearDown() {
		synchronized (LOCK) {
			final List<ScopeConfiguration<?, ?>> values = new ArrayList<>(STORAGE.values());
			STORAGE.clear();
			LINKS.clear();

			// execute pre-destroy
			for (final ScopeConfiguration<?, ?> conf : values)
				if (conf.getInstance() != null)
					ClassUtils.executePreDestroy(conf.getInstance());
		}
	}

	/**
	 * Tear down {@link Manager} state for given service.<br>
	 * For registered and instantiated services 'pre-destroy' will be processed.
	 * 
	 * @param service
	 *            service interface
	 */
	public static <S extends Service> void tearDown(final Class<S> service) {
		synchronized (LOCK) {
			final List<ScopeConfiguration<?, ?>> instances = new ArrayList<>();
			for (final ScopeConfiguration<?, ?> conf : STORAGE.values())
				if (conf.getService().equals(service))
					instances.add(conf);

			// clearing services configuration
			for (final ScopeConfiguration<?, ?> conf : instances) {
				STORAGE.remove(conf.toKey());

				// executing services pre-destroy
				if (conf.getInstance() != null)
					ClassUtils.executePreDestroy(conf.getInstance());
			}

			// clearing services links
			final String serviceLinkPrefix = service.getName() + DELIMITER;
			for (final Entry<String, Scope[]> linkEntry : LINKS.entrySet()) {
				if (linkEntry.getKey().startsWith(serviceLinkPrefix))
					LINKS.remove(linkEntry.getKey());
			}
		}
	}

	/**
	 * Service scope configuration.
	 * 
	 * @author Alexandr Bolbat
	 * 
	 * @param <S>
	 *            service
	 * @param <SF>
	 *            service factory
	 */
	private static class ScopeConfiguration<S extends Service, SF extends ServiceFactory<S>> {

		/**
		 * Empty {@link Configuration} instance.
		 */
		private static final Configuration EMPTY_CONFIGURATION = Configuration.create();

		/**
		 * Service interface.
		 */
		private final Class<S> service;

		/**
		 * Service factory.
		 */
		private final SF serviceFactory;

		/**
		 * Service factory configuration.
		 */
		private final Configuration configuration;

		/**
		 * Service scopes.
		 */
		private final Scope[] scopes;

		/**
		 * Service instance.
		 */
		private S instance;

		/**
		 * Default constructor.
		 * 
		 * @param aService
		 *            service interface
		 * @param aServiceFactory
		 *            service factory
		 * @param aConfiguration
		 *            service factory configuration, can be <code>null</code>
		 * @param aScopes
		 *            service scopes
		 */
		protected ScopeConfiguration(final Class<S> aService, final SF aServiceFactory, final Configuration aConfiguration, final Scope[] aScopes) {
			this.service = aService;
			this.serviceFactory = aServiceFactory;
			this.configuration = aConfiguration != null ? aConfiguration : EMPTY_CONFIGURATION;
			this.scopes = aScopes;
		}

		public Class<S> getService() {
			return service;
		}

		public SF getServiceFactory() {
			return serviceFactory;
		}

		public Configuration getConfiguration() {
			return configuration;
		}

		public S getInstance() {
			return instance;
		}

		public void setInstance(final S aInstance) {
			this.instance = aInstance;
		}

		public String toKey() {
			return service.getName() + DELIMITER + ScopeUtil.scopesToString(scopes);
		}

		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
			builder.append(" [service=").append(service);
			builder.append(", serviceFactory=").append(serviceFactory);
			builder.append(", configuration=").append(configuration);
			builder.append(", scopes=").append(ToStringUtils.toString(scopes));
			builder.append(", instance=").append(instance != null ? instance.getClass().getName() : null);
			builder.append("]");
			return builder.toString();
		}

	}
}
