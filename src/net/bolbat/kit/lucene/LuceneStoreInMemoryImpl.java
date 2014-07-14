package net.bolbat.kit.lucene;

import static net.bolbat.utils.lang.StringUtils.isEmpty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.Version;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link LuceneStore} in-memory implementation.
 * 
 * @author Alexandr Bolbat
 * 
 * @param <S>
 *            storable bean type
 */
public class LuceneStoreInMemoryImpl<S extends Storable> implements LuceneStore<S> {

	/**
	 * Lucene document field name for serialized bean data.
	 */
	private static final String DOCUMENT_DATA_FIELD_NAME = "BEAN_DATA";

	/**
	 * {@link Version} instance.
	 */
	private final Version version;

	/**
	 * In-memory {@link Directory} instance.
	 */
	private final Directory directory;

	/**
	 * {@link Analyzer} instance.
	 */
	private final Analyzer analyzer;

	/**
	 * {@link IndexWriterConfig} instance.
	 */
	private final IndexWriterConfig indexWriterConfig;

	/**
	 * {@link IndexWriter} instance.
	 */
	private final IndexWriter writer;

	/**
	 * {@link IndexReader} instance.
	 */
	private volatile IndexReader reader;

	/**
	 * {@link IndexSearcher} instance;
	 */
	private volatile IndexSearcher searcher;

	/**
	 * Bean type.
	 */
	private final Class<S> beanType;

	/**
	 * {@link ObjectMapper} instance.
	 */
	private final ObjectMapper mapper;

	/**
	 * Synchronization lock for {@link IndexReader} or {@link IndexSearcher} initialization after {@link IndexWriter} process.
	 */
	private final Object lock = new Object();

	/**
	 * Protected constructor.
	 * 
	 * @param aBeanType
	 *            bean type
	 */
	protected LuceneStoreInMemoryImpl(final Class<S> aBeanType) {
		if (aBeanType == null)
			throw new IllegalArgumentException("aBeanType argument is null");

		try {
			// TODO everything should be configurable
			this.version = Version.LUCENE_47;
			this.directory = new RAMDirectory();
			this.analyzer = new StandardAnalyzer(version);
			this.indexWriterConfig = new IndexWriterConfig(version, analyzer);
			this.writer = new IndexWriter(directory, indexWriterConfig);
			writer.commit();

			this.beanType = aBeanType;

			this.mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
			mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		} catch (final IOException e) {
			throw new LuceneStoreRuntimeException(e);
		}
	}

	@Override
	public Collection<S> getAll() {
		try {
			final IndexReader reader = getReader();
			final Bits liveDocs = MultiFields.getLiveDocs(reader);
			final List<S> result = new ArrayList<S>();
			for (int i = 0; i < reader.maxDoc(); i++) {
				if (liveDocs != null && !liveDocs.get(i))
					continue;

				final Document doc = reader.document(i);
				result.add(mapper.readValue(doc.get(DOCUMENT_DATA_FIELD_NAME), beanType));
			}

			return result;
		} catch (final IOException e) {
			throw new LuceneStoreRuntimeException(e);
		}
	}

	@Override
	public S get(final String fieldName, final String fieldValue) {
		if (isEmpty(fieldName))
			throw new IllegalArgumentException("fieldName argument is empty");
		if (isEmpty(fieldValue))
			throw new IllegalArgumentException("fieldValue argument is empty");

		try {
			final IndexSearcher searcher = getSearcher();
			final BooleanQuery query = new BooleanQuery();
			query.add(new TermQuery(new Term(fieldName, fieldValue)), BooleanClause.Occur.MUST);
			final TopDocs topDocs = searcher.search(query, 1);
			if (topDocs.scoreDocs.length == 0)
				return null;

			final Document doc = searcher.doc(topDocs.scoreDocs[0].doc);
			return mapper.readValue(doc.get(DOCUMENT_DATA_FIELD_NAME), beanType);
		} catch (final IOException e) {
			throw new LuceneStoreRuntimeException(e);
		}
	}

	@Override
	public void add(final S toAdd) {
		if (toAdd == null)
			throw new IllegalArgumentException("toAdd argument is null");
		if (isEmpty(toAdd.idFieldName()))
			throw new IllegalArgumentException("toAdd.idFieldName argument is empty");
		if (isEmpty(toAdd.idFieldValue()))
			throw new IllegalArgumentException("toAdd.idFieldValue argument is empty");

		try {
			final Document doc = toAdd.toDocument();
			doc.add(new TextField(DOCUMENT_DATA_FIELD_NAME, mapper.writeValueAsString(toAdd), Field.Store.YES));

			writer.addDocument(doc);
			writer.commit();
		} catch (final IOException e) {
			throw new LuceneStoreRuntimeException(e);
		} finally {
			cleanAfterCommit();
		}
	}

	@Override
	public void update(final S toUpdate) {
		if (toUpdate == null)
			throw new IllegalArgumentException("toUpdate argument is null");
		if (isEmpty(toUpdate.idFieldName()))
			throw new IllegalArgumentException("toRemove.idFieldName argument is empty");
		if (isEmpty(toUpdate.idFieldValue()))
			throw new IllegalArgumentException("toUpdate.idFieldValue argument is empty");

		try {
			final Document doc = toUpdate.toDocument();
			doc.add(new TextField(DOCUMENT_DATA_FIELD_NAME, mapper.writeValueAsString(toUpdate), Field.Store.YES));

			writer.updateDocument(new Term(toUpdate.idFieldName(), toUpdate.idFieldValue()), doc);
			writer.commit();
		} catch (final IOException e) {
			throw new LuceneStoreRuntimeException(e);
		} finally {
			cleanAfterCommit();
		}
	}

	@Override
	public void remove(final S toRemove) {
		if (toRemove == null)
			throw new IllegalArgumentException("toRemove argument is null");
		if (isEmpty(toRemove.idFieldName()))
			throw new IllegalArgumentException("toRemove.idFieldName argument is empty");
		if (isEmpty(toRemove.idFieldValue()))
			throw new IllegalArgumentException("toRemove.idFieldValue argument is empty");

		try {
			writer.deleteDocuments(new Term(toRemove.idFieldName(), toRemove.idFieldValue()));
			writer.commit();
		} catch (final IOException e) {
			throw new LuceneStoreRuntimeException(e);
		} finally {
			cleanAfterCommit();
		}
	}

	@Override
	public void add(final Collection<S> toAdd) {
		if (toAdd == null)
			throw new IllegalArgumentException("toAdd argument is null");
		if (toAdd.isEmpty())
			return;

		try {
			final List<Document> docs = new ArrayList<Document>();
			for (final S bean : toAdd) {
				if (bean == null || isEmpty(bean.idFieldName()) || isEmpty(bean.idFieldValue()))
					continue;

				final Document doc = bean.toDocument();
				doc.add(new TextField(DOCUMENT_DATA_FIELD_NAME, mapper.writeValueAsString(bean), Field.Store.YES));
				docs.add(doc);
			}

			writer.addDocuments(docs);
			writer.commit();
		} catch (final IOException e) {
			throw new LuceneStoreRuntimeException(e);
		} finally {
			cleanAfterCommit();
		}
	}

	@Override
	public void update(final Collection<S> toUpdate) {
		if (toUpdate == null)
			throw new IllegalArgumentException("toUpdate argument is null");
		if (toUpdate.isEmpty())
			return;

		try {
			for (final S bean : toUpdate) {
				if (bean == null || isEmpty(bean.idFieldName()) || isEmpty(bean.idFieldValue()))
					continue;

				final Document doc = bean.toDocument();
				doc.add(new TextField(DOCUMENT_DATA_FIELD_NAME, mapper.writeValueAsString(bean), Field.Store.YES));

				writer.updateDocument(new Term(bean.idFieldName(), bean.idFieldValue()), doc);
				writer.commit();
			}
		} catch (final IOException e) {
			throw new LuceneStoreRuntimeException(e);
		} finally {
			cleanAfterCommit();
		}
	}

	@Override
	public void remove(final Collection<S> toRemove) {
		if (toRemove == null)
			throw new IllegalArgumentException("toRemove argument is null");
		if (toRemove.isEmpty())
			return;

		try {
			for (final S bean : toRemove) {
				if (bean == null || isEmpty(bean.idFieldName()) || isEmpty(bean.idFieldValue()))
					continue;

				writer.deleteDocuments(new Term(bean.idFieldName(), bean.idFieldValue()));
				writer.commit();
			}
		} catch (final IOException e) {
			throw new LuceneStoreRuntimeException(e);
		} finally {
			cleanAfterCommit();
		}
	}

	@Override
	public int count() {
		return getReader().numDocs();
	}

	@Override
	public int count(final Query query) {
		if (query == null)
			throw new IllegalArgumentException("query argument is null");

		try {
			return getSearcher().search(query, Integer.MAX_VALUE).scoreDocs.length;
		} catch (final IOException e) {
			throw new LuceneStoreRuntimeException(e);
		}
	}

	@Override
	public Collection<S> get(final Query query) {
		if (query == null)
			throw new IllegalArgumentException("query argument is null");

		return get(query, Integer.MAX_VALUE);
	}

	@Override
	public Collection<S> get(final Query query, final int limit) {
		if (query == null)
			throw new IllegalArgumentException("query argument is null");
		if (limit < 1)
			return Collections.emptyList();

		try {
			final IndexSearcher searcher = getSearcher();
			final TopDocs topDocs = searcher.search(query, limit);
			final List<S> result = new ArrayList<S>();
			for (final ScoreDoc scoreDoc : topDocs.scoreDocs) {
				final Document doc = searcher.doc(scoreDoc.doc);
				result.add(mapper.readValue(doc.get(DOCUMENT_DATA_FIELD_NAME), beanType));
			}

			return result;
		} catch (final IOException e) {
			throw new LuceneStoreRuntimeException(e);
		}
	}

	@Override
	public synchronized void tearDown() {
		try {
			writer.deleteAll();
			writer.commit();
		} catch (final IOException e) {
			throw new LuceneStoreRuntimeException(e);
		} finally {
			LuceneUtils.close(reader);
			LuceneUtils.close(analyzer);
			LuceneUtils.close(writer);
			LuceneUtils.close(directory);
		}
	}

	/**
	 * Get {@link IndexReader} instance (with lazy initialization).
	 * 
	 * @return {@link IndexReader}
	 */
	private IndexReader getReader() {
		if (reader == null)
			synchronized (lock) {
				if (reader == null) {
					try {
						reader = DirectoryReader.open(directory);
					} catch (final IOException e) {
						throw new LuceneStoreRuntimeException(e);
					}
				}
			}

		return reader;
	}

	/**
	 * Get {@link IndexSearcher} instance (with lazy initialization).
	 * 
	 * @return {@link IndexSearcher}
	 */
	private IndexSearcher getSearcher() {
		if (searcher == null)
			synchronized (lock) {
				if (searcher == null)
					searcher = new IndexSearcher(getReader());
			}

		return searcher;
	}

	/**
	 * This method used for clean up {@link IndexReader} and {@link IndexSearcher} after any commit to the {@link Directory}.<br>
	 * This action required for {@link IndexReader} and {@link IndexSearcher} lazy re-initialization to use new data after {@link IndexWriter} commit.
	 */
	private void cleanAfterCommit() {
		synchronized (lock) {
			LuceneUtils.close(reader);
			reader = null;
			searcher = null;
		}
	}

}
