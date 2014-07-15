package net.bolbat.kit.lucene;

import java.util.Collection;

import org.apache.lucene.search.Query;

/**
 * Lucene store interface.
 * 
 * @author Alexandr Bolbat
 * 
 * @param <S>
 *            storable bean type
 */
public interface LuceneStore<S extends Storable> {

	/**
	 * Get all bean's.
	 * 
	 * @return {@link Collection} of <S>
	 */
	Collection<S> getAll();

	/**
	 * Get bean.
	 * 
	 * @param fieldName
	 *            identifier field name
	 * @param fieldValue
	 *            identifier field value
	 * @return <S> bean of found or <code>null</code>
	 */
	S get(String fieldName, String fieldValue);

	/**
	 * Add bean.
	 * 
	 * @param toAdd
	 *            bean
	 */
	void add(S toAdd);

	/**
	 * Update bean.
	 * 
	 * @param toUpdate
	 *            bean
	 */
	void update(S toUpdate);

	/**
	 * Remove bean.
	 * 
	 * @param toRemove
	 *            bean
	 */
	void remove(S toRemove);

	/**
	 * Add beans.<br>
	 * All not valid bean's would be skipped.
	 * 
	 * @param toAdd
	 *            {@link Collection} of beans
	 */
	void add(Collection<S> toAdd);

	/**
	 * Update beans.<br>
	 * All not valid bean's would be skipped.
	 * 
	 * @param toUpdate
	 *            {@link Collection} of beans
	 */
	void update(Collection<S> toUpdate);

	/**
	 * Remove beans.<br>
	 * All not valid bean's would be skipped.
	 * 
	 * @param toRemove
	 *            {@link Collection} of beans
	 */
	void remove(Collection<S> toRemove);

	/**
	 * Remove all beans.
	 */
	void removeAll();

	/**
	 * Get all beans count.
	 * 
	 * @return bean's count
	 */
	int count();

	/**
	 * Get beans count.
	 * 
	 * @param query
	 *            {@link Query}
	 * @return bean's count
	 */
	int count(Query query);

	/**
	 * Search bean's.
	 * 
	 * @param query
	 *            {@link Query}
	 * @return {@link Collection} of <S>
	 */
	Collection<S> get(Query query);

	/**
	 * Search bean's.
	 * 
	 * @param query
	 *            {@link Query}
	 * @param limit
	 *            limit of the result
	 * @return {@link Collection} of <S>
	 */
	Collection<S> get(Query query, int limit);

	/**
	 * Tear down {@link LuceneStore} state.
	 */
	void tearDown();

}
