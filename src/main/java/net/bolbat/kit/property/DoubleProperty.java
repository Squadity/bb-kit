package net.bolbat.kit.property;

import java.util.Collection;
import java.util.Map;

/**
 * {@link Double} based property.
 * 
 * @author Alexandr Bolbat
 */
public class DoubleProperty extends AbstractProperty<Double> {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = 7681332040623605934L;

	/**
	 * Default constructor.
	 */
	public DoubleProperty() {
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
	public DoubleProperty(final String key, final Double value) {
		super(Properties.DOUBLE, key, value);
	}

	/**
	 * Get {@link Double} property value.
	 * 
	 * @param props
	 *            properties collection
	 * @param aKey
	 *            property key
	 * @return property value or <code>null</code>
	 */
	public static Double get(final Collection<Property<?>> props, final String aKey) {
		return get(props, aKey, null);
	}

	/**
	 * Get {@link Double} property value.<br>
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
	public static Double get(final Collection<Property<?>> props, final String aKey, final Double defValue) {
		return get(Double.class, props, aKey, defValue);
	}

	/**
	 * Get {@link Double} property value.
	 * 
	 * @param props
	 *            properties map
	 * @param aKey
	 *            property key
	 * @return property value or <code>null</code>
	 */
	public static Double get(final Map<String, Property<?>> props, final String aKey) {
		return get(props, aKey, null);
	}

	/**
	 * Get {@link Double} property value.<br>
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
	public static Double get(final Map<String, Property<?>> props, final String aKey, final Double defValue) {
		return get(Double.class, props, aKey, defValue);
	}

	/**
	 * Get {@link Double} property value.
	 * 
	 * @param prop
	 *            property
	 * @return property value or <code>null</code>
	 */
	public static Double get(final Property<?> prop) {
		return get(prop, null);
	}

	/**
	 * Get {@link Double} property value.<br>
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
	public static Double get(final Property<?> prop, final Double defValue) {
		return get(Double.class, prop, defValue);
	}

}
