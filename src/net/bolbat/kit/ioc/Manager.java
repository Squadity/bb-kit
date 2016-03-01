package net.bolbat.kit.ioc;

import static net.bolbat.utils.lang.Validations.checkArgument;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.bolbat.kit.Module;
import net.bolbat.kit.ioc.scope.CompositeScope;
import net.bolbat.kit.ioc.scope.CustomScope;
import net.bolbat.kit.ioc.scope.Scope;
import net.bolbat.kit.ioc.scope.ScopeUtil;
import net.bolbat.kit.service.Configuration;
import net.bolbat.kit.service.DynamicServiceFactory;
import net.bolbat.kit.service.Service;
import net.bolbat.kit.service.ServiceFactory;
import net.bolbat.utils.annotation.Mark.ToDo;
import net.bolbat.utils.annotation.Stability.Evolving;
import net.bolbat.utils.annotation.Stability.Stable;
import net.bolbat.utils.lang.CastUtils;
import net.bolbat.utils.lang.ToStringUtils;
import net.bolbat.utils.logging.LoggingUtils;
import net.bolbat.utils.reflect.ClassUtils;
import net.bolbat.utils.reflect.Instantiator;

/**
 * Module for managing services configuration.
 * 
 * @author Alexandr Bolbat
 */
@Stable
public final class Manager implements Module {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Manager.class);

	/**
	 * Synchronization lock.
	 */
	private static final Object LOCK = new Object();

	/**
	 * Services configuration storage.
	 */
	private static final ConcurrentMap<String, ScopeConfiguration<?>> STORAGE = new ConcurrentHashMap<>();

	/**
	 * Scopes links configuration storage.
	 */
	private static final ConcurrentMap<String, Scope[]> LINKS = new ConcurrentHashMap<>();

	/**
	 * Key delimiter.
	 */
	private static final String DELIMITER = "_";

	/**
	 * Default scope.
	 */
	public static final Scope DEFAULT_SCOPE = CustomScope.get("SYSTEM_DEFAULT_SCOPE");

	/**
	 * Static initialization.
	 */
	static {
		Features.enable(Feature.AUTO_IMPL_DISCOVERY);
	}

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
		checkArgument(service != null, "service argument is null");
		checkArgument(target != null, "target argument is null");

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
		checkArgument(service != null, "service argument is null");
		checkArgument(source != null, "source argument is null");
		checkArgument(target != null, "target argument is null");
		checkArgument(!source.getId().equalsIgnoreCase(target.getId()), "source[" + source + "] and target[" + target + "] scopes is equal.");

		final String sourceKey = resolveKey(service, source);
		final Scope[] targetScopes = target instanceof CompositeScope ? CompositeScope.class.cast(target).getScopes() : new Scope[] { target };
		synchronized (LOCK) {
			LINKS.put(sourceKey, targetScopes);
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
	public static <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final SF factory, final Configuration conf,
			final Scope... scopes) {
		checkArgument(service != null, "service argument is null");
		checkArgument(factory != null, "serviceFactory argument is null");

		final ScopeConfiguration<S> serviceConfiguration = new ScopeConfiguration<>(service, factory, conf, ScopeUtil.scopesToArray(true, scopes));
		synchronized (LOCK) {
			STORAGE.put(serviceConfiguration.toKey(), serviceConfiguration);
		}
	}

	/**
	 * Get service.<br>
	 * Uses <code>ScopeUtil.scopesToArray(true,scopes)</code> upon resolving.
	 * 
	 * @param service
	 *            service interface
	 * @param scopes
	 *            service scopes, default scopes will be selected if no one given
	 * @return service instance
	 * @throws ManagerException
	 */
	public static <S extends Service> S get(final Class<S> service, final Scope... scopes) throws ManagerException {
		checkArgument(service != null, "service argument is null");

		final Scope[] scopesArray = ScopeUtil.scopesToArray(true, scopes);
		ScopeConfiguration<S> conf = resolveConfiguration(service, scopesArray);

		// try to use 'Feature.AUTO_IMPL_DISCOVERY'
		if (conf == null && Features.isEnabled(Feature.AUTO_IMPL_DISCOVERY)) {
			featureAutoImplDiscovery(service, scopesArray);
			conf = resolveConfiguration(service, scopesArray);
		}

		// configuration is not found
		if (conf == null)
			throw new ConfigurationNotFoundException();

		return getInstance(conf, true);
	}

	/**
	 * Resolve service configuration.
	 * 
	 * @param service
	 *            service
	 * @param scopes
	 *            scopes
	 * @return {@link ScopeConfiguration} or <code>null</code>
	 */
	private static <S extends Service> ScopeConfiguration<S> resolveConfiguration(final Class<S> service, final Scope... scopes) {
		final String key = resolveKey(service, scopes);
		final ScopeConfiguration<S> conf = CastUtils.cast(STORAGE.get(key));
		if (conf != null)
			return conf;

		// resolving by links
		final Scope[] scope = LINKS.get(key);
		if (scope != null)
			return resolveConfiguration(service, scope);

		return null;
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
	private static <S extends Service> String resolveKey(final Class<S> service, final Scope... scopes) {
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
	private static <S extends Service> String resolveKey(final Class<S> service, final Scope scope) {
		final String id = scope instanceof CompositeScope ? scope.getId() : ScopeUtil.scopesToString(scope);
		return service.getName() + DELIMITER + id;
	}

	/**
	 * Get service instance.
	 * 
	 * @param conf
	 *            service scope configuration
	 * @param postConstruct
	 *            is post-construct should be executed
	 * @return service instance
	 * @throws ManagerException
	 */
	private static <S extends Service> S getInstance(final ScopeConfiguration<S> conf, final boolean postConstruct) throws ManagerException {
		if (conf.getInstance() != null) // returning fast as possible if instance already initialized
			return conf.getInstance();

		synchronized (LOCK) {
			if (conf.getInstance() != null)
				return conf.getInstance();

			final ServiceFactory<S> factory = conf.getFactory();

			try {
				final S instance = factory.create(conf.getFactoryConf());

				// updating scope configuration with already obtained service instance
				conf.setInstance(instance);

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
	 * Implementation of 'Feature.AUTO_IMPL_DISCOVERY'.
	 * 
	 * @param service
	 *            service
	 * @param scopes
	 *            scopes
	 */
	@ToDo("Improve way how features support work inside manager, and implement other steps")
	private static <S extends Service> void featureAutoImplDiscovery(final Class<S> service, final Scope... scopes) {
		if (scopes.length != 1 && scopes[0] != Manager.DEFAULT_SCOPE)
			return;

		// Step[1]
		// resolving by implementation full class name, preparing by naming convention from service name
		// example: service[some.pkg.HelloWorldService] -> implementation[some.pkg.HelloWorldServiceImpl]
		final String serviceClassName = service.getName();
		final String implClassName = serviceClassName + "Impl";
		try {
			final Class<S> implClass = CastUtils.cast(Class.forName(implClassName));
			final DynamicServiceFactory<S> implClassFactory = new DynamicServiceFactory<>(implClass);
			final ScopeConfiguration<S> serviceConfiguration = new ScopeConfiguration<>(service, implClassFactory, Configuration.EMPTY, scopes);
			synchronized (LOCK) {
				STORAGE.put(serviceConfiguration.toKey(), serviceConfiguration);
			}
			return; // exiting on successful step
		} catch (final ClassNotFoundException e) {
			LoggingUtils.debug(LOGGER, "Step[1]: service[" + serviceClassName + "] implementation[" + implClassName + "] class is not found");
		}

		// Step[2]
		// Step[3]
		// Step[etc]
	}

	/**
	 * Warm up {@link Manager} state.<br>
	 * For registered and not instantiated services 'post-construct' will be processed.
	 */
	public static void warmUp() {
		synchronized (LOCK) {
			final List<Object> instances = new ArrayList<>();
			for (final ScopeConfiguration<?> conf : STORAGE.values()) {
				if (conf.getInstance() != null)
					continue;

				try {
					instances.add(getInstance(conf, false));
				} catch (final ManagerException e) {
					throw new ManagerRuntimeException("Can't warm up", e);
				}
			}

			// execute post-construct
			for (final Object instance : instances)
				try {
					ClassUtils.executePostConstruct(instance, true);
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
			for (final ScopeConfiguration<?> conf : STORAGE.values()) {
				if (!conf.getService().equals(service) || conf.getInstance() != null)
					continue;

				try {
					instances.add(getInstance(conf, false));
				} catch (final ManagerException e) {
					throw new ManagerRuntimeException("Can't warm up", e);
				}
			}

			// execute post-construct
			for (final Object instance : instances)
				try {
					ClassUtils.executePostConstruct(instance, true);
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
			final List<ScopeConfiguration<?>> values = new ArrayList<>(STORAGE.values());
			STORAGE.clear();
			LINKS.clear();

			// execute pre-destroy
			for (final ScopeConfiguration<?> conf : values)
				if (conf.getInstance() != null)
					ClassUtils.executePreDestroy(conf.getInstance(), true);
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
			final List<ScopeConfiguration<?>> instances = new ArrayList<>();
			for (final ScopeConfiguration<?> conf : STORAGE.values())
				if (conf.getService().equals(service))
					instances.add(conf);

			// clearing services configuration
			for (final ScopeConfiguration<?> conf : instances) {
				STORAGE.remove(conf.toKey());

				// executing services pre-destroy
				if (conf.getInstance() != null)
					ClassUtils.executePreDestroy(conf.getInstance(), true);
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
	 */
	private static class ScopeConfiguration<S extends Service> {

		/**
		 * Service interface.
		 */
		private final Class<S> service;

		/**
		 * Service factory.
		 */
		private final ServiceFactory<S> factory;

		/**
		 * Service factory configuration.
		 */
		private final Configuration factoryConf;

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
		 * @param aFactory
		 *            service factory
		 * @param aFactoryConf
		 *            service factory configuration, can be <code>null</code>
		 * @param aScopes
		 *            service scopes
		 */
		protected ScopeConfiguration(final Class<S> aService, final ServiceFactory<S> aFactory, final Configuration aFactoryConf, final Scope[] aScopes) {
			this.service = aService;
			this.factory = aFactory;
			this.factoryConf = aFactoryConf != null ? aFactoryConf : Configuration.EMPTY;
			this.scopes = aScopes;
		}

		public Class<S> getService() {
			return service;
		}

		public ServiceFactory<S> getFactory() {
			return factory;
		}

		public Configuration getFactoryConf() {
			return factoryConf;
		}

		public S getInstance() {
			return instance;
		}

		public void setInstance(final S aInstance) {
			this.instance = aInstance;
		}

		public String toKey() {
			return resolveKey(service, scopes);
		}

		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
			builder.append(" [service=").append(service);
			builder.append(", factory=").append(factory);
			builder.append(", factoryConf=").append(factoryConf);
			builder.append(", scopes=").append(ToStringUtils.toString(scopes));
			builder.append(", instance=").append(instance != null ? instance.getClass().getName() : null);
			builder.append("]");
			return builder.toString();
		}

	}

	/**
	 * Manager features configuration.
	 * 
	 * @author Alexandr Bolbat
	 */
	public static class Features {

		/**
		 * Synchronization lock.
		 */
		private static final Object LOCK = new Object();

		/**
		 * Configuration storage.
		 */
		private static transient Map<Feature, Boolean> features = new EnumMap<>(Feature.class);

		/**
		 * Enable features.
		 * 
		 * @param toEnable
		 *            {@link Feature} array
		 */
		public static final void enable(final Feature... toEnable) {
			if (toEnable == null || toEnable.length == 0)
				return;

			synchronized (LOCK) {
				final Map<Feature, Boolean> newConfiguration = new EnumMap<>(features);
				for (final Feature feature : toEnable)
					if (feature != null)
						newConfiguration.put(feature, Boolean.TRUE);

				features = newConfiguration;
			}
		}

		/**
		 * Disable features.
		 * 
		 * @param toDisable
		 *            {@link Feature} array
		 */
		public static final void disable(final Feature... toDisable) {
			if (toDisable == null || toDisable.length == 0)
				return;

			synchronized (LOCK) {
				final Map<Feature, Boolean> newConfiguration = new EnumMap<>(features);
				for (final Feature feature : toDisable)
					if (feature != null)
						newConfiguration.put(feature, Boolean.FALSE);

				features = newConfiguration;
			}
		}

		/**
		 * Is feature enabled.
		 * 
		 * @param feature
		 *            {@link Feature}
		 * @return <code>true</code> if enabled or <code>false</code>
		 */
		public static boolean isEnabled(final Feature feature) {
			return Boolean.TRUE == features.get(feature);
		}

	}

	/**
	 * Configurable features.
	 * 
	 * @author Alexandr Bolbat
	 */
	public enum Feature {

		/**
		 * Automatic implementation discovery.<br>
		 * Currently expected to work only for 'Manager.DEFAULT_SCOPE'.
		 */
		@Evolving AUTO_IMPL_DISCOVERY;

	}

}
