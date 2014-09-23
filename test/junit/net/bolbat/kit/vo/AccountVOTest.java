package net.bolbat.kit.vo;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link AccountVO} test.
 * 
 * @author Alexandr Bolbat
 */
public class AccountVOTest {

	/**
	 * Long {@link String} constant for testing logic.
	 */
	public static final String TOO_LONG_STRING = "1234567890123456789012345678901234567890123456789012345678901234567890";

	/**
	 * Complex test.
	 */
	@Test
	public void complexTest() {
		AccountVO empty = new AccountVO();
		// empty state
		Assert.assertEquals(AccountId.EMPTY_ID, empty.getId());
		Assert.assertEquals(0, empty.getCreated());
		Assert.assertEquals(0, empty.getUpdated());

		// types
		Assert.assertNotNull(empty.getTypes());
		Assert.assertEquals(0, empty.getTypes().size());

		empty.addType("type1");
		empty.addType("type2");
		empty.addType("type2");
		Assert.assertEquals(2, empty.getTypes().size());
		Assert.assertTrue(empty.hasType("type1"));
		Assert.assertTrue(empty.hasType("type2"));

		// statuses
		Assert.assertNotNull(empty.getStatuses());
		Assert.assertEquals(0, empty.getStatuses().size());

		empty.addStatus("status1");
		empty.addStatus("status2");
		empty.addStatus("status2");
		Assert.assertEquals(2, empty.getStatuses().size());
		Assert.assertTrue(empty.hasStatus("status1"));
		Assert.assertTrue(empty.hasStatus("status2"));

		// clone
		AccountVO clone = empty.clone();
		Assert.assertEquals(empty, clone);
		Assert.assertNotSame(empty, clone);
		Assert.assertNotSame(empty.getId(), clone.getId());

		// types
		Assert.assertEquals(2, empty.getTypes().size());
		Assert.assertEquals(2, clone.getTypes().size());
		Assert.assertTrue(empty.hasType("type1"));
		Assert.assertTrue(empty.hasType("type2"));
		Assert.assertTrue(clone.hasType("type1"));
		Assert.assertTrue(clone.hasType("type2"));

		clone.removeType("type2");
		Assert.assertEquals(2, empty.getTypes().size());
		Assert.assertEquals(1, clone.getTypes().size());
		Assert.assertTrue(empty.hasType("type1"));
		Assert.assertTrue(empty.hasType("type2"));
		Assert.assertTrue(clone.hasType("type1"));

		// statuses
		Assert.assertEquals(2, empty.getStatuses().size());
		Assert.assertEquals(2, clone.getStatuses().size());
		Assert.assertTrue(empty.hasStatus("status1"));
		Assert.assertTrue(empty.hasStatus("status2"));
		Assert.assertTrue(clone.hasStatus("status1"));
		Assert.assertTrue(clone.hasStatus("status2"));

		clone.removeStatus("status2");
		Assert.assertEquals(2, empty.getStatuses().size());
		Assert.assertEquals(1, clone.getStatuses().size());
		Assert.assertTrue(empty.hasStatus("status1"));
		Assert.assertTrue(empty.hasStatus("status2"));
		Assert.assertTrue(clone.hasStatus("status1"));
	}

	/**
	 * Error cases test.
	 */
	@Test
	public void errorCases() {
		AccountVO account = new AccountVO();

		// types
		try {
			account.addType(null);
			Assert.fail();
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().startsWith("type"));
		}

		try {
			account.addType("");
			Assert.fail();
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().startsWith("type"));
		}

		try {
			account.addType("   ");
			Assert.fail();
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().startsWith("type"));
		}

		try {
			account.addType(TOO_LONG_STRING);
			Assert.fail();
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().startsWith("type"));
		}

		// statuses
		try {
			account.addStatus(null);
			Assert.fail();
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().startsWith("status"));
		}

		try {
			account.addStatus("");
			Assert.fail();
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().startsWith("status"));
		}

		try {
			account.addStatus("   ");
			Assert.fail();
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().startsWith("status"));
		}

		try {
			account.addStatus(TOO_LONG_STRING);
			Assert.fail();
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().startsWith("status"));
		}
	}

}
