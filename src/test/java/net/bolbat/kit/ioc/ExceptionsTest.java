package net.bolbat.kit.ioc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.bolbat.test.utils.CommonTester;

/**
 * IoC package exceptions test.
 * 
 * @author Alexandr Bolbat
 */
public class ExceptionsTest {

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
	 * Complex exceptions test.
	 */
	@Test
	public void complexTest() {
		CommonTester.checkExceptionInstantiation(ManagerException.class);
		CommonTester.checkExceptionInstantiation(ConfigurationNotFoundException.class);
	}

}
