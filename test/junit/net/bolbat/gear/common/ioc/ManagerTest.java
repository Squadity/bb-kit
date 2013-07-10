package net.bolbat.gear.common.ioc;

import net.bolbat.gear.common.service.SampleService;
import net.bolbat.gear.common.service.SampleServiceException;
import net.bolbat.gear.common.service.SampleServiceFactory;
import net.bolbat.gear.common.service.SampleServiceImpl;
import net.bolbat.gear.common.service.SampleServiceLocator;
import net.bolbat.gear.common.service.SampleServiceRemoteImpl;
import net.bolbat.gear.common.service.ServiceFactory;
import net.bolbat.gear.common.service.ServiceLocatorConfiguration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link Manager} module test.
 * 
 * @author Alexandr Bolbat
 */
public class ManagerTest {

	/**
	 * Initialization executed before each test.
	 */
	@Before
	public void before() {
		Manager.tearDown();
	}

	/**
	 * Initialization executed after each test.
	 */
	@After
	public void after() {
		Manager.tearDown();
	}

	/**
	 * Service configuration module complex test.
	 */
	@Test
	public void complexTest() {
		// checking clean configuration
		try {
			Manager.get(SampleService.class);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (ManagerException e) {
			Assert.assertTrue("Right exception instance should be there.", e instanceof ServiceScopeConfigurationNotFoundException);
		}

		// configuring local service
		Manager.register(SampleService.class, SampleServiceFactory.class, ServiceDistributionScope.LOCAL, ServiceTypeScope.BUSINESS_SERVICE);

		// defining additional custom scope for remote sample service
		ServiceCustomScope customScope = new ServiceCustomScope("SAMPLE");

		// configuring remote service with obtaining trough service locator with custom configuration
		ServiceLocatorConfiguration configuration = new ServiceLocatorConfiguration();
		configuration.addParameter("PARAM1", "configured parameter");
		Manager.register(SampleService.class, SampleServiceLocator.class, configuration, customScope, ServiceDistributionScope.REMOTE,
				ServiceTypeScope.BUSINESS_SERVICE);

		// checking local service
		try {
			SampleService localInstance = Manager.get(SampleService.class, ServiceTypeScope.BUSINESS_SERVICE, ServiceDistributionScope.LOCAL, null);
			Assert.assertTrue(localInstance instanceof SampleServiceImpl);
			Assert.assertEquals("CREATED", localInstance.getCreationMethod());
		} catch (ManagerException e) {
			Assert.fail("No exception should be on this step.");
		} catch (SampleServiceException e) {
			Assert.fail("No exception should be on this step.");
		}

		// checking remote service
		try {
			SampleService remoteInstance = Manager.get(SampleService.class, ServiceTypeScope.BUSINESS_SERVICE, null, customScope,
					ServiceDistributionScope.REMOTE);
			Assert.assertTrue(remoteInstance instanceof SampleServiceRemoteImpl);
			Assert.assertEquals("LOCATED. PARAMETER: configured parameter", remoteInstance.getCreationMethod());
		} catch (ManagerException e) {
			Assert.fail("No exception should be on this step.");
		} catch (SampleServiceException e) {
			Assert.fail("No exception should be on this step.");
		}

		// resetting manager configuration
		Manager.tearDown();

		// checking clean configuration, default scope
		try {
			Manager.get(SampleService.class);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (ManagerException e) {
			Assert.assertTrue("Right exception instance should be there.", e instanceof ServiceScopeConfigurationNotFoundException);
		}

		// checking clean configuration, local scope
		try {
			Manager.get(SampleService.class, ServiceTypeScope.BUSINESS_SERVICE, null, ServiceDistributionScope.LOCAL);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (ManagerException e) {
			Assert.assertTrue("Right exception instance should be there.", e instanceof ServiceScopeConfigurationNotFoundException);
		}

		// checking clean configuration, remote scope
		try {
			Manager.get(SampleService.class, ServiceTypeScope.BUSINESS_SERVICE, ServiceDistributionScope.REMOTE, null, customScope);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (ManagerException e) {
			Assert.assertTrue("Right exception instance should be there.", e instanceof ServiceScopeConfigurationNotFoundException);
		}
	}

	/**
	 * Error cases test.
	 */
	@Test
	public void errorCasesTest() {
		try {
			ServiceFactory<SampleService> factory = null;
			Manager.register(SampleService.class, factory, null, new Scope[] {});
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("[serviceFactory]"));
		}

		try {
			Manager.register(null, new SampleServiceFactory(), null, new Scope[] {});
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("[service]"));
		}

		try {
			Manager.get(null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("[service]"));
		} catch (ManagerException e) {
			Assert.fail("No exception should be on this step.");
		}
	}

}
