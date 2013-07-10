package net.bolbat.gear.common.ioc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link ServiceCustomScope} test.
 * 
 * @author Alexandr Bolbat
 */
public class ServiceCustomScopeTest {

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
		ServiceCustomScope first = new ServiceCustomScope("FIRST");
		ServiceCustomScope sameAsFirst = new ServiceCustomScope("FIRST");
		ServiceCustomScope second = new ServiceCustomScope("SECOND");

		// check equals
		Assert.assertTrue("Should be the same", first.equals(sameAsFirst));
		Assert.assertFalse("Should be not the same", first.equals(second));
		Assert.assertFalse("Should be not the same", sameAsFirst.equals(second));

		// check id
		Assert.assertEquals("Should be the same", ServiceCustomScope.class.getName() + "." + "FIRST", first.getId());
		Assert.assertEquals("Should be the same", ServiceCustomScope.class.getName() + "." + "FIRST", sameAsFirst.getId());
		Assert.assertEquals("Should be the same", ServiceCustomScope.class.getName() + "." + "SECOND", second.getId());

		// check toString
		Assert.assertEquals("Should be the same", ServiceCustomScope.class.getName() + "." + "FIRST", first.toString());
		Assert.assertEquals("Should be the same", ServiceCustomScope.class.getName() + "." + "FIRST", sameAsFirst.toString());
		Assert.assertEquals("Should be the same", ServiceCustomScope.class.getName() + "." + "SECOND", second.toString());
	}

}
