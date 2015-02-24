package net.bolbat.kit.property;

import java.util.Collection;
import java.util.Map;

/**
 * {@link Float} based property.
 * 
 * @author Alexandr Bolbat
 */
public class FloatProperty extends AbstractProperty<Float> {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = 7681332040623605934L;

	/**
	 * Default constructor.
	 */
	public FloatProperty() {
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
	public FloatProperty(final String key, final Float value) {
		super(Properties.FLOAT, key, value);
	}

	/**
	 * Get {@link Float} property value.
	 * 
	 * @param props
	 *            properties collection
	 * @param aKey
	 *            property key
	 * @return property value or <code>null</code>
	 */
	public static Float get(final Collection<Property<?>> props, final String aKey) {
		return get(props, aKey, null);
	}

	/**
	 * Get {@link Float} property value.<br>
	 * Default value will be used if:<br>
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
	public static Float get(final Collection<Property<?>> props, final String aKey, final Float defValue) {
		return get(Float.class, props, aKey, defValue);
	}

	/**
	 * Get {@link Float} property value.
	 * 
	 * @param props
	 *            properties map
	 * @param aKey
	 *            property key
	 * @return property value or <code>null</code>
	 */
	public static Float get(final Map<String, Property<?>> props, final String aKey) {
		return get(props, aKey, null);
	}

	/**
	 * Get {@link Float} property value.<br>
	 * Default value will be used if:<br>
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
	public static Float get(final Map<String, Property<?>> props, final String aKey, final Float defValue) {
		return get(Float.class, props, aKey, defValue);
	}

	/**
	 * Get {@link Float} property value.
	 * 
	 * @param prop
	 *            property
	 * @return property value or <code>null</code>
	 */
	public static Float get(final Property<?> prop) {
		return get(prop, null);
	}

	/**
	 * Get {@link Float} property value.<br>
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
	public static Float get(final Property<?> prop, final Float defValue) {
		return get(Float.class, prop, defValue);
	}

}
