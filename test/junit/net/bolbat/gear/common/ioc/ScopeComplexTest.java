package net.bolbat.gear.common.ioc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link Scope} implementation's test.
 * 
 * @author Alexandr Bolbat
 */
public class ScopeComplexTest {

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
	 * {@link ServiceCustomScope} test.
	 */
	@Test
	public void serviceCustomScopeTest() {
		ServiceCustomScope customScope = new ServiceCustomScope("SAMPLE");
		Assert.assertSame("Should be the same", customScope, customScope);
		Assert.assertEquals("Should be equal", customScope, customScope);
		Assert.assertFalse("Should be not equal", customScope.equals(null));

		ServiceCustomScope sameIdCustomScope = new ServiceCustomScope("SAMPLE");
		Assert.assertNotSame("Should be not the same", customScope, sameIdCustomScope);
		Assert.assertEquals("Should be equal", customScope, sameIdCustomScope);

		ServiceCustomScope anotherCustomScope = new ServiceCustomScope("ANOTHER_SAMPLE");
		Assert.assertFalse("Should be not equal", customScope.equals(anotherCustomScope));
	}

	/**
	 * {@link ServiceDistributionScope} test.
	 */
	@Test
	public void serviceDistributionScopeTest() {
		Scope local = ServiceDistributionScope.LOCAL;
		Assert.assertEquals(local, ServiceDistributionScope.get(local.getId()));

		Scope remote = ServiceDistributionScope.REMOTE;
		Assert.assertEquals(remote, ServiceDistributionScope.get(remote.getId()));

		Scope defult = ServiceDistributionScope.DEFAULT;
		Assert.assertEquals(defult, ServiceDistributionScope.get(defult.getId()));
		Assert.assertEquals(defult, ServiceDistributionScope.get("WRONG_ID"));

		Assert.assertEquals(local, defult);
		Assert.assertSame(local, defult);

		Assert.assertFalse(local.equals(remote));
		Assert.assertFalse(local.equals(null));

		Assert.assertFalse(remote.equals(local));
		Assert.assertFalse(remote.equals(null));
	}

	/**
	 * {@link ServiceTypeScope} test.
	 */
	@Test
	public void serviceTypeScopeTest() {
		Scope businessService = ServiceTypeScope.BUSINESS_SERVICE;
		Assert.assertEquals(businessService, ServiceTypeScope.get(businessService.getId()));

		Scope persistenceService = ServiceTypeScope.PERSISTENCE_SERVICE;
		Assert.assertEquals(persistenceService, ServiceTypeScope.get(persistenceService.getId()));

		Scope api = ServiceTypeScope.API;
		Assert.assertEquals(api, ServiceTypeScope.get(api.getId()));

		Scope apiExternal = ServiceTypeScope.API_EXTERNAL;
		Assert.assertEquals(apiExternal, ServiceTypeScope.get(apiExternal.getId()));

		Scope defult = ServiceTypeScope.DEFAULT;
		Assert.assertEquals(defult, ServiceTypeScope.get(defult.getId()));
		Assert.assertEquals(defult, ServiceTypeScope.get("WRONG_ID"));

		Assert.assertEquals(businessService, defult);
		Assert.assertSame(businessService, defult);

		Assert.assertFalse(businessService.equals(persistenceService));
		Assert.assertFalse(businessService.equals(api));
		Assert.assertFalse(businessService.equals(apiExternal));
		Assert.assertFalse(businessService.equals(null));

		Assert.assertFalse(persistenceService.equals(businessService));
		Assert.assertFalse(persistenceService.equals(api));
		Assert.assertFalse(persistenceService.equals(apiExternal));
		Assert.assertFalse(persistenceService.equals(null));

		Assert.assertFalse(api.equals(businessService));
		Assert.assertFalse(api.equals(persistenceService));
		Assert.assertFalse(api.equals(apiExternal));
		Assert.assertFalse(api.equals(null));

		Assert.assertFalse(apiExternal.equals(businessService));
		Assert.assertFalse(apiExternal.equals(persistenceService));
		Assert.assertFalse(apiExternal.equals(api));
		Assert.assertFalse(apiExternal.equals(null));
	}
}
