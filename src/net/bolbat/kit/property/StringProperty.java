package net.bolbat.kit.property;

import java.util.Collection;
import java.util.Map;

/**
 * {@link String} based property.
 * 
 * @author Alexandr Bolbat
 */
public class StringProperty extends AbstractProperty<String> {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = 248211999039306769L;

	/**
	 * Default constructor.
	 */
	public StringProperty() {
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
	public StringProperty(final String key, final String value) {
		super(Properties.STRING, key, value);
	}

	/**
	 * Get {@link String} property value.
	 * 
	 * @param props
	 *            properties collection
	 * @param aKey
	 *            property key
	 * @return property value or <code>null</code>
	 */
	public static String get(final Collection<Property<?>> props, final String aKey) {
		return get(props, aKey, null);
	}

	/**
	 * Get {@link String} property value.<br>
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
	public static String get(final Collection<Property<?>> props, final String aKey, final String defValue) {
		return get(String.class, props, aKey, defValue);
	}

	/**
	 * Get {@link String} property value.
	 * 
	 * @param props
	 *            properties map
	 * @param aKey
	 *            property key
	 * @return property value or <code>null</code>
	 */
	public static String get(final Map<String, Property<?>> props, final String aKey) {
		return get(props, aKey, null);
	}

	/**
	 * Get {@link String} property value.<br>
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
	public static String get(final Map<String, Property<?>> props, final String aKey, final String defValue) {
		return get(String.class, props, aKey, defValue);
	}

	/**
	 * Get {@link String} property value.
	 * 
	 * @param prop
	 *            property
	 * @return property value or <code>null</code>
	 */
	public static String get(final Property<?> prop) {
		return get(prop, null);
	}

	/**
	 * Get {@link String} property value.<br>
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
	public static String get(final Property<?> prop, final String defValue) {
		return get(String.class, prop, defValue);
	}

}
