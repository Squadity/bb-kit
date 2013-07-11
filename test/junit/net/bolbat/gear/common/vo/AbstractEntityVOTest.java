package net.bolbat.gear.common.vo;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link AbstractEntityVO} test.
 * 
 * @author Alexandr Bolbat
 */
public class AbstractEntityVOTest {

	/**
	 * Complex test.
	 */
	@Test
	public void complexTest() {
		AbstractEntityVO empty = new AbstractEntityVO();
		Assert.assertEquals(0, empty.getCreated());
		Assert.assertEquals(0, empty.getUpdated());

		AbstractEntityVO clone = empty.clone();
		Assert.assertEquals(empty, clone);
		Assert.assertNotSame(empty, clone);
	}

}
