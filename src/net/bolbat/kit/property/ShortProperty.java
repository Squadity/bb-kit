package net.bolbat.kit.property;

import java.util.Collection;
import java.util.Map;

/**
 * {@link Short} based property.
 * 
 * @author Alexandr Bolbat
 */
public class ShortProperty extends AbstractProperty<Short> {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = -1650681466036561676L;

	/**
	 * Default constructor.
	 */
	public ShortProperty() {
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
	public ShortProperty(final String key, final Short value) {
		super(Properties.SHORT, key, value);
	}

	/**
	 * Get {@link Short} property value.
	 * 
	 * @param props
	 *            properties collection
	 * @param aKey
	 *            property key
	 * @return property value or <code>null</code>
	 */
	public static Short get(final Collection<Property<?>> props, final String aKey) {
		return get(props, aKey, null);
	}

	/**
	 * Get {@link Short} property value.<br>
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
	public static Short get(final Collection<Property<?>> props, final String aKey, final Short defValue) {
		return get(Short.class, props, aKey, defValue);
	}

	/**
	 * Get {@link Short} property value.
	 * 
	 * @param props
	 *            properties map
	 * @param aKey
	 *            property key
	 * @return property value or <code>null</code>
	 */
	public static Short get(final Map<String, Property<?>> props, final String aKey) {
		return get(props, aKey, null);
	}

	/**
	 * Get {@link Short} property value.<br>
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
	public static Short get(final Map<String, Property<?>> props, final String aKey, final Short defValue) {
		return get(Short.class, props, aKey, defValue);
	}

	/**
	 * Get {@link Short} property value.
	 * 
	 * @param prop
	 *            property
	 * @return property value or <code>null</code>
	 */
	public static Short get(final Property<?> prop) {
		return get(prop, null);
	}

	/**
	 * Get {@link Short} property value.<br>
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
	public static Short get(final Property<?> prop, final Short defValue) {
		return get(Short.class, prop, defValue);
	}

}
