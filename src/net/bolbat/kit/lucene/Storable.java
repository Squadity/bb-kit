package net.bolbat.kit.lucene;

import java.io.Serializable;

import org.apache.lucene.document.Document;

/**
 * Interface for beans what can be stored in {@link LuceneStore}.
 * 
 * @author Alexandr Bolbat
 */
public interface Storable extends Serializable {

	/**
	 * Convert bean to lucene document.
	 * 
	 * @return {@link Document} instance
	 */
	Document toDocument();

	/**
	 * Get bean identifier field name.
	 * 
	 * @return {@link String}
	 */
	String idFieldName();

	/**
	 * Get bean identifier field value.
	 * 
	 * @return {@link String}
	 */
	String idFieldValue();

}
