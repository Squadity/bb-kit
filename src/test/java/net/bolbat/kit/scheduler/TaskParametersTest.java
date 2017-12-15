package net.bolbat.kit.scheduler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link TaskParameters} test
 *
 * @author ivanbatura
 */
public class TaskParametersTest {
	private static TaskParameters parameters;
	private final static String key1 = "key1";
	private final static String key2 = "key2";

	@Before
	public void setUp() throws Exception {
		parameters = new TaskParameters();
	}

	@Test
	public void stringTest() {
		//preparation
		final String test = "Test";
		final Integer incorrect = 1;
		// call test method
		parameters.putString(key1, test);
		parameters.putInteger(key2, incorrect);
		//validation
		Assert.assertEquals("Value is not correct", test, parameters.getString(key1));
		Assert.assertEquals("Value is not correct", null, parameters.getString(key2));
	}

	@Test
	public void integerTest() {
		//preparation
		final Integer test = 1;
		final String incorrect = "Test";
		// call test method
		parameters.putInteger(key1, test);
		parameters.putString(key2, incorrect);
		//validation
		Assert.assertEquals("Value is not correct", test, parameters.getInteger(key1));
		Assert.assertEquals("Value is not correct", null, parameters.getInteger(key2));
	}

	@Test
	public void longTest() {
		//preparation
		final Long test = 2L;
		final Integer incorrect = 1;
		// call test method
		parameters.putLong(key1, test);
		parameters.putInteger(key2, incorrect);
		//validation
		Assert.assertEquals("Value is not correct", test, parameters.getLong(key1));
		Assert.assertEquals("Value is not correct", null, parameters.getLong(key2));
	}

	@Test
	public void floatTest() {
		//preparation
		final Float test = new Float(2.1);
		final Integer incorrect = 1;
		// call test method
		parameters.putFloat(key1, test);
		parameters.putInteger(key2, incorrect);
		//validation
		Assert.assertEquals("Value is not correct", test, parameters.getFloat(key1));
		Assert.assertEquals("Value is not correct", null, parameters.getFloat(key2));
	}

	@Test
	public void doubleTest() {
		//preparation
		final Double test = 2.1;
		final Integer incorrect = 1;
		// call test method
		parameters.putDouble(key1, test);
		parameters.putInteger(key2, incorrect);
		//validation
		Assert.assertEquals("Value is not correct", test, parameters.getDouble(key1));
		Assert.assertEquals("Value is not correct", null, parameters.getDouble(key2));
	}

	@Test
	public void booleanTest() {
		//preparation
		final Boolean test = true;
		final Integer incorrect = 1;
		// call test method
		parameters.putBoolean(key1, test);
		parameters.putInteger(key2, incorrect);
		//validation
		Assert.assertEquals("Value is not correct", test, parameters.getBoolean(key1));
		Assert.assertEquals("Value is not correct", null, parameters.getBoolean(key2));
	}
}
