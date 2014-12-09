package net.bolbat.kit.cache.guava;

import java.util.concurrent.TimeUnit;

import net.bolbat.kit.cache.Cache;
import net.bolbat.kit.cache.CacheBuilder;
import net.bolbat.kit.cache.LoadFunction;
import net.bolbat.utils.lang.StringUtils;

import org.configureme.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link CacheBuilder} implementation for guava.
 *
 * @param <K>
 * 		key type
 * @param <V>
 * 		value type
 * @author ivanbatura
 */

public class GuavaCacheBuilder<K, V> implements CacheBuilder<K, V> {
	
	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(GuavaCacheBuilder.class);

	/**
	 * Initiate capacity for cache.
	 */
	private int initiateCapacity;

	/**
	 * Maximum capacity fro cache.
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
	 * Set {@code initiateCapacity}.
	 *
	 * @param aInitiateCapacity
	 * 		initiate capacity.
	 * @return {@link GuavaCacheBuilder}
	 */
	public GuavaCacheBuilder<K, V> initiateCapacity(int aInitiateCapacity) {
		this.initiateCapacity = aInitiateCapacity;
		return this;
	}

	/**
	 * Set {@code maximumCapacity}.
	 *
	 * @param aMaximumCapacity
	 * 		maximum capacity
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
	 * 		expiration time after last access in {@code expireAfterAccessTimeUnit}
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
	 * 		{@link TimeUnit}
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
	 * 		Expiration time after last write in {@code expireAfterWriteTimeUnit}
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
	 * 		{@link TimeUnit}
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
	 * 		{@link net.bolbat.kit.cache.LoadFunction}
	 * @return {@link GuavaCacheBuilder}
	 */
	public GuavaCacheBuilder<K, V> functionLoad(LoadFunction<K, V> aFunctionLoad) {
		this.functionLoad = aFunctionLoad;
		return this;
	}

	@Override
	public Cache<K, V> build() {
		return new GuavaCache<>(initiateCapacity, maximumCapacity, expireAfterAccess, expireAfterAccessTimeUnit, expireAfterWrite, expireAfterWriteTimeUnit, functionLoad);
	}

	@Override
	public Cache<K, V> build(String configuration) {
		final Cache<K, V> cache = build();
		if (StringUtils.isEmpty(configuration))
			return cache;

		try {
			ConfigurationManager.INSTANCE.configureBeanAs(cache, configuration);
			// CHECKSTYLE:OFF
		} catch (final RuntimeException e) {
			// CHECKSTYLE:ON
			LOGGER.warn("build(" + configuration + ") fail. Can't configure cache[" + cache + "], skipping.");
		}
		return cache;
	}
}
