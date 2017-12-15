package net.bolbat.kit.lucene;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lucene utilities.
 * 
 * @author Alexandr Bolbat
 */
public final class LuceneUtils {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LuceneUtils.class);

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private LuceneUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Close {@link IndexReader} instance.
	 * 
	 * @param reader
	 *            {@link IndexReader}
	 */
	public static void close(final IndexReader reader) {
		try {
			if (reader != null)
				reader.close();
		} catch (final IOException e) {
			if (LOGGER.isWarnEnabled())
				LOGGER.warn("Unable to close IndexReader", e);
		}
	}

	/**
	 * Close {@link Analyzer} instance.
	 * 
	 * @param analyzer
	 *            {@link Analyzer}
	 */
	public static void close(final Analyzer analyzer) {
		if (analyzer != null)
			analyzer.close();
	}

	/**
	 * Close {@link IndexWriter} instance.
	 * 
	 * @param writer
	 *            {@link IndexWriter}
	 */
	public static void close(final IndexWriter writer) {
		try {
			if (writer != null)
				writer.close();
		} catch (final IOException e) {
			if (LOGGER.isWarnEnabled())
				LOGGER.warn("Unable to close IndexWriter", e);
		}
	}

	/**
	 * Close {@link Directory} instance.
	 * 
	 * @param directory
	 *            {@link Directory}
	 */
	public static void close(final Directory directory) {
		try {
			if (directory != null)
				directory.close();
		} catch (final IOException e) {
			if (LOGGER.isWarnEnabled())
				LOGGER.warn("Unable to close Directory", e);
		}
	}

	/**
	 * Unlock {@link Directory} instance.
	 * 
	 * @param directory
	 *            {@link Directory}
	 */
	// TODO fix deprecation before migration to Lucene 5
	@SuppressWarnings("deprecation")
	public static void unlock(final Directory directory) {
		try {
			if (directory != null)
				IndexWriter.unlock(directory);
		} catch (final IOException e) {
			if (LOGGER.isWarnEnabled())
				LOGGER.warn("Unable to unlock Directory", e);
		}
	}
}
