package net.bolbat.kit.cache.guava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.bolbat.kit.cache.Cache;
import net.bolbat.kit.cache.LoadException;
import net.bolbat.kit.cache.LoadFunction;
import org.junit.Assert;
import org.junit.Test;

/**
 * Guava cache test.
 *
 * @author ivanbatura
 */
public class GuavaCacheTest {

	private Cache<String, String> cache;

	@Test
	public void crudOperationsTest() throws LoadException {
		//preparation
		GuavaCacheBuilder<String, String> cacheBuilder = new GuavaCacheBuilder<String, String>();
		cacheBuilder.initiateCapacity(10);
		cacheBuilder.maximumCapacity(100);
		cache = cacheBuilder.build();

		final String key = "key";
		final String value = "value";
		//call test method
		String result = cache.get(key);
		Assert.assertNull("Result should be null", result);
		cache.put(key, value);
		//verification
		result = cache.get(key);
		Assert.assertEquals("Result is not correct", value, result);
		cache.invalidate(key);
		result = cache.get(key);
		Assert.assertNull("Result should be null", result);
	}

	@Test
	public void bulkOperationsTest() {
		//preparation
		GuavaCacheBuilder<String, String> cacheBuilder = new GuavaCacheBuilder<String, String>();
		cacheBuilder.initiateCapacity(10);
		cacheBuilder.maximumCapacity(100);
		cache = cacheBuilder.build();

		final String key1 = "key1";
		final String value1 = "value1";
		final String key2 = "key2";
		final String value2 = "value2";
		final List<String> keys = new ArrayList<String>(Arrays.asList(key1, key2));
		//call test method
		List<String> results = cache.get(keys);
		Assert.assertNotNull("Result should not be null", results);
		Assert.assertEquals("Result should be empty", 0, results.size());
		cache.put(key1, value1);
		cache.put(key2, value2);
		//verification
		results = cache.get(keys);
		Assert.assertNotNull("Result should not be null", results);
		Assert.assertEquals("Result should be empty", 2, results.size());
		cache.invalidate(keys);
		results = cache.get(keys);
		Assert.assertNotNull("Result should not be null", results);
		Assert.assertEquals("Result should be empty", 0, results.size());
	}

	@Test
	public void configurationTest() throws LoadException {
		GuavaCacheBuilder<String, String> cacheBuilder = new GuavaCacheBuilder<String, String>();
		Cache<String, String> cacheConfigurable = cacheBuilder.build("cache-configuration-guava");
		Assert.assertNotNull("Result should not be null", cacheConfigurable);

		final String key = "key1";
		final String value = "value1";
		cacheConfigurable.put(key, value);
		//verification
		String result = cacheConfigurable.get(key);
		Assert.assertEquals("Result is not correct", value, result);
		cacheConfigurable.invalidate(key);
		result = cacheConfigurable.get(key);
		Assert.assertNull("Result should be null", result);

	}

	@Test
	public void functionTest() throws LoadException {
		//preparation
		GuavaCacheBuilder<String, String> cacheBuilder = new GuavaCacheBuilder<String, String>();
		cacheBuilder.initiateCapacity(1);
		cacheBuilder.maximumCapacity(2);
		cacheBuilder.functionLoad(new LoadFunction<String, String>() {
			@Override
			public String load(String key) throws LoadException{
				return "value";
			}
		});
		cache = (GuavaCache<String, String>) cacheBuilder.build();

		final String key1 = "key1";
		//call test method
		String result = cache.get(key1);
		Assert.assertNotNull("Result should not be null", result);
		Assert.assertEquals("Result should be empty", "value", result);
	}


}
