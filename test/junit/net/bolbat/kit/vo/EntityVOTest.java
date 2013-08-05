package net.bolbat.kit.vo;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link EntityVO} test.
 * 
 * @author Alexandr Bolbat
 */
public class EntityVOTest {

	/**
	 * Complex test.
	 */
	@Test
	public void complexTest() {
		EntityVO empty = new EntityVO();
		Assert.assertEquals(0, empty.getCreated());
		Assert.assertEquals(0, empty.getUpdated());

		empty.setCreated(123);
		empty.setUpdated(456);
		Assert.assertEquals(123, empty.getCreated());
		Assert.assertEquals(456, empty.getUpdated());

		EntityVO clone = empty.clone();
		Assert.assertEquals(empty.getCreated(), clone.getCreated());
		Assert.assertEquals(empty.getUpdated(), clone.getUpdated());
		Assert.assertNotSame(empty, clone);

		Assert.assertEquals(123, clone.getCreated());
		Assert.assertEquals(456, clone.getUpdated());
	}

}
