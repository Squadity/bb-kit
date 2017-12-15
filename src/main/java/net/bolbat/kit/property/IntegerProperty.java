package net.bolbat.kit.property;

import java.util.Collection;
import java.util.Map;

/**
 * {@link Integer} based property.
 * 
 * @author Alexandr Bolbat
 */
public class IntegerProperty extends AbstractProperty<Integer> {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = 9051580868135859306L;

	/**
	 * Default constructor.
	 */
	public IntegerProperty() {
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
	public IntegerProperty(final String key, final Integer value) {
		super(Properties.INTEGER, key, value);
	}

	/**
	 * Get {@link Integer} property value.
	 * 
	 * @param props
	 *            properties collection
	 * @param aKey
	 *            property key
	 * @return property value or <code>null</code>
	 */
	public static Integer get(final Collection<Property<?>> props, final String aKey) {
		return get(props, aKey, null);
	}

	/**
	 * Get {@link Integer} property value.<br>
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
	public static Integer get(final Collection<Property<?>> props, final String aKey, final Integer defValue) {
		return get(Integer.class, props, aKey, defValue);
	}

	/**
	 * Get {@link Integer} property value.
	 * 
	 * @param props
	 *            properties map
	 * @param aKey
	 *            property key
	 * @return property value or <code>null</code>
	 */
	public static Integer get(final Map<String, Property<?>> props, final String aKey) {
		return get(props, aKey, null);
	}

	/**
	 * Get {@link Integer} property value.<br>
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
	public static Integer get(final Map<String, Property<?>> props, final String aKey, final Integer defValue) {
		return get(Integer.class, props, aKey, defValue);
	}

	/**
	 * Get {@link Integer} property value.
	 * 
	 * @param prop
	 *            property
	 * @return property value or <code>null</code>
	 */
	public static Integer get(final Property<?> prop) {
		return get(prop, null);
	}

	/**
	 * Get {@link Integer} property value.<br>
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
	public static Integer get(final Property<?> prop, final Integer defValue) {
		return get(Integer.class, prop, defValue);
	}

}
