package net.bolbat.kit.cache;

/**
 * Cache builder for {@link Cache}.
 *
 * @param <K>
 * 		key type
 * @param <V>
 * 		value type
 * @author ivanbatura
 */
public interface CacheBuilder<K, V> {

	/**
	 * Build Cache with provided init variables.
	 *
	 * @return {@link Cache}
	 */
	Cache<K, V> build();

	/**
	 * Build Cache with provided configuration.
	 *
	 * @param configurationName
	 * 		configuration name
	 * @return {@link Cache}
	 */
	Cache<K, V> build(String configurationName);
}
