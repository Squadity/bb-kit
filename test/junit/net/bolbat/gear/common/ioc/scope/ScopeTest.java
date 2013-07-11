package net.bolbat.gear.common.ioc.scope;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link Scope} implementation's test.
 * 
 * @author Alexandr Bolbat
 */
public class ScopeTest {

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
	 * {@link CustomScope} test.
	 */
	@Test
	public void serviceCustomScopeTest() {
		CustomScope customScope = CustomScope.get("SAMPLE");
		Assert.assertSame("Should be the same", customScope, customScope);
		Assert.assertEquals("Should be equal", customScope, customScope);
		Assert.assertFalse("Should be not equal", customScope.equals(null));

		CustomScope sameIdCustomScope = CustomScope.get("SAMPLE");
		Assert.assertEquals("Should be equal", customScope, sameIdCustomScope);

		CustomScope anotherCustomScope = CustomScope.get("ANOTHER_SAMPLE");
		Assert.assertFalse("Should be not equal", customScope.equals(anotherCustomScope));
	}

	/**
	 * {@link DistributionScope} test.
	 */
	@Test
	public void serviceDistributionScopeTest() {
		Scope local = DistributionScope.LOCAL;
		Assert.assertEquals(local, DistributionScope.get(local.getId()));

		Scope remote = DistributionScope.REMOTE;
		Assert.assertEquals(remote, DistributionScope.get(remote.getId()));

		Scope defult = DistributionScope.DEFAULT;
		Assert.assertEquals(defult, DistributionScope.get(defult.getId()));
		Assert.assertEquals(defult, DistributionScope.get("WRONG_ID"));

		Assert.assertEquals(local, defult);
		Assert.assertSame(local, defult);

		Assert.assertFalse(local.equals(remote));
		Assert.assertFalse(local.equals(null));

		Assert.assertFalse(remote.equals(local));
		Assert.assertFalse(remote.equals(null));
	}

	/**
	 * {@link TypeScope} test.
	 */
	@Test
	public void serviceTypeScopeTest() {
		Scope businessService = TypeScope.BUSINESS_SERVICE;
		Assert.assertEquals(businessService, TypeScope.get(businessService.getId()));

		Scope persistenceService = TypeScope.PERSISTENCE_SERVICE;
		Assert.assertEquals(persistenceService, TypeScope.get(persistenceService.getId()));

		Scope api = TypeScope.API;
		Assert.assertEquals(api, TypeScope.get(api.getId()));

		Scope apiExternal = TypeScope.API_EXTERNAL;
		Assert.assertEquals(apiExternal, TypeScope.get(apiExternal.getId()));

		Scope defult = TypeScope.DEFAULT;
		Assert.assertEquals(defult, TypeScope.get(defult.getId()));
		Assert.assertEquals(defult, TypeScope.get("WRONG_ID"));

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
