package net.bolbat.gear.common.service;

import net.bolbat.utils.test.CommonTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Service package exceptions test.
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
		CommonTester.checkExceptionInstantiation(ServiceException.class);
		CommonTester.checkExceptionInstantiation(ServiceInstantiationException.class);
	}

}
