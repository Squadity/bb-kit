package net.bolbat.kit.cache.guava;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.configureme.annotations.AfterConfiguration;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import com.google.common.cache.CacheBuilder;

import net.bolbat.kit.cache.Cache;
import net.bolbat.kit.cache.LoadFunction;

/**
 * {@link Cache} implementation for guava.
 *
 * @param <K>
 *            key
 * @param <V>
 *            value
 * @author ivanbatura
 */
@ConfigureMe(allfields = false)
public class GuavaCache<K, V> implements Cache<K, V> {

	/**
	 * {@link com.google.common.cache.Cache}.
	 */
	private com.google.common.cache.Cache<K, V> originalCache;
	/**
	 * Initial capacity for cache.
	 */
	@Configure
	private int initialCapacity;

	/**
	 * Maximum capacity for cache.
	 */
	@Configure
	private long maximumCapacity;

	/**
	 * Expiration time after last access in {@code expireAfterAccessTimeUnit}.
	 */
	@Configure
	private Long expireAfterAccess;

	/**
	 * {@link TimeUnit} for {@code expireAfterAccess}.
	 */
	@Configure
	private TimeUnit expireAfterAccessTimeUnit;

	/**
	 * Expiration time after last write in {@code expireAfterWriteTimeUnit}.
	 */
	@Configure
	private Long expireAfterWrite;

	/**
	 * {@link TimeUnit} for {@code expireAfterWrite}.
	 */
	@Configure
	private TimeUnit expireAfterWriteTimeUnit;

	/**
	 * {@link net.bolbat.kit.cache.LoadFunction} to load elements.
	 */
	private LoadFunction<K, V> functionLoad;

	/**
	 * Constructor.
	 *
	 * @param aInitialCapacity
	 *            Initial capacity
	 * @param aMaximumCapacity
	 *            Maximum capacity
	 * @param aExpireAfterAccess
	 *            Expiration time after last access
	 * @param aExpireAfterAccessTimeUnit
	 *            {@link TimeUnit} for access expiration
	 * @param aExpireAfterWrite
	 *            Expiration time after last write
	 * @param aExpireAfterWriteTimeUnit
	 *            {@link TimeUnit} for write expiration
	 * @param aFunctionLoad
	 *            {@link net.bolbat.kit.cache.LoadFunction}
	 * @param skipConfiguration
	 *            is configuration should be skipped
	 */
	protected GuavaCache(final int aInitialCapacity, final long aMaximumCapacity, final Long aExpireAfterAccess, final TimeUnit aExpireAfterAccessTimeUnit,
			final Long aExpireAfterWrite, TimeUnit aExpireAfterWriteTimeUnit, final LoadFunction<K, V> aFunctionLoad, final boolean skipConfiguration) {
		this.initialCapacity = aInitialCapacity;
		this.maximumCapacity = aMaximumCapacity;
		this.expireAfterAccess = aExpireAfterAccess;
		this.expireAfterAccessTimeUnit = aExpireAfterAccessTimeUnit;
		this.expireAfterWrite = aExpireAfterWrite;
		this.expireAfterWriteTimeUnit = aExpireAfterWriteTimeUnit;
		this.functionLoad = aFunctionLoad;

		// configure cache
		if (!skipConfiguration)
			configureCache();
	}

	public void setOriginalCache(com.google.common.cache.Cache<K, V> originalCache) {
		this.originalCache = originalCache;
	}

	public void setInitialCapacity(int aInitialCapacity) {
		this.initialCapacity = aInitialCapacity;
	}

	/**
	 * Use <code>setInitialCapacity</code> instead.
	 * 
	 * @param aInitialCapacity
	 */
	@Deprecated
	public void setInitiateCapacity(int aInitialCapacity) {
		setInitialCapacity(aInitialCapacity);
	}

	public void setMaximumCapacity(long maximumCapacity) {
		this.maximumCapacity = maximumCapacity;
	}

	public void setExpireAfterAccess(Long expireAfterAccess) {
		this.expireAfterAccess = expireAfterAccess;
	}

	public void setExpireAfterAccessTimeUnit(TimeUnit expireAfterAccessTimeUnit) {
		this.expireAfterAccessTimeUnit = expireAfterAccessTimeUnit;
	}

	public void setExpireAfterWrite(Long expireAfterWrite) {
		this.expireAfterWrite = expireAfterWrite;
	}

	public void setExpireAfterWriteTimeUnit(TimeUnit expireAfterWriteTimeUnit) {
		this.expireAfterWriteTimeUnit = expireAfterWriteTimeUnit;
	}

	/**
	 * Cache configuration.
	 */
	@AfterConfiguration
	public void configureCache() {
		// save old cache data
		Map<K, V> oldCache = null;
		if (originalCache != null && originalCache.size() > 0)
			oldCache = new HashMap<>(originalCache.asMap());

		final CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
		if (initialCapacity > 0)
			cacheBuilder.initialCapacity(initialCapacity);
		if (maximumCapacity > 0)
			cacheBuilder.maximumSize(maximumCapacity);
		if (expireAfterAccess != null)
			cacheBuilder.expireAfterAccess(expireAfterAccess, expireAfterAccessTimeUnit);
		if (expireAfterWrite != null)
			cacheBuilder.expireAfterWrite(expireAfterWrite, expireAfterWriteTimeUnit);
		originalCache = cacheBuilder.build();

		// restore old data to new cache
		if (oldCache != null)
			originalCache.putAll(oldCache);
	}

	@Override
	public V get(K key) {
		V result = originalCache.getIfPresent(key);
		if (functionLoad != null && result == null) {
			result = functionLoad.load(key);
			if (result != null)
				put(key, result);
		}
		return result;
	}

	@Override
	public void put(K key, V value) {
		originalCache.put(key, value);
	}

	@Override
	public void invalidate(K key) {
		originalCache.invalidate(key);
	}

	@Override
	public Collection<V> get(Iterable<? extends K> keys) {
		return originalCache.getAllPresent(keys).values();
	}

	@Override
	public Collection<V> getAll() {
		return originalCache.asMap().values();
	}

	@Override
	public Map<K, V> getAllAsMap() {
		return originalCache.asMap();
	}

	@Override
	public void put(Map<K, V> elements) {
		originalCache.putAll(elements);
	}

	@Override
	public void invalidate(Iterable<? extends K> keys) {
		originalCache.invalidateAll(keys);
	}

	@Override
	public void invalidateAll() {
		originalCache.invalidateAll();
	}

	@Override
	public long size() {
		return originalCache.size();
	}

	@Override
	public void cleanUp() {
		originalCache.cleanUp();
	}

	@Override
	public boolean isEmpty() {
		return originalCache == null || originalCache.size() == 0;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append("[originalCache=").append(originalCache);
		sb.append(", initialCapacity=").append(initialCapacity);
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
