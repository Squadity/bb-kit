package net.bolbat.kit.ioc.links;

import static net.bolbat.kit.ioc.Manager.DEFAULT_SCOPE;
import static net.bolbat.utils.lang.Validations.checkArgument;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.bolbat.kit.ioc.ManagerUtils;
import net.bolbat.kit.ioc.scope.CompositeScope;
import net.bolbat.kit.ioc.scope.Scope;
import net.bolbat.kit.service.Service;

/**
 * {@link LinksRegistry} implementation.
 * 
 * @author Alexandr Bolbat
 */
public class LinksRegistryImpl implements LinksRegistry {

	/**
	 * Scopes links configuration storage.
	 */
	private final ConcurrentMap<String, Scope[]> storage = new ConcurrentHashMap<>();

	@Override
	public Scope[] get(final String key) {
		return storage.get(key);
	}

	@Override
	public <S extends Service> void link(final Class<S> service, final Scope target) {
		link(service, DEFAULT_SCOPE, target);
	}

	@Override
	public <S extends Service> void link(final Class<S> service, final Scope source, final Scope target) {
		checkArgument(service != null, "service argument is null");
		checkArgument(source != null, "source argument is null");
		checkArgument(target != null, "target argument is null");
		checkArgument(!source.getId().equalsIgnoreCase(target.getId()), "source[" + source + "] and target[" + target + "] scopes is equal.");

		final String sourceKey = ManagerUtils.resolveKey(service, source);
		final Scope[] targetScopes = target instanceof CompositeScope ? CompositeScope.class.cast(target).getScopes() : new Scope[] { target };
		storage.put(sourceKey, targetScopes);
	}

	@Override
	public <S extends Service> void clear(final Class<S> service) {
		final String serviceLinkPrefix = service.getName() + ManagerUtils.DELIMITER;
		for (final Entry<String, Scope[]> linkEntry : storage.entrySet()) {
			if (linkEntry.getKey().startsWith(serviceLinkPrefix))
				storage.remove(linkEntry.getKey());
		}
	}

	@Override
	public void clear() {
		storage.clear();
	}

}
