package net.bolbat.gear.common.ioc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link ScopeUtil} test.
 * 
 * @author Alexandr Bolbat
 */
public class ScopeUtilTest {

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
	 * Conversion test.
	 */
	@Test
	public void scopesToStringTest() {
		String scopesStringEtalon = "<" + ServiceDistributionScope.LOCAL.getId() + "><" + ServiceTypeScope.BUSINESS_SERVICE.getId() + ">";

		String scopesString = ScopeUtil.scopesToString(ServiceDistributionScope.LOCAL, ServiceTypeScope.BUSINESS_SERVICE);
		Assert.assertNotNull(scopesString);
		Assert.assertEquals("Should be the same", scopesStringEtalon, scopesString);

		String reverseScopesString = ScopeUtil.scopesToString(ServiceTypeScope.BUSINESS_SERVICE, ServiceDistributionScope.LOCAL);
		Assert.assertNotNull(reverseScopesString);
		Assert.assertEquals("Should be the same", scopesStringEtalon, reverseScopesString);
		Assert.assertEquals("Should be the same", scopesString, reverseScopesString);

		String emptyScopesString1 = ScopeUtil.scopesToString();
		Assert.assertEquals("", emptyScopesString1);
		Scope someNullScope = null;
		String emptyScopesString2 = ScopeUtil.scopesToString(someNullScope);
		Assert.assertEquals("", emptyScopesString2);
		String emptyScopesString3 = ScopeUtil.scopesToString(new Scope[] {});
		Assert.assertEquals("", emptyScopesString3);
	}

}
