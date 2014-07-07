package net.bolbat.kit.config;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;
import org.configureme.annotations.DontConfigure;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link ConfigurationManager} module test.
 * 
 * @author Alexandr Bolbat
 */
public class ConfigurationManagerTest {

	/**
	 * Initialization executed before each test.
	 */
	@Before
	public void before() {
		ConfigurationManager.tearDown();
	}

	/**
	 * Initialization executed after each test.
	 */
	@After
	public void after() {
		ConfigurationManager.tearDown();
	}

	/**
	 * Complex test.
	 */
	@Test
	public void complexTest() {
		// expected behavior
		final MockConfiguration instance1 = ConfigurationManager.getInstance(MockConfiguration.class);
		Assert.assertNotNull(instance1);
		Assert.assertEquals("configured value", instance1.getValue());
		Assert.assertNotEquals(MockConfiguration.DEF_VALUE, instance1.getValue());

		// default value if configuration not exist
		final MockConfiguration instance2 = ConfigurationManager.getInstanceForConf(MockConfiguration.class, "not-exist");
		Assert.assertNotNull(instance2);
		Assert.assertEquals(MockConfiguration.DEF_VALUE, instance2.getValue());
	}

	/**
	 * Caching test.
	 */
	@Test
	public void cachingTest() {
		// check caching for default configuration
		final MockConfiguration instance1 = ConfigurationManager.getInstance(MockConfiguration.class);
		Assert.assertNotNull(instance1);
		Assert.assertSame(instance1, ConfigurationManager.getInstance(MockConfiguration.class));

		// if configuration not exist
		final MockConfiguration customConfInstance = ConfigurationManager.getInstanceForConf(MockConfiguration.class, "custom-config");
		Assert.assertNotNull(customConfInstance);
		Assert.assertNotSame(customConfInstance, instance1);
		Assert.assertSame(customConfInstance, ConfigurationManager.getInstanceForConf(MockConfiguration.class, "custom-config"));

		// custom environment
		final MockConfiguration customEnvInstance = ConfigurationManager.getInstanceForEnv(MockConfiguration.class, "custom-env");
		Assert.assertNotNull(customEnvInstance);
		Assert.assertNotSame(customEnvInstance, instance1);
		Assert.assertNotSame(customEnvInstance, customConfInstance);
		Assert.assertSame(customEnvInstance, ConfigurationManager.getInstanceForEnv(MockConfiguration.class, "custom-env"));

		// both
		final MockConfiguration customInstance = ConfigurationManager.getInstance(MockConfiguration.class, "custom-config", "custom-env");
		Assert.assertNotNull(customInstance);
		Assert.assertNotSame(customInstance, instance1);
		Assert.assertNotSame(customInstance, customConfInstance);
		Assert.assertNotSame(customInstance, customEnvInstance);
		Assert.assertSame(customInstance, ConfigurationManager.getInstance(MockConfiguration.class, "custom-config", "custom-env"));
	}

	/**
	 * Error cases test.
	 */
	@Test
	public void errorCasesTest() {
		try {
			ConfigurationManager.getInstance(null, null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("type"));
		}
	}

	/**
	 * Mock configuration for testing purposes.
	 * 
	 * @author Alexandr Bolbat
	 */
	@ConfigureMe(name = "mock-configuration")
	public static class MockConfiguration {

		/**
		 * Default value.
		 */
		@DontConfigure
		public static final String DEF_VALUE = "DEFAULT";

		/**
		 * Testing value;
		 */
		@Configure
		private String value = DEF_VALUE;

		public String getValue() {
			return value;
		}

		public void setValue(final String aValue) {
			this.value = aValue;
		}

	}

}
