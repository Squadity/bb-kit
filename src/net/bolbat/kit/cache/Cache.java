package net.bolbat.kit.cache;

import java.util.List;
import java.util.Map;

/**
 * In-memory Cache.
 *
 * @param <K>
 * 		key type
 * @param <V>
 * 		value type
 * @author ivanbatura
 */
public interface Cache<K, V> {

	//Crud operation

	/**
	 * Get value by {@code key} from cache.
	 *
	 * @param key
	 * 		key
	 * @return value
	 */
	V get(K key);

	/**
	 * Put {@code value} by {@code key} to cache.
	 *
	 * @param key
	 * 		key
	 * @param value
	 * 		value
	 */
	void put(K key, V value);

	/**
	 * Invalidate element in cache by {@code key}.
	 *
	 * @param key
	 * 		key
	 */
	void invalidate(K key);

	//bulk operations

	/**
	 * Get values by {@code keys}.
	 *
	 * @param keys
	 * 		{@link java.lang.Iterable}
	 * @return {@link List} with values
	 */
	List<V> get(Iterable<? extends K> keys);

	/**
	 * Get all values from cache.
	 *
	 * @return {@link List} with values
	 */
	List<V> getAll();

	/**
	 * Get all values from cache as {@link Map}.
	 *
	 * @return {@link Map} with key/value
	 */
	Map<K, V> getAllAsMap();

	/**
	 * Put {@code elements} to cache.
	 *
	 * @param elements
	 * 		{@link Map} with key/value to cache
	 */
	void put(Map<K, V> elements);

	/**
	 * Invalidate values by {@code keys}.
	 *
	 * @param keys
	 * 		{@link java.lang.Iterable}
	 */
	void invalidate(Iterable<? extends K> keys);

	/**
	 * Invalidate all values in cache.
	 */
	void invalidateAll();

	// management operation

	/**
	 * Get cache size.
	 *
	 * @return size
	 */
	long size();

	/**
	 * Cleanup cache.
	 */
	void cleanUp();

	/**
	 * Is empty.
	 *
	 * @return true if empty, otherwise false
	 */
	boolean isEmpty();
}
