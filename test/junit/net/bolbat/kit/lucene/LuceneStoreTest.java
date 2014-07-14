package net.bolbat.kit.lucene;

import java.util.Arrays;
import java.util.Collection;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link LuceneStore} module test.
 * 
 * @author Alexandr Bolbat
 */
// TODO this test can be improved with additional asserts in all test cases
// TODO multi-threaded test case should be implemented
public class LuceneStoreTest {

	/**
	 * Initialization executed before each test.
	 */
	@Before
	public void before() {
		LuceneStoreManager.getStore(StorableVO.class).tearDown();
		LuceneStoreManager.tearDown();
	}

	/**
	 * Initialization executed after each test.
	 */
	@After
	public void after() {
		LuceneStoreManager.getStore(StorableVO.class).tearDown();
		LuceneStoreManager.tearDown();
	}

	/**
	 * {@link LuceneStore} basic operations test.
	 */
	@Test
	public void complexTest() {
		final LuceneStore<StorableVO> store = LuceneStoreManager.getStore(StorableVO.class);

		final StorableVO bean1 = new StorableVO("bean1", "bean1@beans.com");
		store.add(bean1);

		Assert.assertNotNull(store.get("id", "bean1"));
		Assert.assertEquals("bean1@beans.com", store.get("id", "bean1").getEmail());

		Assert.assertTrue(store.count() == 1);
		Assert.assertNotNull(store.getAll());
		Assert.assertTrue(store.getAll().contains(bean1));

		final StorableVO bean2 = new StorableVO("bean2", "bean2@beans.com");
		store.add(bean2);

		Assert.assertNotNull(store.get("id", "bean2"));
		Assert.assertEquals("bean2@beans.com", store.get("id", "bean2").getEmail());

		Assert.assertTrue(store.count() == 2);
		Assert.assertNotNull(store.getAll());
		Assert.assertTrue(store.getAll().contains(bean1));
		Assert.assertTrue(store.getAll().contains(bean2));

		// update
		bean1.setEmail("bean1-changed@beans.com");
		bean2.setEmail("bean2-changed@beans.com");
		store.update(Arrays.asList(bean1, bean2));

		Assert.assertNotNull(store.get("id", "bean1"));
		Assert.assertEquals("bean1-changed@beans.com", store.get("id", "bean1").getEmail());
		Assert.assertNotNull(store.get("id", "bean2"));
		Assert.assertEquals("bean2-changed@beans.com", store.get("id", "bean2").getEmail());

		Assert.assertTrue(store.count() == 2);
		Assert.assertNotNull(store.getAll());
		Assert.assertTrue(store.getAll().contains(bean1));
		Assert.assertTrue(store.getAll().contains(bean2));

		// deletion
		store.remove(bean1);
		Assert.assertNull(store.get("id", "bean1"));

		Assert.assertTrue(store.count() == 1);
		Assert.assertNotNull(store.getAll());
		Assert.assertTrue(store.getAll().contains(bean2));

		store.remove(bean2);
		Assert.assertNull(store.get("id", "bean2"));

		Assert.assertNotNull(store.getAll());
		Assert.assertTrue(store.count() == 0);
	}

	/**
	 * {@link LuceneStore} search operations test.
	 */
	@Test
	public void searchTest() {
		final LuceneStore<StorableVO> store = LuceneStoreManager.getStore(StorableVO.class);

		final StorableVO bean1 = new StorableVO("bean1", "bean1@beans.com");
		final StorableVO bean2 = new StorableVO("bean2", "bean2@beans.com");
		final StorableVO bean3 = new StorableVO("bean3", "bean3@beans.com");

		store.add(Arrays.asList(bean1, bean2, bean3));
		Assert.assertTrue(store.count() == 3);

		// TermQuery
		Query q = new TermQuery(new Term("email", "bean1@beans.com"));
		Collection<StorableVO> result = store.get(q);
		Assert.assertNotNull(result);
		Assert.assertEquals(1, result.size());
		Assert.assertTrue(result.contains(bean1));

		// PrefixQuery
		q = new PrefixQuery(new Term("email", "bean"));
		result = store.get(q);
		Assert.assertNotNull(result);
		Assert.assertEquals(3, result.size());
		Assert.assertTrue(result.contains(bean1));
		Assert.assertTrue(result.contains(bean2));
		Assert.assertTrue(result.contains(bean3));

		// PrefixQuery with result's limit
		q = new PrefixQuery(new Term("email", "bean"));
		result = store.get(q, 2);
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());

		// WildcardQuery
		q = new WildcardQuery(new Term("email", "bean*beans.com"));
		result = store.get(q);
		Assert.assertNotNull(result);
		Assert.assertEquals(3, result.size());
	}

	/**
	 * Error cases test.
	 */
	@Test
	public void errorCasesTest() {
		final LuceneStore<StorableVO> store = LuceneStoreManager.getStore(StorableVO.class);
		final StorableVO nullStorable = null;
		final Collection<StorableVO> nullCollection = null;
		final Query nullQuery = null;

		try {
			store.add(nullStorable);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("toAdd"));
		}
		try {
			store.add(nullCollection);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("toAdd"));
		}

		try {
			store.update(nullStorable);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("toUpdate"));
		}
		try {
			store.update(nullCollection);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("toUpdate"));
		}

		try {
			store.remove(nullStorable);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("toRemove"));
		}
		try {
			store.remove(nullCollection);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("toRemove"));
		}

		try {
			store.get(nullQuery);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("query"));
		}
		try {
			store.get(nullQuery, 0);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("query"));
		}

		try {
			store.count(nullQuery);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("query"));
		}
	}

}
