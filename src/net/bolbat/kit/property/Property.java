package net.bolbat.kit.property;

import java.io.Serializable;

/**
 * Property interface.
 * 
 * @author Alexandr Bolbat
 * 
 * @param <ValueType>
 *            value type
 */
public interface Property<ValueType> extends Serializable {

	/**
	 * Get property type.
	 * 
	 * @return property type
	 */
	Properties getType();

	/**
	 * Get property key.
	 * 
	 * @return property key
	 */
	String getKey();

	/**
	 * Get property value.
	 * 
	 * @return property value
	 */
	ValueType getValue();

	/**
	 * Get property group.<br>
	 * Optional field for any custom properties grouping.
	 * 
	 * @return property group
	 */
	String getGroup();

}
