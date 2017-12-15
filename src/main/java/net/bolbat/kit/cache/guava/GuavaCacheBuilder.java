package net.bolbat.kit.cache.guava;

import java.util.concurrent.TimeUnit;

import org.configureme.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.bolbat.kit.cache.Cache;
import net.bolbat.kit.cache.CacheBuilder;
import net.bolbat.kit.cache.LoadFunction;
import net.bolbat.utils.lang.StringUtils;

/**
 * {@link CacheBuilder} implementation for guava.
 *
 * @param <K>
 *            key type
 * @param <V>
 *            value type
 * @author ivanbatura
 */

public class GuavaCacheBuilder<K, V> implements CacheBuilder<K, V> {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(GuavaCacheBuilder.class);

	/**
	 * Initial capacity for cache.
	 */
	private int initialCapacity;

	/**
	 * Maximum capacity for cache.
	 */
	private long maximumCapacity;

	/**
	 * Expiration time after last access in {@code expireAfterAccessTimeUnit}.
	 */
	private Long expireAfterAccess;

	/**
	 * {@link TimeUnit} for {@code expireAfterAccess}.
	 */
	private TimeUnit expireAfterAccessTimeUnit;

	/**
	 * Expiration time after last write in {@code expireAfterWriteTimeUnit}.
	 */
	private Long expireAfterWrite;

	/**
	 * {@link TimeUnit} for {@code expireAfterWrite}.
	 */
	private TimeUnit expireAfterWriteTimeUnit;

	/**
	 * {@link net.bolbat.kit.cache.LoadFunction} to load elements.
	 */
	private LoadFunction<K, V> functionLoad;

	/**
	 * Set {@code initialCapacity}.
	 *
	 * @param aInitialCapacity
	 *            initial capacity.
	 * @return {@link GuavaCacheBuilder}
	 */
	public GuavaCacheBuilder<K, V> initialCapacity(int aInitialCapacity) {
		this.initialCapacity = aInitialCapacity;
		return this;
	}

	/**
	 * Use <code>setInitialCapacity</code> instead.
	 *
	 * @param aInitialCapacity
	 *            initial capacity.
	 * @return {@link GuavaCacheBuilder}
	 */
	@Deprecated
	public GuavaCacheBuilder<K, V> initiateCapacity(int aInitialCapacity) {
		return initialCapacity(aInitialCapacity);
	}

	/**
	 * Set {@code maximumCapacity}.
	 *
	 * @param aMaximumCapacity
	 *            maximum capacity
	 * @return {@link GuavaCacheBuilder}
	 */
	public GuavaCacheBuilder<K, V> maximumCapacity(long aMaximumCapacity) {
		this.maximumCapacity = aMaximumCapacity;
		return this;
	}

	/**
	 * Set {@code expireAfterAccess}.
	 *
	 * @param aExpireAfterAccess
	 *            expiration time after last access in {@code expireAfterAccessTimeUnit}
	 * @return {@link GuavaCacheBuilder}
	 */
	public GuavaCacheBuilder<K, V> expireAfterAccess(Long aExpireAfterAccess) {
		this.expireAfterAccess = aExpireAfterAccess;
		return this;
	}

	/**
	 * Set {@code expireAfterAccessTimeUnit}.
	 *
	 * @param aExpireAfterAccessTimeUnit
	 *            {@link TimeUnit}
	 * @return {@link GuavaCacheBuilder}
	 */
	public GuavaCacheBuilder<K, V> expireAfterAccessTimeUnit(TimeUnit aExpireAfterAccessTimeUnit) {
		this.expireAfterAccessTimeUnit = aExpireAfterAccessTimeUnit;
		return this;
	}

	/**
	 * Set {@code expireAfterWrite}.
	 *
	 * @param aExpireAfterWrite
	 *            Expiration time after last write in {@code expireAfterWriteTimeUnit}
	 * @return {@link GuavaCacheBuilder}
	 */
	public GuavaCacheBuilder<K, V> expireAfterWrite(Long aExpireAfterWrite) {
		this.expireAfterWrite = aExpireAfterWrite;
		return this;
	}

	/**
	 * Set {@code expireAfterWriteTimeUnit}.
	 *
	 * @param aExpireAfterWriteTimeUnit
	 *            {@link TimeUnit}
	 * @return {@link GuavaCacheBuilder}
	 */
	public GuavaCacheBuilder<K, V> expireAfterWriteTimeUnit(TimeUnit aExpireAfterWriteTimeUnit) {
		this.expireAfterWriteTimeUnit = aExpireAfterWriteTimeUnit;
		return this;
	}

	/**
	 * Set {@code functionLoad}.
	 *
	 * @param aFunctionLoad
	 *            {@link net.bolbat.kit.cache.LoadFunction}
	 * @return {@link GuavaCacheBuilder}
	 */
	public GuavaCacheBuilder<K, V> functionLoad(LoadFunction<K, V> aFunctionLoad) {
		this.functionLoad = aFunctionLoad;
		return this;
	}

	@Override
	public Cache<K, V> build() {
		return build(false);
	}

	/**
	 * Create cache wrapper.
	 * 
	 * @param skipConfiguration
	 *            is configuration should be skipped
	 */
	private GuavaCache<K, V> build(final boolean skipConfiguration) {
		return new GuavaCache<>(initialCapacity, maximumCapacity, expireAfterAccess, expireAfterAccessTimeUnit, expireAfterWrite, expireAfterWriteTimeUnit,
				functionLoad, skipConfiguration);
	}

	@Override
	public Cache<K, V> build(final String configuration) {
		final GuavaCache<K, V> cache = build(true);
		if (StringUtils.isEmpty(configuration)) {
			cache.configureCache();
			return cache;
		}

		try {
			ConfigurationManager.INSTANCE.configureAs(cache, configuration);
			// CHECKSTYLE:OFF
		} catch (final RuntimeException e) {
			// CHECKSTYLE:ON
			LOGGER.warn("build(" + configuration + ") fail. Can't configure cache[" + cache + "], skipping.");
			cache.configureCache();
		}
		return cache;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append("[initialCapacity=").append(initialCapacity);
		sb.append(", maximumCapacity=").append(maximumCapacity);
		sb.append(", expireAfterAccess=").append(expireAfterAccess);
		sb.append(", expireAfterAccessTimeUnit=").append(expireAfterAccessTimeUnit);
		sb.append(", expireAfterWrite=").append(expireAfterWrite);
		sb.append(", expireAfterWriteTimeUnit=").append(expireAfterWriteTimeUnit);
		sb.append(", functionLoad=").append(functionLoad);
		sb.append(']');
		return sb.toString();
	}
}
