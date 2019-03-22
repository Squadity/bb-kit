package net.bolbat.kit.property;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.bolbat.utils.lang.CastUtils;

/**
 * {@link List} based property.
 * 
 * @author Alexandr Bolbat
 */
public class ListProperty extends AbstractProperty<List<Property<?>>> {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = 7082253212602909360L;

	/**
	 * Default constructor.
	 */
	public ListProperty() {
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
	public ListProperty(final String key, final List<Property<?>> value) {
		super(Properties.LIST, key, value);
	}

	/**
	 * Get {@link List} property value.
	 * 
	 * @param props
	 *            properties collection
	 * @param aKey
	 *            property key
	 * @return property value or <code>null</code>
	 */
	public static List<Property<?>> get(final Collection<Property<?>> props, final String aKey) {
		return get(props, aKey, null);
	}

	/**
	 * Get {@link List} property value.<br>
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
	public static List<Property<?>> get(final Collection<Property<?>> props, final String aKey, final List<Property<?>> defValue) {
		return CastUtils.castList(get(List.class, props, aKey, defValue));
	}

	/**
	 * Get {@link List} property value.
	 * 
	 * @param props
	 *            properties map
	 * @param aKey
	 *            property key
	 * @return property value or <code>null</code>
	 */
	public static List<Property<?>> get(final Map<String, Property<?>> props, final String aKey) {
		return get(props, aKey, null);
	}

	/**
	 * Get {@link List} property value.<br>
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
	public static List<Property<?>> get(final Map<String, Property<?>> props, final String aKey, final List<Property<?>> defValue) {
		return CastUtils.castList(get(List.class, props, aKey, defValue));
	}

	/**
	 * Get {@link List} property value.
	 * 
	 * @param prop
	 *            property
	 * @return property value or <code>null</code>
	 */
	public static List<Property<?>> get(final Property<?> prop) {
		return get(prop, null);
	}

	/**
	 * Get {@link List} property value.<br>
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
	public static List<Property<?>> get(final Property<?> prop, final List<Property<?>> defValue) {
		return CastUtils.castList(get(List.class, prop, defValue));
	}

}
