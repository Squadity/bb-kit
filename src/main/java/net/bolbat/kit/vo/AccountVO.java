package net.bolbat.kit.vo;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.bolbat.kit.property.Property;
import net.bolbat.utils.lang.StringUtils;
import net.bolbat.utils.lang.ToStringUtils;

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
	 * Account types.
	 */
	private Set<String> types = new HashSet<>();

	/**
	 * Account statuses.
	 */
	private Set<String> statuses = new HashSet<>();

	/**
	 * Account properties.
	 */
	private Map<String, Property<?>> properties = new HashMap<>();

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

	public Set<String> getTypes() {
		return Collections.unmodifiableSet(types);
	}

	/**
	 * Add type to account types.
	 * 
	 * @param type
	 *            account type, can't be empty and length more then permitted (check <code>TYPE_MAX_LENGTH</code> constant)
	 */
	public void addType(final String type) {
		if (StringUtils.isEmpty(type))
			throw new IllegalArgumentException("type argument is empty.");
		if (type.length() > TYPE_MAX_LENGTH)
			throw new IllegalArgumentException("type argument too long, maximum length is [" + TYPE_MAX_LENGTH + "] characters.");

		types.add(type);
	}

	/**
	 * Remove type from account types.
	 * 
	 * @param type
	 *            account type
	 */
	public void removeType(final String type) {
		if (StringUtils.isEmpty(type) || type.length() > TYPE_MAX_LENGTH)
			return;

		types.remove(type);
	}

	/**
	 * Is type exist in account types.
	 * 
	 * @param type
	 *            account type
	 * @return <code>true</code> if exist or <code>false</code>
	 */
	public boolean hasType(final String type) {
		if (StringUtils.isEmpty(type) || type.length() > TYPE_MAX_LENGTH)
			return false;

		return types.contains(type);
	}

	public Set<String> getStatuses() {
		return Collections.unmodifiableSet(statuses);
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

	public Map<String, Property<?>> getProperties() {
		return Collections.unmodifiableMap(properties);
	}

	/**
	 * Get property.
	 * 
	 * @param propertyKey
	 *            property key
	 * @return {@link Property} or <code>null</code>
	 */
	public Property<?> getProperty(final String propertyKey) {
		return StringUtils.isNotEmpty(propertyKey) ? properties.get(propertyKey) : null;
	}

	/**
	 * Add property.
	 * 
	 * @param property
	 *            property, can't be <code>null</code> and property key can't be empty
	 */
	public void addProperty(final Property<?> property) {
		if (property == null)
			throw new IllegalArgumentException("property argument is null.");
		if (StringUtils.isEmpty(property.getKey()))
			throw new IllegalArgumentException("property.key argument is empty.");

		properties.put(property.getKey(), property);
	}

	/**
	 * Remove property.
	 * 
	 * @param propertyKey
	 *            property key
	 */
	public void removeProperty(final String propertyKey) {
		if (StringUtils.isEmpty(propertyKey))
			return;

		properties.remove(propertyKey);
	}

	/**
	 * Is property exist.
	 * 
	 * @param propertyKey
	 *            property key
	 * @return <code>true</code> if exist or <code>false</code>
	 */
	public boolean hasProperty(final String propertyKey) {
		if (StringUtils.isEmpty(propertyKey))
			return false;

		return properties.get(propertyKey) != null;
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
		if (!(obj instanceof AccountVO))
			return false;
		final AccountVO other = (AccountVO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [id=").append(id);
		builder.append(", types=").append(ToStringUtils.toString(types));
		builder.append(", statuses=").append(ToStringUtils.toString(statuses));
		builder.append(", properties=").append(ToStringUtils.toString(properties));
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

	@Override
	public AccountVO clone() {
		final AccountVO result = AccountVO.class.cast(super.clone());
		result.id = id.clone();

		result.types = new HashSet<>();
		for (final String type : types)
			result.addType(type);

		result.statuses = new HashSet<>();
		for (final String status : statuses)
			result.addStatus(status);

		result.properties = new HashMap<>();
		for (final Property<?> property : properties.values())
			result.addProperty(property.clone());

		return result;
	}

}
