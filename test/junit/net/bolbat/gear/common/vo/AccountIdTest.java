package net.bolbat.gear.common.vo;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link AccountId} test.
 * 
 * @author Alexandr Bolbat
 */
public class AccountIdTest {

	/**
	 * Complex test.
	 */
	@Test
	public void complexTest() {
		AccountId noId = new AccountId();
		Assert.assertEquals(AccountId.EMPTY_RAW_ID, noId.getId());
		Assert.assertEquals(AccountId.EMPTY_RAW_ID, noId.toString());
		Assert.assertSame(AccountId.EMPTY_RAW_ID, noId.getId());

		AccountId nullId = new AccountId(null);
		Assert.assertEquals(AccountId.EMPTY_RAW_ID, nullId.getId());
		Assert.assertEquals(AccountId.EMPTY_RAW_ID, nullId.toString());
		Assert.assertSame(AccountId.EMPTY_RAW_ID, nullId.getId());

		AccountId empty = new AccountId("");
		Assert.assertEquals(AccountId.EMPTY_RAW_ID, empty.getId());
		Assert.assertEquals(AccountId.EMPTY_RAW_ID, empty.toString());
		Assert.assertSame(AccountId.EMPTY_RAW_ID, empty.getId());

		AccountId withSpaces = new AccountId("   ");
		Assert.assertEquals(AccountId.EMPTY_RAW_ID, withSpaces.getId());
		Assert.assertEquals(AccountId.EMPTY_RAW_ID, withSpaces.toString());
		Assert.assertSame(AccountId.EMPTY_RAW_ID, withSpaces.getId());

		AccountId generated1 = AccountId.generateNew();
		Assert.assertNotNull(generated1);
		Assert.assertEquals(generated1.getId(), generated1.toString());
		Assert.assertSame(generated1.getId(), generated1.toString());
		Assert.assertNotEquals(AccountId.EMPTY_RAW_ID, generated1.getId());

		AccountId generated2 = AccountId.generateNew();
		Assert.assertNotNull(generated2);
		Assert.assertEquals(generated2.getId(), generated2.toString());
		Assert.assertSame(generated2.getId(), generated2.toString());
		Assert.assertNotEquals(generated1, generated2);

		AccountId cloned1 = generated1.clone();
		Assert.assertEquals(generated1, cloned1);
		Assert.assertEquals(generated1.getId(), cloned1.getId());
		Assert.assertEquals(generated1.toString(), cloned1.toString());
		Assert.assertNotSame(generated1, cloned1);
	}

}
