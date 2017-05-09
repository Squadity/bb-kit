package net.bolbat.kit.ioc;

import static net.bolbat.kit.ioc.Manager.DEFAULT_SCOPE;
import static net.bolbat.utils.lang.Validations.checkArgument;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.bolbat.kit.Module;
import net.bolbat.kit.ioc.Manager.Feature;
import net.bolbat.kit.ioc.features.Features;
import net.bolbat.kit.ioc.features.FeaturesImpl;
import net.bolbat.kit.ioc.links.LinksRegistry;
import net.bolbat.kit.ioc.links.LinksRegistryImpl;
import net.bolbat.kit.ioc.scope.Scope;
import net.bolbat.kit.ioc.scope.ScopeUtil;
import net.bolbat.kit.ioc.services.ServiceConfiguration;
import net.bolbat.kit.ioc.services.ServicesRegistry;
import net.bolbat.kit.ioc.services.ServicesRegistryImpl;
import net.bolbat.kit.service.Configuration;
import net.bolbat.kit.service.DynamicServiceFactory;
import net.bolbat.kit.service.Service;
import net.bolbat.kit.service.ServiceFactory;
import net.bolbat.utils.annotation.Mark.ToDo;
import net.bolbat.utils.annotation.Stability.Stable;
import net.bolbat.utils.lang.CastUtils;
import net.bolbat.utils.lang.ToStringUtils;
import net.bolbat.utils.logging.LoggingUtils;
import net.bolbat.utils.reflect.ClassUtils;

/**
 * {@link Manager} module.
 * 
 * @author Alexandr Bolbat
 */
@Stable
public final class ManagerModule implements Module {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerModule.class);

	/**
	 * Synchronization lock.
	 */
	private final Object LOCK = new Object();

	/**
	 * Features configuration.
	 */
	private final Features features = new FeaturesImpl();

	/**
	 * Services configuration.
	 */
	private final ServicesRegistry services = new ServicesRegistryImpl();

	/**
	 * Links configuration.
	 */
	private final LinksRegistry links = new LinksRegistryImpl();

	/**
	 * Package protected constructor.
	 */
	ManagerModule() {
	}

	/**
	 * Get features configuration.
	 * 
	 * @return {@link Features}
	 */
	public Features features() {
		return features;
	}

	/**
	 * Get services configuration.
	 * 
	 * @return {@link LinksRegistry}
	 */
	public ServicesRegistry services() {
		return services;
	}

	/**
	 * Get links configuration.
	 * 
	 * @return {@link LinksRegistry}
	 */
	public LinksRegistry links() {
		return links;
	}

	/**
	 * Get service.<br>
	 * Logic the same as for <code>get</code> method but with {@link ManagerRuntimeException} instead of {@link ManagerException}.
	 * 
	 * @param service
	 *            service interface
	 * @param scopes
	 *            service scopes, default scopes will be selected if no one given
	 * @return service instance
	 */
	public <S extends Service> S getFast(final Class<S> service, final Scope... scopes) {
		try {
			return get(service, scopes);
		} catch (final ManagerException e) {
			final Scope[] scopesArray = ScopeUtil.scopesToArray(true, scopes);
			throw new ManagerRuntimeException("getFast(" + service + ", " + ToStringUtils.toString(scopesArray) + ") failed to obtain service instance", e);
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
	public <S extends Service> S get(final Class<S> service, final Scope... scopes) throws ManagerException {
		checkArgument(service != null, "service argument is null");

		final Scope[] scopesArray = ScopeUtil.scopesToArray(true, scopes);
		ServiceConfiguration<S> conf = resolveConfiguration(service, scopesArray);

		// try to use 'Feature.AUTO_IMPL_DISCOVERY'
		if (conf == null && features().isEnabled(Feature.AUTO_IMPL_DISCOVERY)) {
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
	private <S extends Service> ServiceConfiguration<S> resolveConfiguration(final Class<S> service, final Scope... scopes) {
		final String key = ManagerUtils.resolveKey(service, scopes);
		final ServiceConfiguration<S> conf = services().get(key);
		if (conf != null)
			return conf;

		// resolving by links
		final Scope[] scope = links().get(key);
		if (scope != null)
			return resolveConfiguration(service, scope);

		return null;
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
	private <S extends Service> S getInstance(final ServiceConfiguration<S> conf, final boolean postConstruct) throws ManagerException {
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
	private <S extends Service> void featureAutoImplDiscovery(final Class<S> service, final Scope... scopes) {
		if (scopes.length != 1 && scopes[0] != DEFAULT_SCOPE)
			return;

		// Step[1]
		// resolving by implementation full class name, preparing by naming convention from service name
		// example: service[some.pkg.HelloWorldService] -> implementation[some.pkg.HelloWorldServiceImpl]
		final String serviceClassName = service.getName();
		final String implClassName = serviceClassName + "Impl";
		try {
			final Class<S> implClass = CastUtils.cast(Class.forName(implClassName));
			services().register(service, new DynamicServiceFactory<>(implClass), Configuration.EMPTY, scopes);
			return; // exiting on successful step
		} catch (final ClassNotFoundException e) {
			LoggingUtils.debug(LOGGER, "Step[1]: service[" + serviceClassName + "] implementation[" + implClassName + "] class is not found");
		}

		// Step[2]
		// Step[3]
		// Step[etc]
	}

	/**
	 * Warm up {@link ManagerModule} state.<br>
	 * For registered and not instantiated services 'post-construct' will be processed.
	 */
	public void warmUp() {
		synchronized (LOCK) {
			final List<Object> instances = new ArrayList<>();
			for (final ServiceConfiguration<?> conf : services().getAll()) {
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
	 * Warm up {@link ManagerModule} state for given service.<br>
	 * For registered and not instantiated services 'post-construct' will be processed.
	 * 
	 * @param service
	 *            service interface
	 */
	public <S extends Service> void warmUp(final Class<S> service) {
		synchronized (LOCK) {
			final List<Object> instances = new ArrayList<>();
			for (final ServiceConfiguration<?> conf : services().getAll()) {
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
	 * Tear down {@link ManagerModule} state.<br>
	 * For registered and instantiated services 'pre-destroy' will be processed.
	 */
	public void tearDown() {
		synchronized (LOCK) {
			final List<ServiceConfiguration<?>> values = new ArrayList<>(services().getAll());
			services().clear();
			links().clear();

			// execute pre-destroy
			for (final ServiceConfiguration<?> conf : values)
				if (conf.getInstance() != null)
					ClassUtils.executePreDestroy(conf.getInstance(), true);
		}
	}

	/**
	 * Tear down {@link ManagerModule} state for given service.<br>
	 * For registered and instantiated services 'pre-destroy' will be processed.
	 * 
	 * @param service
	 *            service interface
	 */
	public <S extends Service> void tearDown(final Class<S> service) {
		synchronized (LOCK) {
			final List<ServiceConfiguration<?>> instances = new ArrayList<>();
			for (final ServiceConfiguration<?> conf : services().getAll())
				if (conf.getService().equals(service))
					instances.add(conf);

			// clearing services configuration
			for (final ServiceConfiguration<?> conf : instances) {
				services().clear(conf.toKey());

				// executing services pre-destroy
				if (conf.getInstance() != null)
					ClassUtils.executePreDestroy(conf.getInstance(), true);
			}

			// clearing services links
			links().clear(service);
		}
	}

}
