package net.bolbat.gear.common.vo;

import java.util.HashSet;
import java.util.Set;

import net.bolbat.utils.lang.StringUtils;

/**
 * Account data.
 * 
 * @author Alexandr Bolbat
 */
public class AccountVO extends EntityVO {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = 4957254145120125765L;

	/**
	 * Default account type.
	 */
	public static final String DEFAULT_TYPE = "";

	/**
	 * Type maximum length.
	 */
	public static final int TYPE_MAX_LENGTH = 64;

	/**
	 * Status maximum length.
	 */
	public static final int STATUS_MAX_LENGTH = 64;

	/**
	 * Account identifier.
	 */
	private AccountId id = AccountId.EMPTY_ID;

	/**
	 * Account type.
	 */
	private String type = DEFAULT_TYPE;

	/**
	 * Account statuses.
	 */
	private Set<String> statuses = new HashSet<String>();

	/**
	 * Default constructor.
	 */
	public AccountVO() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param aId
	 *            account identifier
	 */
	public AccountVO(final AccountId aId) {
		setId(aId);
	}

	public AccountId getId() {
		return id;
	}

	public void setId(final AccountId aId) {
		this.id = aId == null ? AccountId.EMPTY_ID : aId;
	}

	public String getType() {
		return type;
	}

	/**
	 * Set account type.
	 * 
	 * @param aType
	 *            account type, length can't be more then permitted (check <code>TYPE_MAX_LENGTH</code> constant)
	 */
	public void setType(final String aType) {
		if (aType.length() > TYPE_MAX_LENGTH)
			throw new IllegalArgumentException("aType argument too long, maximum length is [" + TYPE_MAX_LENGTH + "] characters.");

		this.type = StringUtils.isNotEmpty(aType) ? aType : DEFAULT_TYPE;
	}

	/**
	 * Is account has type equals to given type.
	 * 
	 * @param aType
	 *            account type
	 * @return <code>true</code> if equals or <code>false</code>
	 */
	public boolean hasType(final String aType) {
		if (StringUtils.isEmpty(aType) || aType.length() > TYPE_MAX_LENGTH)
			return false;

		return type.equals(aType);
	}

	public Set<String> getStatuses() {
		return new HashSet<String>(statuses);
	}

	/**
	 * Add status to account statuses.
	 * 
	 * @param status
	 *            account status, can't be empty and length more then permitted (check <code>STATUS_MAX_LENGTH</code> constant)
	 */
	public void addStatus(final String status) {
		if (StringUtils.isEmpty(status))
			throw new IllegalArgumentException("status argument is empty.");
		if (status.length() > STATUS_MAX_LENGTH)
			throw new IllegalArgumentException("status argument too long, maximum length is [" + STATUS_MAX_LENGTH + "] characters.");

		statuses.add(status);
	}

	/**
	 * Remove status from account statuses.
	 * 
	 * @param status
	 *            account status
	 */
	public void removeStatus(final String status) {
		if (StringUtils.isEmpty(status) || status.length() > STATUS_MAX_LENGTH)
			return;

		statuses.remove(status);
	}

	/**
	 * Is status exist in account statuses.
	 * 
	 * @param status
	 *            account status
	 * @return <code>true</code> if exist or <code>false</code>
	 */
	public boolean hasStatus(final String status) {
		if (StringUtils.isEmpty(status) || status.length() > STATUS_MAX_LENGTH)
			return false;

		return statuses.contains(status);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof AccountVO))
			return false;
		AccountVO other = (AccountVO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [id=").append(id);
		builder.append(", type=").append(type);
		builder.append(", statuses=").append(statuses);
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

	@Override
	protected AccountVO clone() {
		final AccountVO result = AccountVO.class.cast(super.clone());
		result.id = id.clone();
		result.type = type;
		result.statuses = new HashSet<String>();
		for (final String status : statuses)
			result.addStatus(status);

		return result;
	}

}
