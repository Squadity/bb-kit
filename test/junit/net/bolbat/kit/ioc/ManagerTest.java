package net.bolbat.kit.ioc;

import static net.bolbat.kit.ioc.scope.DistributionScope.LOCAL;
import static net.bolbat.kit.ioc.scope.DistributionScope.REMOTE;
import static net.bolbat.kit.ioc.scope.TypeScope.SERVICE;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import net.bolbat.kit.ioc.Manager.Feature;
import net.bolbat.kit.ioc.scope.CompositeScope;
import net.bolbat.kit.ioc.scope.CustomScope;
import net.bolbat.kit.ioc.scope.DistributionScope;
import net.bolbat.kit.ioc.scope.Scope;
import net.bolbat.kit.ioc.scope.TypeScope;
import net.bolbat.kit.service.Configuration;
import net.bolbat.kit.service.SampleService;
import net.bolbat.kit.service.SampleServiceException;
import net.bolbat.kit.service.SampleServiceFactory;
import net.bolbat.kit.service.SampleServiceImpl;
import net.bolbat.kit.service.SampleServiceRemoteFactory;
import net.bolbat.kit.service.SampleServiceRemoteImpl;
import net.bolbat.kit.service.Service;
import net.bolbat.kit.service.ServiceFactory;
import net.bolbat.utils.annotation.Mark.ToDo;

/**
 * {@link Manager} module test.
 * 
 * @author Alexandr Bolbat
 */
@ToDo({ "Implement", "warmUp/tearDown", "Feature.AUTO_IMPL_DISCOVERY", "test cases", "multi-module configuration" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ManagerTest {

	/**
	 * Initialization executed before each test.
	 */
	@Before
	public void before() {
		after();
	}

	/**
	 * Initialization executed after each test.
	 */
	@After
	public void after() {
		try {
			Manager.tearDown();
		} catch (final RuntimeException e) {
			Assert.assertEquals("Just for check.", e.getCause().getMessage());
		}
	}

	/**
	 * Service configuration module complex test.
	 */
	@Test
	public void complexTest() {
		// checking clean configuration
		try {
			Manager.Features.disable(Feature.AUTO_IMPL_DISCOVERY);

			Assert.assertFalse(Manager.isConfigured(SampleService.class));

			Manager.get(SampleService.class);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (ManagerException e) {
			Assert.assertTrue("Right exception instance should be there.", e instanceof ConfigurationNotFoundException);
		} finally {
			Manager.Features.enable(Feature.AUTO_IMPL_DISCOVERY);
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
			Assert.assertTrue(Manager.isConfigured(SampleService.class, SERVICE, LOCAL, null));

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
			Assert.assertTrue(Manager.isConfigured(SampleService.class, SERVICE, null, customScope, REMOTE));

			SampleService remoteInstance = Manager.get(SampleService.class, SERVICE, null, customScope, REMOTE);
			Assert.assertTrue(remoteInstance instanceof SampleServiceRemoteImpl);
			Assert.assertEquals("LOCATED. PARAMETER: configured parameter", remoteInstance.getCreationMethod());
		} catch (ManagerException e) {
			Assert.fail("No exception should be on this step.");
		} catch (SampleServiceException e) {
			Assert.fail("No exception should be on this step.");
		}

		// resetting manager configuration
		try {
			Manager.tearDown();
		} catch (final RuntimeException e) {
			Assert.assertEquals("Just for check.", e.getCause().getMessage());
		}

		// checking clean configuration, default scope
		try {
			Manager.Features.disable(Feature.AUTO_IMPL_DISCOVERY);
			Assert.assertFalse(Manager.isConfigured(SampleService.class));

			Manager.get(SampleService.class);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (ManagerException e) {
			Assert.assertTrue("Right exception instance should be there.", e instanceof ConfigurationNotFoundException);
		} finally {
			Manager.Features.enable(Feature.AUTO_IMPL_DISCOVERY);
		}

		// checking clean configuration, local scope
		try {
			Manager.Features.disable(Feature.AUTO_IMPL_DISCOVERY);

			Assert.assertFalse(Manager.isConfigured(SampleService.class, SERVICE, null, LOCAL));

			Manager.get(SampleService.class, SERVICE, null, LOCAL);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (ManagerException e) {
			Assert.assertTrue("Right exception instance should be there.", e instanceof ConfigurationNotFoundException);
		} finally {
			Manager.Features.enable(Feature.AUTO_IMPL_DISCOVERY);
		}

		// checking clean configuration, remote scope
		try {
			Manager.Features.disable(Feature.AUTO_IMPL_DISCOVERY);

			Assert.assertFalse(Manager.isConfigured(SampleService.class, SERVICE, REMOTE, null, customScope));

			Manager.get(SampleService.class, SERVICE, REMOTE, null, customScope);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (ManagerException e) {
			Assert.assertTrue("Right exception instance should be there.", e instanceof ConfigurationNotFoundException);
		} finally {
			Manager.Features.enable(Feature.AUTO_IMPL_DISCOVERY);
		}
	}

	@Test
	public void registerByInstance() throws SampleServiceException {
		Manager.register(SampleService.class, SampleServiceImpl.createBy(this.getClass().getSimpleName()));
		final SampleService service = Manager.getFast(SampleService.class);
		Assert.assertNotNull(service);
		Assert.assertEquals("CREATED BY: " + this.getClass().getSimpleName(), service.getCreationMethod());
	}

	@Test
	public void registerByInstanceWithScopes() throws SampleServiceException {
		final CompositeScope scope = CompositeScope.get(TypeScope.API_EXTERNAL, DistributionScope.LOCAL, CustomScope.get(UUID.randomUUID().toString()));
		Manager.register(SampleService.class, SampleServiceImpl.createBy(this.getClass().getSimpleName()), scope);

		try {
			Manager.Features.disable(Feature.AUTO_IMPL_DISCOVERY);
			Manager.getFast(SampleService.class);
			Assert.fail();
		} catch (final ManagerRuntimeException e) {
			Assert.assertTrue("Right exception instance should be there.", e.getCause() instanceof ConfigurationNotFoundException);
		} finally {
			Manager.Features.enable(Feature.AUTO_IMPL_DISCOVERY);
		}

		final SampleService service = Manager.getFast(SampleService.class, scope);
		Assert.assertNotNull(service);
		Assert.assertEquals("CREATED BY: " + this.getClass().getSimpleName(), service.getCreationMethod());
	}

	@Test
	public void getFast() {
		// not configured
		try {
			Manager.Features.disable(Feature.AUTO_IMPL_DISCOVERY);
			Manager.getFast(SampleService.class);
			Assert.fail("Exception shold be thrown before this step");
		} catch (ManagerRuntimeException e) {
			Assert.assertNotNull("Right exception instance should be there", e.getCause());
			Assert.assertTrue("Right exception instance should be there", e.getCause() instanceof ConfigurationNotFoundException);
		} finally {
			Manager.Features.enable(Feature.AUTO_IMPL_DISCOVERY);
		}

		// automatically resolved
		Manager.register(SampleService.class, SampleServiceFactory.class, SERVICE);
		Manager.link(SampleService.class, SERVICE);

		Assert.assertTrue(Manager.isConfigured(SampleService.class));

		final SampleService service = Manager.getFast(SampleService.class);
		Assert.assertNotNull("Service instance should be found", service);
	}

	/**
	 * {@link ScopeLink} functionality test.
	 */
	@Test
	public void scopeLinksTest() {
		Manager.register(SampleService.class, SampleServiceFactory.class, LOCAL, SERVICE);
		SampleService instance1 = null;
		try {
			instance1 = Manager.get(SampleService.class, LOCAL, SERVICE);
			Assert.assertNotNull(instance1);
		} catch (final ManagerException e) {
			Assert.fail();
		}

		try {
			Manager.Features.disable(Feature.AUTO_IMPL_DISCOVERY);
			Manager.get(SampleService.class);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (ManagerException e) {
			Assert.assertTrue("Right exception instance should be there.", e instanceof ConfigurationNotFoundException);
		} finally {
			Manager.Features.enable(Feature.AUTO_IMPL_DISCOVERY);
		}

		Manager.link(SampleService.class, SERVICE);
		Manager.link(SampleService.class, SERVICE, CustomScope.get("SOMETHING"));
		Manager.link(SampleService.class, CustomScope.get("SOMETHING"), CompositeScope.get(LOCAL, SERVICE));
		SampleService instance2 = null;
		try {
			instance2 = Manager.get(SampleService.class);
			Assert.assertNotNull(instance2);
			Assert.assertSame(instance1, instance2);

			instance2 = Manager.get(SampleService.class, CustomScope.get("SOMETHING"));
			Assert.assertNotNull(instance2);
			Assert.assertSame(instance1, instance2);
		} catch (final ManagerException e) {
			Assert.fail();
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

	/**
	 * {@link PostConstruct} test.
	 * 
	 * @throws ManagerException
	 */
	@Test
	public void postConstructTest() throws ManagerException {
		// saving initial state
		final int currentValue = SampleServiceImpl.getPostConstructedAcount();

		Manager.register(SampleService.class, SampleServiceFactory.class);
		Manager.warmUp();
		Assert.assertEquals(currentValue + 2, SampleServiceImpl.getPostConstructedAcount());
	}

	/**
	 * {@link PreDestroy} test.
	 * 
	 * @throws ManagerException
	 */
	@Test
	public void preDestroyTest() throws ManagerException {
		// saving initial state
		final int currentValue = SampleServiceImpl.getPreDestroyedAcount();

		Manager.register(SampleService.class, SampleServiceFactory.class);
		Manager.get(SampleService.class); // force initialization
		try {
			Manager.tearDown();
		} catch (final RuntimeException e) {
			Assert.assertEquals("Just for check.", e.getCause().getMessage());
		}
		Assert.assertEquals(currentValue + 2, SampleServiceImpl.getPreDestroyedAcount());
	}

	/**
	 * Test {@link Manager} warm up if there services with relations to each other in post-construct.
	 * 
	 * @throws ManagerException
	 */
	@Test
	public void twoWayRelationsTest() throws ManagerException {
		Manager.register(ServiceOne.class, ServiceOneFactory.class);
		Manager.register(ServiceTwo.class, ServiceTwoFactory.class);
		Manager.warmUp();
	}

	/**
	 * Test {@link Manager} warm up if there service with circular dependency in post-construct.
	 * 
	 * @throws ManagerException
	 */
	@Test
	public void circularDependencyInWarmUpTest() throws ManagerException {
		Manager.register(ServiceWithCircularDependencyInWarmUp.class, ServiceWithCircularDependencyInWarmUpFactory.class);
		Manager.get(ServiceWithCircularDependencyInWarmUp.class);
	}

	/**
	 * Mock service.
	 * 
	 * @author Alexandr Bolbat
	 */
	public interface ServiceOne extends Service {

		/**
		 * Get message.
		 * 
		 * @return {@link String}
		 */
		String getMessage();

	}

	/**
	 * Mock implementation.
	 * 
	 * @author Alexandr Bolbat
	 */
	public static class ServiceOneImpl implements ServiceOne {

		@PostConstruct
		private void postConstruct() {
			try {
				System.out.println(this.getClass().getSimpleName() + " postConstruct[" + Manager.get(ServiceTwo.class).getMessage() + "] called");
			} catch (final ManagerException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public String getMessage() {
			return this.getClass().getSimpleName();
		}

	}

	/**
	 * Mock Factory.
	 * 
	 * @author Alexandr Bolbat
	 */
	public static class ServiceOneFactory implements ServiceFactory<ServiceOne> {
		@Override
		public ServiceOne create(final Configuration configuration) {
			return new ServiceOneImpl();
		}
	}

	/**
	 * Mock service.
	 * 
	 * @author Alexandr Bolbat
	 */
	public interface ServiceTwo extends Service {

		/**
		 * Get message.
		 * 
		 * @return {@link String}
		 */
		String getMessage();

	}

	/**
	 * Mock implementation.
	 * 
	 * @author Alexandr Bolbat
	 */
	public static class ServiceTwoImpl implements ServiceTwo {

		@PostConstruct
		private void postConstruct() {
			try {
				System.out.println(this.getClass().getSimpleName() + " postConstruct[" + Manager.get(ServiceOne.class).getMessage() + "] called");
			} catch (final ManagerException e) {
				throw new RuntimeException(e);
			}

		}

		@Override
		public String getMessage() {
			return this.getClass().getSimpleName();
		}

	}

	/**
	 * Mock Factory.
	 * 
	 * @author Alexandr Bolbat
	 */
	public static class ServiceTwoFactory implements ServiceFactory<ServiceTwo> {
		@Override
		public ServiceTwo create(final Configuration configuration) {
			return new ServiceTwoImpl();
		}
	}

	/**
	 * Mock service.
	 * 
	 * @author Alexandr Bolbat
	 */
	public interface ServiceWithCircularDependencyInWarmUp extends Service {

		/**
		 * Get message.
		 * 
		 * @return {@link String}
		 */
		String getMessage();

	}

	/**
	 * Mock implementation.
	 * 
	 * @author Alexandr Bolbat
	 */
	public static class ServiceWithCircularDependencyInWarmUpImpl implements ServiceWithCircularDependencyInWarmUp {

		@PostConstruct
		private void postConstruct() {
			try {
				System.out.println(this.getClass().getSimpleName() + " postConstruct[" + Manager.get(ServiceWithCircularDependencyInWarmUp.class).getMessage()
						+ "] called");
			} catch (final ManagerException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public String getMessage() {
			return this.getClass().getSimpleName();
		}

	}

	/**
	 * Mock Factory.
	 * 
	 * @author Alexandr Bolbat
	 */
	public static class ServiceWithCircularDependencyInWarmUpFactory implements ServiceFactory<ServiceWithCircularDependencyInWarmUp> {
		@Override
		public ServiceWithCircularDependencyInWarmUp create(final Configuration configuration) {
			return new ServiceWithCircularDependencyInWarmUpImpl();
		}
	}

}
