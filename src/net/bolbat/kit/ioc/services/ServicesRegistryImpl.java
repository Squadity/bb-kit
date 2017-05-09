package net.bolbat.kit.ioc.services;

import static net.bolbat.kit.ioc.Manager.DEFAULT_SCOPE;
import static net.bolbat.utils.lang.Validations.checkArgument;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.bolbat.kit.ioc.scope.Scope;
import net.bolbat.kit.ioc.scope.ScopeUtil;
import net.bolbat.kit.service.Configuration;
import net.bolbat.kit.service.Service;
import net.bolbat.kit.service.ServiceFactory;
import net.bolbat.utils.lang.CastUtils;
import net.bolbat.utils.reflect.Instantiator;

/**
 * {@link ServicesRegistry} implementation.
 * 
 * @author Alexandr Bolbat
 */
public class ServicesRegistryImpl implements ServicesRegistry {

	/**
	 * Services configuration storage.
	 */
	private final ConcurrentMap<String, ServiceConfiguration<?>> storage = new ConcurrentHashMap<>();

	@Override
	public Collection<ServiceConfiguration<?>> getAll() {
		return Collections.unmodifiableCollection(storage.values());
	}

	@Override
	public <S extends Service> ServiceConfiguration<S> get(final String key) {
		return CastUtils.cast(storage.get(key));
	}

	@Override
	public <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final Class<SF> factory) {
		register(service, factory, DEFAULT_SCOPE);
	}

	@Override
	public <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final Class<SF> factory, final Scope... scopes) {
		register(service, factory, Configuration.EMPTY, scopes);
	}

	@Override
	public <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final Class<SF> factory, final Configuration conf) {
		register(service, factory, conf, DEFAULT_SCOPE);
	}

	@Override
	public <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final Class<SF> factory, final Configuration conf,
			final Scope... scopes) {
		final SF instance = Instantiator.instantiate(factory);
		register(service, instance, conf, scopes);
	}

	@Override
	public <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final SF factory) {
		register(service, factory, Configuration.EMPTY, DEFAULT_SCOPE);
	}

	@Override
	public <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final SF factory, final Scope... scopes) {
		register(service, factory, Configuration.EMPTY, scopes);
	}

	@Override
	public <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final SF factory, final Configuration conf) {
		register(service, factory, conf, DEFAULT_SCOPE);
	}

	@Override
	public <S extends Service, SF extends ServiceFactory<S>> void register(final Class<S> service, final SF factory, final Configuration conf,
			final Scope... scopes) {
		checkArgument(service != null, "service argument is null");
		checkArgument(factory != null, "serviceFactory argument is null");

		final ServiceConfiguration<S> sConfig = new ServiceConfiguration<>(service, factory, conf, ScopeUtil.scopesToArray(true, scopes));
		storage.put(sConfig.toKey(), sConfig);
	}

	@Override
	public <S extends Service> void register(final Class<S> service, final S instance) {
		register(service, instance, DEFAULT_SCOPE);
	}

	@Override
	public <S extends Service> void register(final Class<S> service, final S instance, final Scope... scopes) {
		checkArgument(service != null, "service argument is null");
		checkArgument(instance != null, "instance argument is null");

		final ServiceConfiguration<S> sConfig = new ServiceConfiguration<>(service, instance, ScopeUtil.scopesToArray(true, scopes));
		storage.put(sConfig.toKey(), sConfig);
	}

	@Override
	public void clear(final String key) {
		storage.remove(key);
	}

	@Override
	public void clear() {
		storage.clear();
	}

}
