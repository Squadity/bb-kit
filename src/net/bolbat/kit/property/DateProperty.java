package net.bolbat.kit.property;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * {@link Date} based property.
 * 
 * @author Alexandr Bolbat
 */
public class DateProperty extends AbstractProperty<Date> {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = -6326338080903198042L;

	/**
	 * Default constructor.
	 */
	public DateProperty() {
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
	public DateProperty(final String key, final Date value) {
		super(Properties.DATE, key, value);
	}

	/**
	 * Get {@link Date} property value.
	 * 
	 * @param props
	 *            properties collection
	 * @param aKey
	 *            property key
	 * @return property value or <code>null</code>
	 */
	public static Date get(final Collection<Property<?>> props, final String aKey) {
		return get(props, aKey, null);
	}

	/**
	 * Get {@link Date} property value.<br>
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
	public static Date get(final Collection<Property<?>> props, final String aKey, final Date defValue) {
		return get(Date.class, props, aKey, defValue);
	}

	/**
	 * Get {@link Date} property value.
	 * 
	 * @param props
	 *            properties map
	 * @param aKey
	 *            property key
	 * @return property value or <code>null</code>
	 */
	public static Date get(final Map<String, Property<?>> props, final String aKey) {
		return get(props, aKey, null);
	}

	/**
	 * Get {@link Date} property value.<br>
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
	public static Date get(final Map<String, Property<?>> props, final String aKey, final Date defValue) {
		return get(Date.class, props, aKey, defValue);
	}

	/**
	 * Get {@link Date} property value.
	 * 
	 * @param prop
	 *            property
	 * @return property value or <code>null</code>
	 */
	public static Date get(final Property<?> prop) {
		return get(prop, null);
	}

	/**
	 * Get {@link Date} property value.<br>
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
	public static Date get(final Property<?> prop, final Date defValue) {
		return get(Date.class, prop, defValue);
	}

}
