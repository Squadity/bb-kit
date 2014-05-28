package net.bolbat.kit.cache;

/**
 * Function to load element.
 *
 * @param <K>
 * 		ket element
 * @param <V>
 * 		value to return
 * @author ivanbatura
 */
public interface LoadFunction<K, V> {

	/**
	 * Load value by {@code key}.
	 *
	 * @param key
	 * 		key type {@link K}
	 * @return value of type {@link V}
	 */
	V load(K key) throws LoadException;
}
