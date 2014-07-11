package net.bolbat.kit.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;

/**
 * {@link Storable} bean for testing purposes.
 * 
 * @author Alexandr Bolbat
 */
public class StorableVO implements Storable {

	/**
	 * Default serialVersionUID variable.
	 */
	private static final long serialVersionUID = 2380100479259475626L;

	/**
	 * Unique id.
	 */
	private String id;

	/**
	 * Email.
	 */
	private String email;

	/**
	 * Default constructor.
	 */
	public StorableVO() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param aId
	 *            unique id
	 * @param aEmail
	 *            email
	 */
	public StorableVO(final String aId, final String aEmail) {
		this.id = aId;
		this.email = aEmail;
	}

	public String getId() {
		return id;
	}

	public void setId(final String aId) {
		this.id = aId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String aEmail) {
		this.email = aEmail;
	}

	@Override
	public Document toDocument() {
		final Document doc = new Document();
		doc.add(new StringField(idFieldName(), idFieldValue(), Field.Store.YES));
		doc.add(new StringField("email", getEmail(), Field.Store.YES));
		return doc;
	}

	@Override
	public String idFieldName() {
		return "id";
	}

	@Override
	public String idFieldValue() {
		return getId();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [id=").append(id);
		builder.append(", email=").append(email);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StorableVO))
			return false;
		final StorableVO other = (StorableVO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
