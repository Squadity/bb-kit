package net.bolbat.kit.property;

import java.util.Collection;
import java.util.Map;

/**
 * {@link Boolean} based property.
 * 
 * @author Alexandr Bolbat
 */
public class BooleanProperty extends AbstractProperty<Boolean> {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = 2895227800035939100L;

	/**
	 * Default constructor.
	 */
	public BooleanProperty() {
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
	public BooleanProperty(final String key, final Boolean value) {
		super(Properties.BOOLEAN, key, value);
	}

	/**
	 * Get {@link Boolean} property value.
	 * 
	 * @param props
	 *            properties collection
	 * @param aKey
	 *            property key
	 * @return property value or <code>null</code>
	 */
	public static Boolean get(final Collection<Property<?>> props, final String aKey) {
		return get(props, aKey, null);
	}

	/**
	 * Get {@link Boolean} property value.<br>
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
	public static Boolean get(final Collection<Property<?>> props, final String aKey, final Boolean defValue) {
		return get(Boolean.class, props, aKey, defValue);
	}

	/**
	 * Get {@link Boolean} property value.
	 * 
	 * @param props
	 *            properties map
	 * @param aKey
	 *            property key
	 * @return property value or <code>null</code>
	 */
	public static Boolean get(final Map<String, Property<?>> props, final String aKey) {
		return get(props, aKey, null);
	}

	/**
	 * Get {@link Boolean} property value.<br>
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
	public static Boolean get(final Map<String, Property<?>> props, final String aKey, final Boolean defValue) {
		return get(Boolean.class, props, aKey, defValue);
	}

	/**
	 * Get {@link Boolean} property value.
	 * 
	 * @param prop
	 *            property
	 * @return property value or <code>null</code>
	 */
	public static Boolean get(final Property<?> prop) {
		return get(prop, null);
	}

	/**
	 * Get {@link Boolean} property value.<br>
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
	public static Boolean get(final Property<?> prop, final Boolean defValue) {
		return get(Boolean.class, prop, defValue);
	}

}
