package net.bolbat.kit.ioc;

import static net.bolbat.utils.lang.StringUtils.isNotEmpty;
import static net.bolbat.utils.lang.Validations.checkArgument;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.bolbat.kit.Module;
import net.bolbat.kit.ioc.scope.CompositeScope;
import net.bolbat.kit.ioc.scope.CustomScope;
import net.bolbat.kit.ioc.scope.Scope;
import net.bolbat.kit.service.Configuration;
import net.bolbat.kit.service.Service;
import net.bolbat.kit.service.ServiceFactory;
import net.bolbat.utils.annotation.Mark.ToDo;
import net.bolbat.utils.annotation.Stability.Evolving;
import net.bolbat.utils.annotation.Stability.Stable;
import net.bolbat.utils.concurrency.lock.ConcurrentIdBasedLockManager;
import net.bolbat.utils.concurrency.lock.IdBasedLock;
import net.bolbat.utils.concurrency.lock.IdBasedLockManager;

/**
 * Module for managing services configuration.
 * 
 * @author Alexandr Bolbat
 */
@Stable
public final class Manager implements Module {

	/**
	 * Default scope.
	 */
	public static final Scope DEFAULT_SCOPE = CustomScope.get("SYSTEM_DEFAULT_SCOPE");

	/**
	 * Default module name.
	 */
	public static final String DEFAULT_MODULE = "DEFAULT";

	/**
	 * Modules storage.
	 */
	private static final ConcurrentMap<String, ManagerModule> MODULES = new ConcurrentHashMap<>();

	/**
	 * {@link IdBasedLockManager}.
	 */
	private static final IdBasedLockManager<String> LOCK_MANAGER = new ConcurrentIdBasedLockManager<>();

	/**
	 * Private constructor for preventing class instantiation.
	 */
	private Manager() {
		throw new IllegalAccessError("Can't instantiate.");
	}

	/**
	 * Get default module instance.
	 * 
	 * @return {@link ManagerModule}
	 */
	public static ManagerModule defaultModule() {
		return moduleInternally(DEFAULT_MODULE);
	}

	/**
	 * Get module instance.
	 * 
	 * @param name
	 *            module name
	 * @return {@link ManagerModule}
	 */
	public static ManagerModule module(final String name) {
		checkArgument(isNotEmpty(name), "name argument is empty");
		return moduleInternally(name);
	}

	/**
	 * Get module instance internally (without argument validation).
	 * 
	 * @param name
	 *            module name
	 * @return {@link ManagerModule}
	 */
	private static ManagerModule moduleInternally(final String name) {
		ManagerModule defaultModule = MODULES.get(name);
		if (defaultModule == null) {
			final IdBasedLock<String> lock = LOCK_MANAGER.obtainLock(name);
			lock.lock();
			try {
				defaultModule = MODULES.get(name);
				if (defaultModule == null) {
					defaultModule = new ManagerModule();
					MODULES.put(name, defaultModule);
				}
			} finally {
				lock.unlock();
			}
		}
		return defaultModule;
	}

	/**
	 * Make default module link between default and target scope.
	 * 
	 * @param service
	 *            service
	 * @param target
	 *            target scope, can be {@link CompositeScope}
	 */
	public static <S extends Service> void link(final Class<S> service, final Scope target) {
		defaultModule().links().link(service, target);
	}

	/**
	 * Make default module link between scopes.
	 * 
	 * @param service
	 *            service
	 * @param source
	 *            source scope, can be {@link CompositeScope}
	 * @param target
	 *            target scope, can be {@link CompositeScope}
	 */
	public static <S extends Service> void link(final Class<S> service, final Scope source, final Scope target) {
		defaultModule().links().link(service, source, target);
	}

	/**
	 * Register default module service.
	 * 
	 * @param service
	 *            service interface
	 * @param factory
	 *            service factory
	 */
	public static <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final Class<SF> factory) {
		defaultModule().services().register(service, factory);
	}

	/**
	 * Register default module service.
	 * 
	 * @param service
	 *            service interface
	 * @param factory
	 *            service factory
	 * @param scopes
	 *            service scopes, default scopes will be selected if no one given
	 */
	public static <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final Class<SF> factory, final Scope... scopes) {
		defaultModule().services().register(service, factory, scopes);
	}

	/**
	 * Register default module service.
	 * 
	 * @param service
	 *            service interface
	 * @param factory
	 *            service factory
	 * @param conf
	 *            service factory configuration, can be <code>null</code>
	 */
	public static <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final Class<SF> factory, final Configuration conf) {
		defaultModule().services().register(service, factory, conf);
	}

	/**
	 * Register default module service.
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
		defaultModule().services().register(service, factory, conf, scopes);
	}

	/**
	 * Register default module service.
	 * 
	 * @param service
	 *            service interface
	 * @param factory
	 *            service factory instance
	 */
	public static <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final SF factory) {
		defaultModule().services().register(service, factory);
	}

	/**
	 * Register default module service.<br>
	 * Uses <code>ScopeUtil.scopesToArray(true,scopes)</code> upon registration.
	 * 
	 * @param service
	 *            service interface
	 * @param factory
	 *            service factory instance
	 * @param scopes
	 *            service scopes, default scopes will be selected if no one given
	 */
	public static <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final SF factory, final Scope... scopes) {
		defaultModule().services().register(service, factory, scopes);
	}

	/**
	 * Register default module service.
	 * 
	 * @param service
	 *            service interface
	 * @param factory
	 *            service factory instance
	 * @param conf
	 *            service factory configuration, can be <code>null</code>
	 */
	public static <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final SF factory, final Configuration conf) {
		defaultModule().services().register(service, factory, conf);
	}

	/**
	 * Register default module service.<br>
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
		defaultModule().services().register(service, factory, conf, scopes);
	}

	/**
	 * Register default module service.<br>
	 * {@link PostConstruct} will be ignored.<br>
	 * {@link PreDestroy} will be executed during <code>tearDown</code>.
	 * 
	 * @param service
	 *            service interface
	 * @param instance
	 *            service instance
	 */
	public static <S extends Service> void register(final Class<S> service, final S instance) {
		defaultModule().services().register(service, instance);
	}

	/**
	 * Register default module service.<br>
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
	public static <S extends Service> void register(final Class<S> service, final S instance, final Scope... scopes) {
		defaultModule().services().register(service, instance, scopes);
	}

	/**
	 * Get default module service.<br>
	 * Logic the same as for <code>get</code> method but with {@link ManagerRuntimeException} instead of {@link ManagerException}.
	 * 
	 * @param service
	 *            service interface
	 * @param scopes
	 *            service scopes, default scopes will be selected if no one given
	 * @return service instance
	 */
	public static <S extends Service> S getFast(final Class<S> service, final Scope... scopes) {
		return defaultModule().getFast(service, scopes);
	}

	/**
	 * Get default module service.<br>
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
		return defaultModule().get(service, scopes);
	}

	/**
	 * Warm up {@link Manager} default module state.<br>
	 * For registered and not instantiated services 'post-construct' will be processed.
	 */
	public static void warmUp() {
		defaultModule().warmUp();
	}

	/**
	 * Warm up {@link Manager} default module state for given service.<br>
	 * For registered and not instantiated services 'post-construct' will be processed.
	 * 
	 * @param service
	 *            service interface
	 */
	public static <S extends Service> void warmUp(final Class<S> service) {
		defaultModule().warmUp(service);
	}

	/**
	 * Tear down {@link Manager} default module state.<br>
	 * For registered and instantiated services 'pre-destroy' will be processed.
	 */
	public static void tearDown() {
		defaultModule().tearDown();
	}

	/**
	 * Tear down {@link Manager} default module state for given service.<br>
	 * For registered and instantiated services 'pre-destroy' will be processed.
	 * 
	 * @param service
	 *            service interface
	 */
	public static <S extends Service> void tearDown(final Class<S> service) {
		defaultModule().tearDown(service);
	}

	/**
	 * Manager default module features configuration.
	 * 
	 * @author Alexandr Bolbat
	 */
	public static class Features {

		/**
		 * Enable features.
		 * 
		 * @param toEnable
		 *            {@link Feature} array
		 */
		public static final void enable(final Feature... toEnable) {
			defaultModule().features().enable(toEnable);
		}

		/**
		 * Disable features.
		 * 
		 * @param toDisable
		 *            {@link Feature} array
		 */
		public static final void disable(final Feature... toDisable) {
			defaultModule().features().disable(toDisable);
		}

		/**
		 * Is feature enabled.
		 * 
		 * @param feature
		 *            {@link Feature}
		 * @return <code>true</code> if enabled or <code>false</code>
		 */
		public static boolean isEnabled(final Feature feature) {
			return defaultModule().features().isEnabled(feature);
		}

	}

	/**
	 * Configurable features.
	 * 
	 * @author Alexandr Bolbat
	 */
	@ToDo("Better to move this enumeration to 'net.bolbat.kit.ioc.features' package")
	public enum Feature {

		/**
		 * Automatic implementation discovery.<br>
		 * Currently expected to work only for 'Manager.DEFAULT_SCOPE'.
		 */
		@Evolving
		AUTO_IMPL_DISCOVERY;

	}

}
