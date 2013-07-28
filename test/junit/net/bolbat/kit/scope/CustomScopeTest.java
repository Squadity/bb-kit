package net.bolbat.kit.scope;

import net.bolbat.kit.ioc.scope.CustomScope;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link CustomScope} test.
 * 
 * @author Alexandr Bolbat
 */
public class CustomScopeTest {

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
	 * Complex test.
	 */
	@Test
	public void complexTest() {
		// initial data
		CustomScope first = CustomScope.get("FIRST");
		CustomScope sameAsFirst = CustomScope.get("FIRST");
		CustomScope second = CustomScope.get("SECOND");

		// check equals
		Assert.assertTrue("Should be the same", first.equals(sameAsFirst));
		Assert.assertFalse("Should be not the same", first.equals(second));
		Assert.assertFalse("Should be not the same", sameAsFirst.equals(second));

		// check id
		Assert.assertEquals("Should be the same", "FIRST", first.getId());
		Assert.assertEquals("Should be the same", "FIRST", sameAsFirst.getId());
		Assert.assertEquals("Should be the same", "SECOND", second.getId());

		// check toString
		Assert.assertEquals("Should be the same", "FIRST", first.toString());
		Assert.assertEquals("Should be the same", "FIRST", sameAsFirst.toString());
		Assert.assertEquals("Should be the same", "SECOND", second.toString());
	}

}
