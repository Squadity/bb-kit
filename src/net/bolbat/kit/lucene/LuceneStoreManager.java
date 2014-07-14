package net.bolbat.kit.lucene;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.bolbat.kit.Module;

/**
 * {@link LuceneStore} manager.
 * 
 * @author Alexandr Bolbat
 */
public final class LuceneStoreManager implements Module {

	/**
	 * Storages instances.
	 */
	private static final Map<Class<?>, LuceneStore<?>> IN_MEMORY_IMPL_STORAGES = new ConcurrentHashMap<Class<?>, LuceneStore<?>>();

	/**
	 * Synchronization lock.
	 */
	private static final Object LOCK = new Object();

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private LuceneStoreManager() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Get {@link LuceneStore} by {@link Storable} type.
	 * 
	 * @param type
	 *            {@link Storable} type
	 * @return {@link LuceneCache} instance
	 */
	@SuppressWarnings("unchecked")
	public static <S extends Storable> LuceneStore<S> getStore(final Class<S> type) {
		if (type == null)
			throw new IllegalArgumentException("type argument is null");

		LuceneStore<?> result = IN_MEMORY_IMPL_STORAGES.get(type); // first check
		if (result == null || !type.isAssignableFrom(result.getClass()))
			synchronized (LOCK) {
				result = IN_MEMORY_IMPL_STORAGES.get(type); // second check
				if (result == null || !type.isAssignableFrom(result.getClass())) {
					result = new LuceneStoreInMemoryImpl<S>(type);
					IN_MEMORY_IMPL_STORAGES.put(type, result);
				}
			}

		return (LuceneStore<S>) result;
	}

	/**
	 * Tear down {@link LuceneStoreManager} state.
	 */
	public static void tearDown() {
		IN_MEMORY_IMPL_STORAGES.clear();
	}

	/**
	 * Tear down {@link LuceneStoreManager} state for given {@link Storable} type.
	 * 
	 * @param type
	 *            {@link Storable} type
	 */
	public static <S extends Storable> void tearDown(final Class<S> type) {
		if (type != null)
			IN_MEMORY_IMPL_STORAGES.remove(type);
	}

}
