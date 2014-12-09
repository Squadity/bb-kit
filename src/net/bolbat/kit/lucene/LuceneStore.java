package net.bolbat.kit.lucene;

import java.util.Collection;

import org.apache.lucene.document.Document;
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
	 * Get all beans.
	 * 
	 * @return {@link Collection} of <S>
	 */
	Collection<S> getAll();

	/**
	 * Get all {@link Document} instances.
	 *
	 * @return {@link Document} collection
	 */
	Collection<Document> getAllDocuments();

	/**
	 * Get bean.
	 *
	 * @param fieldName
	 *            identifier field name
	 * @param fieldValue
	 *            identifier field value
	 * @return <S> bean if found or <code>null</code>
	 */
	S get(String fieldName, String fieldValue);

	/**
	 * Get {@link Document} with selected properties.
	 *
	 * @param fieldName
	 *            identifier field name
	 * @param fieldValue
	 *            identifier field value
	 * @return {@link Document} if found or <code>null</code>
	 */
	Document getDocument(String fieldName, String fieldValue);

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
	 * Search documents.
	 *
	 * @param query
	 *            {@link Query}
	 * @return {@link Document} collection
	 */
	Collection<Document> getDocuments(Query query);

	/**
	 * Search beans.
	 * 
	 * @param query
	 *            {@link Query}
	 * @param limit
	 *            limit of the result
	 * @return {@link Collection} of <S>
	 */
	Collection<S> get(Query query, int limit);

	/**
	 * Search documents.
	 *
	 * @param query
	 * 		{@link Query}
	 * @param limit
	 * 		max amount of documents in result
	 * @return {@link Document} collection
	 */
	Collection<Document> getDocuments(Query query, int limit);

	/**
	 * Tear down {@link LuceneStore} state.
	 */
	void tearDown();

}
