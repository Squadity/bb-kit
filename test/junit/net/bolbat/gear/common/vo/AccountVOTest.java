package net.bolbat.gear.common.vo;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link AccountVO} test.
 * 
 * @author Alexandr Bolbat
 */
public class AccountVOTest {

	/**
	 * Complex test.
	 */
	@Test
	public void complexTest() {
		AccountVO empty = new AccountVO();
		Assert.assertEquals(AccountId.EMPTY_ID, empty.getId());
		Assert.assertEquals(0, empty.getCreated());
		Assert.assertEquals(0, empty.getUpdated());

		AccountVO clone = empty.clone();
		Assert.assertEquals(empty, clone);
		Assert.assertNotSame(empty, clone);
		Assert.assertNotSame(empty.getId(), clone.getId());
	}

}
