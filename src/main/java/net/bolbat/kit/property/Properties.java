package net.bolbat.kit.property;

import static net.bolbat.utils.lang.StringUtils.isEmpty;

import java.util.Date;
import java.util.List;

/**
 * Properties enumeration.
 * 
 * @author Alexandr Bolbat
 */
public enum Properties {

	/**
	 * {@link StringProperty}.
	 */
	STRING(StringProperty.class, String.class),

	/**
	 * {@link BooleanProperty}.
	 */
	BOOLEAN(BooleanProperty.class, Boolean.class),

	/**
	 * {@link ShortProperty}.
	 */
	SHORT(ShortProperty.class, Short.class),

	/**
	 * {@link IntegerProperty}.
	 */
	INTEGER(IntegerProperty.class, Integer.class),

	/**
	 * {@link LongProperty}.
	 */
	LONG(LongProperty.class, Long.class),

	/**
	 * {@link FloatProperty}.
	 */
	FLOAT(FloatProperty.class, Float.class),

	/**
	 * {@link DoubleProperty}.
	 */
	DOUBLE(DoubleProperty.class, Double.class),

	/**
	 * {@link DateProperty}.
	 */
	DATE(DateProperty.class, Date.class),

	/**
	 * {@link ListProperty}.
	 */
	LIST(ListProperty.class, List.class);

	/**
	 * Property class.
	 */
	private final Class<?> propertyClass;

	/**
	 * Property value class.
	 */
	private final Class<?> propertyValueClass;

	/**
	 * Default constructor.
	 * 
	 * @param aPropertyClass
	 *            property class
	 */
	Properties(final Class<?> aPropertyClass, final Class<?> aPropertyValueClass) {
		this.propertyClass = aPropertyClass;
		this.propertyValueClass = aPropertyValueClass;
	}

	public Class<?> getPropertyClass() {
		return propertyClass;
	}

	public Class<?> getPropertyValueClass() {
		return propertyValueClass;
	}

	/**
	 * Resolve property type by name.
	 * 
	 * @param name
	 *            property name
	 * @return property type
	 */
	public static Properties resolveByName(final String name) {
		if (isEmpty(name))
			return null;

		for (final Properties property : Properties.values())
			if (property.name().equalsIgnoreCase(name))
				return property;

		return null;
	}

	/**
	 * Resolve property type by property class.
	 * 
	 * @param aPropertyClass
	 *            property class
	 * @return property type
	 */
	public static Properties resolveByPropertyClass(final Class<? extends Property<?>> aPropertyClass) {
		if (aPropertyClass == null)
			return null;

		for (final Properties property : Properties.values())
			if (property.getPropertyClass() == aPropertyClass)
				return property;

		return null;
	}

}
