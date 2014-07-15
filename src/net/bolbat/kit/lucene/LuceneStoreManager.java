package net.bolbat.kit.lucene;

import static net.bolbat.utils.lang.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
	private static final Map<String, LuceneStore<?>> STORAGE = new ConcurrentHashMap<String, LuceneStore<?>>();

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
	 * Get {@link LuceneStore} by {@link Storable} type with default configuration.
	 * 
	 * @param type
	 *            {@link Storable} type
	 * @return {@link LuceneCache} instance
	 */
	public static <S extends Storable> LuceneStore<S> getStore(final Class<S> type) {
		return getStore(type, LuceneStoreConfig.DEFAULT_CONFIGURATION_NAME);
	}

	/**
	 * Get {@link LuceneStore} by {@link Storable} type with with given configuration.
	 * 
	 * @param type
	 *            {@link Storable} type
	 * @param configuration
	 *            {@link LuceneStoreConfig} name
	 * @return {@link LuceneCache} instance
	 */
	@SuppressWarnings("unchecked")
	public static <S extends Storable> LuceneStore<S> getStore(final Class<S> type, final String configuration) {
		if (type == null)
			throw new IllegalArgumentException("type argument is null");

		final String conf = isEmpty(configuration) ? LuceneStoreConfig.DEFAULT_CONFIGURATION_NAME : configuration;
		final String storeKey = type.getName() + "_" + conf;

		LuceneStore<?> result = STORAGE.get(storeKey); // first check
		if (result == null)
			synchronized (LOCK) {
				result = STORAGE.get(storeKey); // second check
				if (result == null) {
					result = new LuceneStoreImpl<S>(type, conf);
					STORAGE.put(storeKey, result);
				}
			}

		return (LuceneStore<S>) result;
	}

	/**
	 * Get all initialized {@link LuceneStore} identifiers.
	 * 
	 * @return {@link Set} with {@link LuceneStore} identifiers
	 */
	public static Set<String> getStoresIds() {
		return new HashSet<String>(STORAGE.keySet());
	}

	/**
	 * Get all initialized {@link LuceneStore}.
	 * 
	 * @return {@link List} with {@link LuceneStore}
	 */
	public static List<LuceneStore<?>> getStores() {
		return new ArrayList<LuceneStore<?>>(STORAGE.values());
	}

	/**
	 * Tear down {@link LuceneStoreManager} state.<br>
	 * This is just for removing all initialized {@link LuceneStore} instances from internal storage.<br>
	 * Tear down/cleanup for initialized {@link LuceneStore} instances should be done separately.
	 */
	public static void tearDown() {
		STORAGE.clear();
	}

	/**
	 * Tear down {@link LuceneStoreManager} state for given {@link Storable} type.<br>
	 * This is just for removing initialized {@link LuceneStore} instance from internal storage.<br>
	 * Tear down/cleanup for initialized {@link LuceneStore} instance should be done separately.
	 * 
	 * @param type
	 *            {@link Storable} type
	 */
	public static <S extends Storable> void tearDown(final Class<S> type) {
		tearDown(type, LuceneStoreConfig.DEFAULT_CONFIGURATION_NAME);
	}

	/**
	 * Tear down {@link LuceneStoreManager} state for given {@link Storable} type.<br>
	 * This is just for removing initialized {@link LuceneStore} instance from internal storage.<br>
	 * Tear down/cleanup for initialized {@link LuceneStore} instance should be done separately.
	 * 
	 * @param type
	 *            {@link Storable} type
	 * @param configuration
	 *            {@link LuceneStoreConfig} name
	 */
	public static <S extends Storable> void tearDown(final Class<S> type, final String configuration) {
		if (type == null)
			return;

		final String conf = isEmpty(configuration) ? LuceneStoreConfig.DEFAULT_CONFIGURATION_NAME : configuration;
		final String storeKey = type.getName() + "_" + conf;
		STORAGE.remove(storeKey);
	}

}
