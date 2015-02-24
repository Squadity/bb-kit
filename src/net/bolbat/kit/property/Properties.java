package net.bolbat.kit.property;

import java.util.Date;

/**
 * {@link Property} related utilities.
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
	DATE(DateProperty.class, Date.class);

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
	private Properties(final Class<?> aPropertyClass, final Class<?> aPropertyValueClass) {
		this.propertyClass = aPropertyClass;
		this.propertyValueClass = aPropertyValueClass;
	}

	public Class<?> getPropertyClass() {
		return propertyClass;
	}

	public Class<?> getPropertyValueClass() {
		return propertyValueClass;
	}

}
