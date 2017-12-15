package net.bolbat.kit.property;

import static net.bolbat.utils.lang.StringUtils.isEmpty;

import java.util.Collection;
import java.util.Map;

/**
 * Abstract {@link Property}.
 * 
 * @author Alexandr Bolbat
 *
 * @param <ValueType>
 *            value type
 */
public abstract class AbstractProperty<ValueType> implements Property<ValueType> {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = 4300974178177626747L;

	/**
	 * Property type.
	 */
	private final Properties type;

	/**
	 * Property key.
	 */
	private String key;

	/**
	 * Property value.
	 */
	private ValueType value;

	/**
	 * Property group.
	 */
	private String group;

	/**
	 * Default constructor.
	 * 
	 * @param aType
	 *            property type
	 * @param aKey
	 *            property key
	 * @param aValue
	 *            property value
	 */
	protected AbstractProperty(final Properties aType, final String aKey, final ValueType aValue) {
		if (aType == null)
			throw new IllegalArgumentException("aType argument is null");

		this.type = aType;
		this.key = aKey;
		this.value = aValue;
	}

	@Override
	public Properties getType() {
		return type;
	}

	@Override
	public String getKey() {
		return key;
	}

	public void setKey(final String aKey) {
		this.key = aKey;
	}

	@Override
	public ValueType getValue() {
		return value;
	}

	public void setValue(final ValueType aValue) {
		this.value = aValue;
	}

	@Override
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * Get property value from {@link Collection}.
	 * 
	 * @param type
	 *            property type
	 * @param props
	 *            properties
	 * @param aKey
	 *            property key
	 * @return property value or <code>null</code>
	 */
	public static <ValueType> ValueType get(final Class<ValueType> type, final Collection<Property<?>> props, final String aKey) {
		return get(type, props, aKey, null);
	}

	/**
	 * Get property value from {@link Collection}.<br>
	 * Default value will be used if:<br>
	 * - type is <code>null</code>;<br>
	 * - props is <code>null</code>;<br>
	 * - aKey is empty;<br>
	 * - property not found;<br>
	 * - property value is <code>null</code>;<br>
	 * - property value type differs.
	 * 
	 * @param type
	 *            property type
	 * @param props
	 *            properties
	 * @param aKey
	 *            property key
	 * @param defValue
	 *            default value
	 * @return property value or <code>null</code>
	 */
	public static <ValueType> ValueType get(final Class<ValueType> type, final Collection<Property<?>> props, final String aKey, final ValueType defValue) {
		if (type == null || props == null || isEmpty(aKey))
			return defValue;

		ValueType value = null;
		for (final Property<?> raw : props) {
			if (raw == null || type != raw.getType().getPropertyValueClass() || !aKey.equals(raw.getKey()))
				continue;

			if (raw.getValue() == null)
				break;

			value = type.cast(raw.getValue());
		}

		if (value != null)
			return value;

		return defValue;
	}

	/**
	 * Get property value from {@link Map}.<br>
	 * Property would be resolved by map key, property key would not be verified.
	 * 
	 * @param type
	 *            property type
	 * @param props
	 *            properties
	 * @param aKey
	 *            property key
	 * @return property value or <code>null</code>
	 */
	public static <ValueType> ValueType get(final Class<ValueType> type, final Map<String, Property<?>> props, final String aKey) {
		return get(type, props, aKey, null);
	}

	/**
	 * Get property value from {@link Map}.<br>
	 * Property would be resolved by map key, property key would not be verified.<br>
	 * Default value will be used if:<br>
	 * - type is <code>null</code>;<br>
	 * - props is <code>null</code>;<br>
	 * - aKey is empty;<br>
	 * - property not found;<br>
	 * - property value is <code>null</code>;<br>
	 * - property value type differs.
	 * 
	 * @param type
	 *            property type
	 * @param props
	 *            properties
	 * @param aKey
	 *            property key
	 * @param defValue
	 *            default value
	 * @return property value or <code>null</code>
	 */
	public static <ValueType> ValueType get(final Class<ValueType> type, final Map<String, Property<?>> props, final String aKey, final ValueType defValue) {
		if (type == null || props == null || isEmpty(aKey))
			return defValue;

		return get(type, props.get(aKey), defValue);
	}

	/**
	 * Get property value from {@link Property}.
	 * 
	 * @param type
	 *            property type
	 * @param prop
	 *            property
	 * @return property value or <code>null</code>
	 */
	public static <ValueType> ValueType get(final Class<ValueType> type, final Property<?> prop) {
		return get(type, prop, null);
	}

	/**
	 * Get property value from {@link Property}.<br>
	 * Default value will be used if:<br>
	 * - type is <code>null</code>;<br>
	 * - prop is <code>null</code>;<br>
	 * - property value is <code>null</code>;<br>
	 * - property value type differs.
	 * 
	 * @param type
	 *            property type
	 * @param prop
	 *            property
	 * @param defValue
	 *            default value
	 * @return property value or <code>null</code>
	 */
	public static <ValueType> ValueType get(final Class<ValueType> type, final Property<?> prop, final ValueType defValue) {
		if (type == null || prop == null)
			return defValue;

		ValueType value = null;
		if (type == prop.getType().getPropertyValueClass() && prop.getValue() != null)
			value = type.cast(prop.getValue());

		if (value != null)
			return value;

		return defValue;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [type=").append(type);
		builder.append(", key=").append(key);
		builder.append(", value=").append(value);
		builder.append(", group=").append(group);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AbstractProperty))
			return false;
		final AbstractProperty<?> other = (AbstractProperty<?>) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	@Override
	public AbstractProperty<ValueType> clone() {
		try {
			@SuppressWarnings("unchecked")
			final AbstractProperty<ValueType> result = AbstractProperty.class.cast(super.clone());
			return result;
		} catch (final CloneNotSupportedException e) {
			throw new AssertionError("Can't clone [" + this + "]");
		}
	}

}
