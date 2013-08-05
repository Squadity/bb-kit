package net.bolbat.kit.ioc;

import static net.bolbat.kit.ioc.scope.DistributionScope.LOCAL;
import static net.bolbat.kit.ioc.scope.DistributionScope.REMOTE;
import static net.bolbat.kit.ioc.scope.TypeScope.SERVICE;
import net.bolbat.kit.ioc.ConfigurationNotFoundException;
import net.bolbat.kit.ioc.Manager;
import net.bolbat.kit.ioc.ManagerException;
import net.bolbat.kit.ioc.scope.CustomScope;
import net.bolbat.kit.ioc.scope.Scope;
import net.bolbat.kit.service.Configuration;
import net.bolbat.kit.service.SampleService;
import net.bolbat.kit.service.SampleServiceException;
import net.bolbat.kit.service.SampleServiceFactory;
import net.bolbat.kit.service.SampleServiceImpl;
import net.bolbat.kit.service.SampleServiceRemoteFactory;
import net.bolbat.kit.service.SampleServiceRemoteImpl;
import net.bolbat.kit.service.ServiceFactory;

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
			Assert.assertTrue("Right exception instance should be there.", e instanceof ConfigurationNotFoundException);
		}

		// configuring local service
		Manager.register(SampleService.class, SampleServiceFactory.class, LOCAL, SERVICE);

		// defining additional custom scope for remote sample service
		CustomScope customScope = CustomScope.get("SAMPLE");

		// configuring remote service with obtaining trough service locator with custom configuration
		Configuration configuration = Configuration.create();
		configuration.set(SampleServiceRemoteFactory.PARAM_TEST, "configured parameter");
		Manager.register(SampleService.class, SampleServiceRemoteFactory.class, configuration, customScope, REMOTE, SERVICE);

		// checking local service
		try {
			SampleService localInstance = Manager.get(SampleService.class, SERVICE, LOCAL, null);
			Assert.assertTrue(localInstance instanceof SampleServiceImpl);
			Assert.assertEquals("CREATED", localInstance.getCreationMethod());
		} catch (ManagerException e) {
			Assert.fail("No exception should be on this step.");
		} catch (SampleServiceException e) {
			Assert.fail("No exception should be on this step.");
		}

		// checking remote service
		try {
			SampleService remoteInstance = Manager.get(SampleService.class, SERVICE, null, customScope, REMOTE);
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
			Assert.assertTrue("Right exception instance should be there.", e instanceof ConfigurationNotFoundException);
		}

		// checking clean configuration, local scope
		try {
			Manager.get(SampleService.class, SERVICE, null, LOCAL);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (ManagerException e) {
			Assert.assertTrue("Right exception instance should be there.", e instanceof ConfigurationNotFoundException);
		}

		// checking clean configuration, remote scope
		try {
			Manager.get(SampleService.class, SERVICE, REMOTE, null, customScope);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (ManagerException e) {
			Assert.assertTrue("Right exception instance should be there.", e instanceof ConfigurationNotFoundException);
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
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("serviceFactory"));
		}

		try {
			Manager.register(null, new SampleServiceFactory(), null, new Scope[] {});
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("service"));
		}

		try {
			Manager.get(null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("service"));
		} catch (ManagerException e) {
			Assert.fail("No exception should be on this step.");
		}
	}

}