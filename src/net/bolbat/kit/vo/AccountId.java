package net.bolbat.kit.vo;

import java.io.Serializable;
import java.util.UUID;

import net.bolbat.utils.lang.StringUtils;

/**
 * Account unique identifier.
 * 
 * @author Alexandr Bolbat
 */
public class AccountId implements Serializable, Cloneable {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = 347872401754968026L;

	/**
	 * {@link AccountId} with empty raw identifier.
	 */
	public static final AccountId EMPTY_ID = new AccountId();

	/**
	 * Empty raw identifier.
	 */
	public static final String EMPTY_RAW_ID = "";

	/**
	 * Raw identifier.
	 */
	private String id;

	/**
	 * Default constructor.
	 */
	public AccountId() {
		setId(EMPTY_RAW_ID);
	}

	/**
	 * Public constructor.
	 * 
	 * @param aId
	 *            raw identifier
	 */
	public AccountId(final String aId) {
		setId(aId);
	}

	/**
	 * Generate unique {@link AccountId}.
	 * 
	 * @return {@link AccountId} instance
	 */
	public static AccountId generateNew() {
		return new AccountId(UUID.randomUUID().toString());
	}

	public String getId() {
		return id;
	}

	public void setId(final String aId) {
		this.id = StringUtils.isEmpty(aId) ? EMPTY_RAW_ID : aId;
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
		if (!(obj instanceof AccountId))
			return false;
		AccountId other = (AccountId) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return id;
	}

	@Override
	public AccountId clone() {
		try {
			return AccountId.class.cast(super.clone());
		} catch (final CloneNotSupportedException e) {
			throw new AssertionError("Can't clone [" + this + "]");
		}
	}

}
