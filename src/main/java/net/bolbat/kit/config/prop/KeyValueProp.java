package net.bolbat.kit.config.prop;

import java.io.Serializable;

import org.configureme.annotations.Configure;
import org.configureme.annotations.DontConfigure;

/**
 * Key/Value property.
 * 
 * @author Alexandr Bolbat
 */
public class KeyValueProp implements Serializable {

	/**
	 * Generated SerialVersionUID.
	 */
	@DontConfigure
	private static final long serialVersionUID = -4010591455413905094L;

	/**
	 * Property 'name'.
	 */
	@Configure
	private String name;

	/**
	 * Property 'value'.
	 */
	@Configure
	private String value;

	/**
	 * Default constructor.
	 */
	public KeyValueProp() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param aName
	 *            property name
	 * @param aValue
	 *            property value
	 */
	public KeyValueProp(final String aName, final String aValue) {
		this.name = aName;
		this.value = aValue;
	}

	public String getName() {
		return name;
	}

	public void setName(final String aName) {
		this.name = aName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String aValue) {
		this.value = aValue;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append("[name=").append(name);
		sb.append(", value=").append(value);
		sb.append(']');
		return sb.toString();
	}

}
