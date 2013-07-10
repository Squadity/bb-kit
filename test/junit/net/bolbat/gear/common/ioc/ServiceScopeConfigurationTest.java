package net.bolbat.gear.common.ioc;

import net.bolbat.gear.common.service.SampleService;
import net.bolbat.gear.common.service.SampleServiceFactory;
import net.bolbat.gear.common.service.SampleServiceImpl;
import net.bolbat.gear.common.service.ServiceInstantiationException;
import net.bolbat.gear.common.service.ServiceLocatorConfiguration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link ServiceScopeConfiguration} test.
 * 
 * @author Alexandr Bolbat
 */
public class ServiceScopeConfigurationTest {

	/**
	 * Initialization executed before each test.
	 */
	@Before
	public void before() {
	}

	/**
	 * Initialization executed after each test.
	 */
	@After
	public void after() {
	}

	/**
	 * {@link ServiceScopeConfiguration} complex test.
	 */
	@Test
	public void complexTest() {
		Class<SampleService> clazz = SampleService.class;
		SampleServiceFactory factory = new SampleServiceFactory();
		ServiceLocatorConfiguration config = new ServiceLocatorConfiguration();
		Scope[] scopes = new Scope[] { Manager.DEFAULT_SCOPE };

		ServiceScopeConfiguration<SampleService, SampleServiceFactory> configuration = new ServiceScopeConfiguration<SampleService, SampleServiceFactory>(
				clazz, factory, config, scopes);

		Assert.assertEquals(clazz, configuration.getService());
		Assert.assertEquals(factory, configuration.getServiceFactory());

		Assert.assertSame(config, configuration.getConfiguration());

		Assert.assertNotSame(scopes, configuration.getScopes());
		Assert.assertEquals(1, configuration.getScopes().length);
		Assert.assertEquals(scopes[0], configuration.getScopes()[0]);

		Assert.assertEquals(null, configuration.getInstance());

		try {
			configuration.setInstance(configuration.getServiceFactory().create());
		} catch (ServiceInstantiationException e) {
			Assert.fail("No exception should be on this step.");
		}

		Assert.assertTrue(configuration.getInstance() instanceof SampleServiceImpl);

		Assert.assertNotNull(configuration.toString());
		Assert.assertTrue("Should contain", configuration.toString().contains(clazz.getName()));
		Assert.assertTrue("Should contain", configuration.toString().contains(factory.toString()));
	}
}
