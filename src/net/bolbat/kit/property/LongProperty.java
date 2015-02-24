package net.bolbat.kit.property;

import java.util.Collection;
import java.util.Map;

/**
 * {@link Long} based property.
 * 
 * @author Alexandr Bolbat
 */
public class LongProperty extends AbstractProperty<Long> {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = 214407063895973875L;

	/**
	 * Default constructor.
	 */
	public LongProperty() {
		this(null, null);
	}

	/**
	 * Public constructor.
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            value
	 */
	public LongProperty(final String key, final Long value) {
		super(Properties.LONG, key, value);
	}

	/**
	 * Get {@link Long} property value.
	 * 
	 * @param props
	 *            properties collection
	 * @param aKey
	 *            property key
	 * @return property value or <code>null</code>
	 */
	public static Long get(final Collection<Property<?>> props, final String aKey) {
		return get(props, aKey, null);
	}

	/**
	 * Get {@link Long} property value.<br>
	 * Default value will be used if:<br>
	 * - props is <code>null</code>;<br>
	 * - aKey is empty;<br>
	 * - property not found;<br>
	 * - property value is <code>null</code>;<br>
	 * - property value type differs.
	 * 
	 * @param props
	 *            properties collection
	 * @param aKey
	 *            property key
	 * @param defValue
	 *            default property value
	 * @return property value or <code>null</code>
	 */
	public static Long get(final Collection<Property<?>> props, final String aKey, final Long defValue) {
		return get(Long.class, props, aKey, defValue);
	}

	/**
	 * Get {@link Long} property value.
	 * 
	 * @param props
	 *            properties map
	 * @param aKey
	 *            property key
	 * @return property value or <code>null</code>
	 */
	public static Long get(final Map<String, Property<?>> props, final String aKey) {
		return get(props, aKey, null);
	}

	/**
	 * Get {@link Long} property value.<br>
	 * Default value will be used if:<br>
	 * - props is <code>null</code>;<br>
	 * - aKey is empty;<br>
	 * - property not found;<br>
	 * - property value is <code>null</code>;<br>
	 * - property value type differs.
	 * 
	 * @param props
	 *            properties map
	 * @param aKey
	 *            property key
	 * @param defValue
	 *            default property value
	 * @return property value or <code>null</code>
	 */
	public static Long get(final Map<String, Property<?>> props, final String aKey, final Long defValue) {
		return get(Long.class, props, aKey, defValue);
	}

	/**
	 * Get {@link Long} property value.
	 * 
	 * @param prop
	 *            property
	 * @return property value or <code>null</code>
	 */
	public static Long get(final Property<?> prop) {
		return get(prop, null);
	}

	/**
	 * Get {@link Long} property value.<br>
	 * Default value will be used if:<br>
	 * - prop is <code>null</code>;<br>
	 * - property value is <code>null</code>;<br>
	 * - property value type differs.
	 * 
	 * @param prop
	 *            property
	 * @param defValue
	 *            default property value
	 * @return property value or <code>null</code>
	 */
	public static Long get(final Property<?> prop, final Long defValue) {
		return get(Long.class, prop, defValue);
	}

}
