package net.bolbat.kit.property;

import static net.bolbat.utils.lang.StringUtils.EMPTY;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link Properties} test.
 * 
 * @author Alexandr Bolbat
 */
public class PropertiesTest {

	private final String defaultString = "defaultString";
	private final Boolean defaultBoolean = Boolean.FALSE;
	private final Short defaultShort = Short.MIN_VALUE;
	private final Integer defaultInteger = Integer.MIN_VALUE;
	private final Long defaultLong = Long.MIN_VALUE;
	private final Float defaultFloat = Float.MIN_VALUE;
	private final Double defaultDouble = Double.MIN_VALUE;
	private final Date defaultDate = new Date(0L);

	private final String notExistKey = "notExist";

	private final StringProperty stringProperty = new StringProperty("stringKey", "stringValue");
	private final BooleanProperty booleanProperty = new BooleanProperty("booleanKey", Boolean.TRUE);
	private final ShortProperty shortProperty = new ShortProperty("shortKey", Short.MAX_VALUE);
	private final IntegerProperty integerProperty = new IntegerProperty("integerKey", Integer.MAX_VALUE);
	private final LongProperty longProperty = new LongProperty("longKey", Long.MAX_VALUE);
	private final FloatProperty floatProperty = new FloatProperty("floatKey", Float.MAX_VALUE);
	private final DoubleProperty doubleProperty = new DoubleProperty("doubleKey", Double.MAX_VALUE);
	private final DateProperty dateProperty = new DateProperty("dateKey", new Date());

	private final Collection<Property<?>> propertiesCollection = new ArrayList<>();
	private final Map<String, Property<?>> propertiesMap = new HashMap<>();

	@Before
	public void before() {
		propertiesCollection.clear();
		propertiesCollection.add(stringProperty);
		propertiesCollection.add(booleanProperty);
		propertiesCollection.add(shortProperty);
		propertiesCollection.add(integerProperty);
		propertiesCollection.add(longProperty);
		propertiesCollection.add(floatProperty);
		propertiesCollection.add(doubleProperty);
		propertiesCollection.add(dateProperty);

		propertiesMap.clear();
		propertiesMap.put(stringProperty.getKey(), stringProperty);
		propertiesMap.put(booleanProperty.getKey(), booleanProperty);
		propertiesMap.put(shortProperty.getKey(), shortProperty);
		propertiesMap.put(integerProperty.getKey(), integerProperty);
		propertiesMap.put(longProperty.getKey(), longProperty);
		propertiesMap.put(floatProperty.getKey(), floatProperty);
		propertiesMap.put(doubleProperty.getKey(), doubleProperty);
		propertiesMap.put(dateProperty.getKey(), dateProperty);
	}

	/**
	 * Test for general cases.
	 */
	@Test
	public void generalCasesTest() {
		Assert.assertEquals(stringProperty.getValue(), StringProperty.get(stringProperty));
		Assert.assertEquals(stringProperty.getValue(), StringProperty.get(String.class, stringProperty));
		Assert.assertEquals(stringProperty.getValue(), StringProperty.get(propertiesCollection, stringProperty.getKey()));
		Assert.assertEquals(stringProperty.getValue(), StringProperty.get(String.class, propertiesCollection, stringProperty.getKey()));
		Assert.assertEquals(stringProperty.getValue(), StringProperty.get(propertiesMap, stringProperty.getKey()));
		Assert.assertEquals(stringProperty.getValue(), StringProperty.get(String.class, propertiesMap, stringProperty.getKey()));

		Assert.assertEquals(booleanProperty.getValue(), BooleanProperty.get(booleanProperty));
		Assert.assertEquals(booleanProperty.getValue(), BooleanProperty.get(Boolean.class, booleanProperty));
		Assert.assertEquals(booleanProperty.getValue(), BooleanProperty.get(propertiesCollection, booleanProperty.getKey()));
		Assert.assertEquals(booleanProperty.getValue(), BooleanProperty.get(Boolean.class, propertiesCollection, booleanProperty.getKey()));
		Assert.assertEquals(booleanProperty.getValue(), BooleanProperty.get(propertiesMap, booleanProperty.getKey()));
		Assert.assertEquals(booleanProperty.getValue(), BooleanProperty.get(Boolean.class, propertiesMap, booleanProperty.getKey()));

		Assert.assertEquals(shortProperty.getValue(), ShortProperty.get(shortProperty));
		Assert.assertEquals(shortProperty.getValue(), ShortProperty.get(Short.class, shortProperty));
		Assert.assertEquals(shortProperty.getValue(), ShortProperty.get(propertiesCollection, shortProperty.getKey()));
		Assert.assertEquals(shortProperty.getValue(), ShortProperty.get(Short.class, propertiesCollection, shortProperty.getKey()));
		Assert.assertEquals(shortProperty.getValue(), ShortProperty.get(propertiesMap, shortProperty.getKey()));
		Assert.assertEquals(shortProperty.getValue(), ShortProperty.get(Short.class, propertiesMap, shortProperty.getKey()));

		Assert.assertEquals(integerProperty.getValue(), IntegerProperty.get(integerProperty));
		Assert.assertEquals(integerProperty.getValue(), IntegerProperty.get(Integer.class, integerProperty));
		Assert.assertEquals(integerProperty.getValue(), IntegerProperty.get(propertiesCollection, integerProperty.getKey()));
		Assert.assertEquals(integerProperty.getValue(), IntegerProperty.get(Integer.class, propertiesCollection, integerProperty.getKey()));
		Assert.assertEquals(integerProperty.getValue(), IntegerProperty.get(propertiesMap, integerProperty.getKey()));
		Assert.assertEquals(integerProperty.getValue(), IntegerProperty.get(Integer.class, propertiesMap, integerProperty.getKey()));

		Assert.assertEquals(longProperty.getValue(), LongProperty.get(longProperty));
		Assert.assertEquals(longProperty.getValue(), LongProperty.get(Long.class, longProperty));
		Assert.assertEquals(longProperty.getValue(), LongProperty.get(propertiesCollection, longProperty.getKey()));
		Assert.assertEquals(longProperty.getValue(), LongProperty.get(Long.class, propertiesCollection, longProperty.getKey()));
		Assert.assertEquals(longProperty.getValue(), LongProperty.get(propertiesMap, longProperty.getKey()));
		Assert.assertEquals(longProperty.getValue(), LongProperty.get(Long.class, propertiesMap, longProperty.getKey()));

		Assert.assertEquals(floatProperty.getValue(), FloatProperty.get(floatProperty));
		Assert.assertEquals(floatProperty.getValue(), FloatProperty.get(Float.class, floatProperty));
		Assert.assertEquals(floatProperty.getValue(), FloatProperty.get(propertiesCollection, floatProperty.getKey()));
		Assert.assertEquals(floatProperty.getValue(), FloatProperty.get(Float.class, propertiesCollection, floatProperty.getKey()));
		Assert.assertEquals(floatProperty.getValue(), FloatProperty.get(propertiesMap, floatProperty.getKey()));
		Assert.assertEquals(floatProperty.getValue(), FloatProperty.get(Float.class, propertiesMap, floatProperty.getKey()));

		Assert.assertEquals(doubleProperty.getValue(), DoubleProperty.get(Double.class, doubleProperty));
		Assert.assertEquals(doubleProperty.getValue(), DoubleProperty.get(doubleProperty));
		Assert.assertEquals(doubleProperty.getValue(), DoubleProperty.get(propertiesCollection, doubleProperty.getKey()));
		Assert.assertEquals(doubleProperty.getValue(), DoubleProperty.get(Double.class, propertiesCollection, doubleProperty.getKey()));
		Assert.assertEquals(doubleProperty.getValue(), DoubleProperty.get(propertiesMap, doubleProperty.getKey()));
		Assert.assertEquals(doubleProperty.getValue(), DoubleProperty.get(Double.class, propertiesMap, doubleProperty.getKey()));

		Assert.assertEquals(dateProperty.getValue(), DateProperty.get(dateProperty));
		Assert.assertEquals(dateProperty.getValue(), DateProperty.get(Date.class, dateProperty));
		Assert.assertEquals(dateProperty.getValue(), DateProperty.get(propertiesCollection, dateProperty.getKey()));
		Assert.assertEquals(dateProperty.getValue(), DateProperty.get(Date.class, propertiesCollection, dateProperty.getKey()));
		Assert.assertEquals(dateProperty.getValue(), DateProperty.get(propertiesMap, dateProperty.getKey()));
		Assert.assertEquals(dateProperty.getValue(), DateProperty.get(Date.class, propertiesMap, dateProperty.getKey()));
	}

	/**
	 * Test for <code>null</code> arguments cases.
	 */
	@Test
	public void nullArgsTest() {
		Assert.assertNull(StringProperty.get((Property<?>) null));
		Assert.assertNull(StringProperty.get(null, (Property<?>) null));
		Assert.assertNull(StringProperty.get(propertiesCollection, null));
		Assert.assertNull(StringProperty.get((Collection<Property<?>>) null, stringProperty.getKey()));
		Assert.assertNull(StringProperty.get((Collection<Property<?>>) null, null));
		Assert.assertNull(StringProperty.get(null, (Collection<Property<?>>) null, null));
		Assert.assertNull(StringProperty.get(propertiesMap, null));
		Assert.assertNull(StringProperty.get((Map<String, Property<?>>) null, stringProperty.getKey()));
		Assert.assertNull(StringProperty.get((Map<String, Property<?>>) null, null));
		Assert.assertNull(StringProperty.get(null, (Map<String, Property<?>>) null, null));

		Assert.assertNull(BooleanProperty.get((Property<?>) null));
		Assert.assertNull(BooleanProperty.get(null, (Property<?>) null));
		Assert.assertNull(BooleanProperty.get(propertiesCollection, null));
		Assert.assertNull(BooleanProperty.get((Collection<Property<?>>) null, booleanProperty.getKey()));
		Assert.assertNull(BooleanProperty.get((Collection<Property<?>>) null, null));
		Assert.assertNull(BooleanProperty.get(null, (Collection<Property<?>>) null, null));
		Assert.assertNull(BooleanProperty.get(propertiesMap, null));
		Assert.assertNull(BooleanProperty.get((Map<String, Property<?>>) null, booleanProperty.getKey()));
		Assert.assertNull(BooleanProperty.get((Map<String, Property<?>>) null, null));
		Assert.assertNull(BooleanProperty.get(null, (Map<String, Property<?>>) null, null));

		Assert.assertNull(ShortProperty.get((Property<?>) null));
		Assert.assertNull(ShortProperty.get(null, (Property<?>) null));
		Assert.assertNull(ShortProperty.get(propertiesCollection, null));
		Assert.assertNull(ShortProperty.get((Collection<Property<?>>) null, shortProperty.getKey()));
		Assert.assertNull(ShortProperty.get((Collection<Property<?>>) null, null));
		Assert.assertNull(ShortProperty.get(null, (Collection<Property<?>>) null, null));
		Assert.assertNull(ShortProperty.get(propertiesMap, null));
		Assert.assertNull(ShortProperty.get((Map<String, Property<?>>) null, shortProperty.getKey()));
		Assert.assertNull(ShortProperty.get((Map<String, Property<?>>) null, null));
		Assert.assertNull(ShortProperty.get(null, (Map<String, Property<?>>) null, null));

		Assert.assertNull(IntegerProperty.get((Property<?>) null));
		Assert.assertNull(IntegerProperty.get(null, (Property<?>) null));
		Assert.assertNull(IntegerProperty.get(propertiesCollection, null));
		Assert.assertNull(IntegerProperty.get((Collection<Property<?>>) null, integerProperty.getKey()));
		Assert.assertNull(IntegerProperty.get((Collection<Property<?>>) null, null));
		Assert.assertNull(IntegerProperty.get(null, (Collection<Property<?>>) null, null));
		Assert.assertNull(IntegerProperty.get(propertiesMap, null));
		Assert.assertNull(IntegerProperty.get((Map<String, Property<?>>) null, integerProperty.getKey()));
		Assert.assertNull(IntegerProperty.get((Map<String, Property<?>>) null, null));
		Assert.assertNull(IntegerProperty.get(null, (Map<String, Property<?>>) null, null));

		Assert.assertNull(LongProperty.get((Property<?>) null));
		Assert.assertNull(LongProperty.get(null, (Property<?>) null));
		Assert.assertNull(LongProperty.get(propertiesCollection, null));
		Assert.assertNull(LongProperty.get((Collection<Property<?>>) null, longProperty.getKey()));
		Assert.assertNull(LongProperty.get((Collection<Property<?>>) null, null));
		Assert.assertNull(LongProperty.get(null, (Collection<Property<?>>) null, null));
		Assert.assertNull(LongProperty.get(propertiesMap, null));
		Assert.assertNull(LongProperty.get((Map<String, Property<?>>) null, longProperty.getKey()));
		Assert.assertNull(LongProperty.get((Map<String, Property<?>>) null, null));
		Assert.assertNull(LongProperty.get(null, (Map<String, Property<?>>) null, null));

		Assert.assertNull(FloatProperty.get((Property<?>) null));
		Assert.assertNull(FloatProperty.get(null, (Property<?>) null));
		Assert.assertNull(FloatProperty.get(propertiesCollection, null));
		Assert.assertNull(FloatProperty.get((Collection<Property<?>>) null, floatProperty.getKey()));
		Assert.assertNull(FloatProperty.get((Collection<Property<?>>) null, null));
		Assert.assertNull(FloatProperty.get(null, (Collection<Property<?>>) null, null));
		Assert.assertNull(FloatProperty.get(propertiesMap, null));
		Assert.assertNull(FloatProperty.get((Map<String, Property<?>>) null, floatProperty.getKey()));
		Assert.assertNull(FloatProperty.get((Map<String, Property<?>>) null, null));
		Assert.assertNull(FloatProperty.get(null, (Map<String, Property<?>>) null, null));

		Assert.assertNull(DoubleProperty.get((Property<?>) null));
		Assert.assertNull(DoubleProperty.get(null, (Property<?>) null));
		Assert.assertNull(DoubleProperty.get(propertiesCollection, null));
		Assert.assertNull(DoubleProperty.get((Collection<Property<?>>) null, doubleProperty.getKey()));
		Assert.assertNull(DoubleProperty.get((Collection<Property<?>>) null, null));
		Assert.assertNull(DoubleProperty.get(null, (Collection<Property<?>>) null, null));
		Assert.assertNull(DoubleProperty.get(propertiesMap, null));
		Assert.assertNull(DoubleProperty.get((Map<String, Property<?>>) null, doubleProperty.getKey()));
		Assert.assertNull(DoubleProperty.get((Map<String, Property<?>>) null, null));
		Assert.assertNull(DoubleProperty.get(null, (Map<String, Property<?>>) null, null));

		Assert.assertNull(DateProperty.get((Property<?>) null));
		Assert.assertNull(DateProperty.get(null, (Property<?>) null));
		Assert.assertNull(DateProperty.get(propertiesCollection, null));
		Assert.assertNull(DateProperty.get((Collection<Property<?>>) null, dateProperty.getKey()));
		Assert.assertNull(DateProperty.get((Collection<Property<?>>) null, null));
		Assert.assertNull(DateProperty.get(null, (Collection<Property<?>>) null, null));
		Assert.assertNull(DateProperty.get(propertiesMap, null));
		Assert.assertNull(DateProperty.get((Map<String, Property<?>>) null, dateProperty.getKey()));
		Assert.assertNull(DateProperty.get((Map<String, Property<?>>) null, null));
		Assert.assertNull(DateProperty.get(null, (Map<String, Property<?>>) null, null));
	}

	/**
	 * Test for cases with default value.
	 */
	@Test
	public void defaultValueCasesTest() {
		Assert.assertEquals(defaultString, StringProperty.get((Property<?>) null, defaultString));
		Assert.assertEquals(defaultString, StringProperty.get(null, (Property<?>) null, defaultString));
		Assert.assertEquals(defaultString, StringProperty.get(propertiesCollection, null, defaultString));
		Assert.assertEquals(defaultString, StringProperty.get(propertiesCollection, notExistKey, defaultString));
		Assert.assertEquals(defaultString, StringProperty.get((Collection<Property<?>>) null, stringProperty.getKey(), defaultString));
		Assert.assertEquals(defaultString, StringProperty.get((Collection<Property<?>>) null, null, defaultString));
		Assert.assertEquals(defaultString, StringProperty.get(String.class, (Collection<Property<?>>) null, null, defaultString));
		Assert.assertEquals(defaultString, StringProperty.get(propertiesMap, null, defaultString));
		Assert.assertEquals(defaultString, StringProperty.get(propertiesMap, notExistKey, defaultString));
		Assert.assertEquals(defaultString, StringProperty.get((Map<String, Property<?>>) null, stringProperty.getKey(), defaultString));
		Assert.assertEquals(defaultString, StringProperty.get((Map<String, Property<?>>) null, null, defaultString));
		Assert.assertEquals(defaultString, StringProperty.get(String.class, (Map<String, Property<?>>) null, null, defaultString));

		Assert.assertEquals(defaultBoolean, BooleanProperty.get((Property<?>) null, defaultBoolean));
		Assert.assertEquals(defaultBoolean, BooleanProperty.get(null, (Property<?>) null, defaultBoolean));
		Assert.assertEquals(defaultBoolean, BooleanProperty.get(propertiesCollection, null, defaultBoolean));
		Assert.assertEquals(defaultBoolean, BooleanProperty.get(propertiesCollection, notExistKey, defaultBoolean));
		Assert.assertEquals(defaultBoolean, BooleanProperty.get((Collection<Property<?>>) null, booleanProperty.getKey(), defaultBoolean));
		Assert.assertEquals(defaultBoolean, BooleanProperty.get((Collection<Property<?>>) null, null, defaultBoolean));
		Assert.assertEquals(defaultBoolean, BooleanProperty.get(Boolean.class, (Collection<Property<?>>) null, null, defaultBoolean));
		Assert.assertEquals(defaultBoolean, BooleanProperty.get(propertiesMap, null, defaultBoolean));
		Assert.assertEquals(defaultBoolean, BooleanProperty.get(propertiesMap, notExistKey, defaultBoolean));
		Assert.assertEquals(defaultBoolean, BooleanProperty.get((Map<String, Property<?>>) null, booleanProperty.getKey(), defaultBoolean));
		Assert.assertEquals(defaultBoolean, BooleanProperty.get((Map<String, Property<?>>) null, null, defaultBoolean));
		Assert.assertEquals(defaultBoolean, BooleanProperty.get(Boolean.class, (Map<String, Property<?>>) null, null, defaultBoolean));

		Assert.assertEquals(defaultShort, ShortProperty.get((Property<?>) null, defaultShort));
		Assert.assertEquals(defaultShort, ShortProperty.get(null, (Property<?>) null, defaultShort));
		Assert.assertEquals(defaultShort, ShortProperty.get(propertiesCollection, null, defaultShort));
		Assert.assertEquals(defaultShort, ShortProperty.get(propertiesCollection, notExistKey, defaultShort));
		Assert.assertEquals(defaultShort, ShortProperty.get((Collection<Property<?>>) null, shortProperty.getKey(), defaultShort));
		Assert.assertEquals(defaultShort, ShortProperty.get((Collection<Property<?>>) null, null, defaultShort));
		Assert.assertEquals(defaultShort, ShortProperty.get(Short.class, (Collection<Property<?>>) null, null, defaultShort));
		Assert.assertEquals(defaultShort, ShortProperty.get(propertiesMap, null, defaultShort));
		Assert.assertEquals(defaultShort, ShortProperty.get(propertiesMap, notExistKey, defaultShort));
		Assert.assertEquals(defaultShort, ShortProperty.get((Map<String, Property<?>>) null, shortProperty.getKey(), defaultShort));
		Assert.assertEquals(defaultShort, ShortProperty.get((Map<String, Property<?>>) null, null, defaultShort));
		Assert.assertEquals(defaultShort, ShortProperty.get(Short.class, (Map<String, Property<?>>) null, null, defaultShort));

		Assert.assertEquals(defaultInteger, IntegerProperty.get((Property<?>) null, defaultInteger));
		Assert.assertEquals(defaultInteger, IntegerProperty.get(null, (Property<?>) null, defaultInteger));
		Assert.assertEquals(defaultInteger, IntegerProperty.get(propertiesCollection, null, defaultInteger));
		Assert.assertEquals(defaultInteger, IntegerProperty.get(propertiesCollection, notExistKey, defaultInteger));
		Assert.assertEquals(defaultInteger, IntegerProperty.get((Collection<Property<?>>) null, integerProperty.getKey(), defaultInteger));
		Assert.assertEquals(defaultInteger, IntegerProperty.get((Collection<Property<?>>) null, null, defaultInteger));
		Assert.assertEquals(defaultInteger, IntegerProperty.get(Integer.class, (Collection<Property<?>>) null, null, defaultInteger));
		Assert.assertEquals(defaultInteger, IntegerProperty.get(propertiesMap, null, defaultInteger));
		Assert.assertEquals(defaultInteger, IntegerProperty.get(propertiesMap, notExistKey, defaultInteger));
		Assert.assertEquals(defaultInteger, IntegerProperty.get((Map<String, Property<?>>) null, integerProperty.getKey(), defaultInteger));
		Assert.assertEquals(defaultInteger, IntegerProperty.get((Map<String, Property<?>>) null, null, defaultInteger));
		Assert.assertEquals(defaultInteger, IntegerProperty.get(Integer.class, (Map<String, Property<?>>) null, null, defaultInteger));

		Assert.assertEquals(defaultLong, LongProperty.get((Property<?>) null, defaultLong));
		Assert.assertEquals(defaultLong, LongProperty.get(null, (Property<?>) null, defaultLong));
		Assert.assertEquals(defaultLong, LongProperty.get(propertiesCollection, null, defaultLong));
		Assert.assertEquals(defaultLong, LongProperty.get(propertiesCollection, notExistKey, defaultLong));
		Assert.assertEquals(defaultLong, LongProperty.get((Collection<Property<?>>) null, longProperty.getKey(), defaultLong));
		Assert.assertEquals(defaultLong, LongProperty.get((Collection<Property<?>>) null, null, defaultLong));
		Assert.assertEquals(defaultLong, LongProperty.get(Long.class, (Collection<Property<?>>) null, null, defaultLong));
		Assert.assertEquals(defaultLong, LongProperty.get(propertiesMap, null, defaultLong));
		Assert.assertEquals(defaultLong, LongProperty.get(propertiesMap, notExistKey, defaultLong));
		Assert.assertEquals(defaultLong, LongProperty.get((Map<String, Property<?>>) null, longProperty.getKey(), defaultLong));
		Assert.assertEquals(defaultLong, LongProperty.get((Map<String, Property<?>>) null, null, defaultLong));
		Assert.assertEquals(defaultLong, LongProperty.get(Long.class, (Map<String, Property<?>>) null, null, defaultLong));

		Assert.assertEquals(defaultFloat, FloatProperty.get((Property<?>) null, defaultFloat));
		Assert.assertEquals(defaultFloat, FloatProperty.get(null, (Property<?>) null, defaultFloat));
		Assert.assertEquals(defaultFloat, FloatProperty.get(propertiesCollection, null, defaultFloat));
		Assert.assertEquals(defaultFloat, FloatProperty.get(propertiesCollection, notExistKey, defaultFloat));
		Assert.assertEquals(defaultFloat, FloatProperty.get((Collection<Property<?>>) null, floatProperty.getKey(), defaultFloat));
		Assert.assertEquals(defaultFloat, FloatProperty.get((Collection<Property<?>>) null, null, defaultFloat));
		Assert.assertEquals(defaultFloat, FloatProperty.get(Float.class, (Collection<Property<?>>) null, null, defaultFloat));
		Assert.assertEquals(defaultFloat, FloatProperty.get(propertiesMap, null, defaultFloat));
		Assert.assertEquals(defaultFloat, FloatProperty.get(propertiesMap, notExistKey, defaultFloat));
		Assert.assertEquals(defaultFloat, FloatProperty.get((Map<String, Property<?>>) null, floatProperty.getKey(), defaultFloat));
		Assert.assertEquals(defaultFloat, FloatProperty.get((Map<String, Property<?>>) null, null, defaultFloat));
		Assert.assertEquals(defaultFloat, FloatProperty.get(Float.class, (Map<String, Property<?>>) null, null, defaultFloat));

		Assert.assertEquals(defaultDouble, DoubleProperty.get((Property<?>) null, defaultDouble));
		Assert.assertEquals(defaultDouble, DoubleProperty.get(null, (Property<?>) null, defaultDouble));
		Assert.assertEquals(defaultDouble, DoubleProperty.get(propertiesCollection, null, defaultDouble));
		Assert.assertEquals(defaultDouble, DoubleProperty.get(propertiesCollection, notExistKey, defaultDouble));
		Assert.assertEquals(defaultDouble, DoubleProperty.get((Collection<Property<?>>) null, doubleProperty.getKey(), defaultDouble));
		Assert.assertEquals(defaultDouble, DoubleProperty.get((Collection<Property<?>>) null, null, defaultDouble));
		Assert.assertEquals(defaultDouble, DoubleProperty.get(Double.class, (Collection<Property<?>>) null, null, defaultDouble));
		Assert.assertEquals(defaultDouble, DoubleProperty.get(propertiesMap, null, defaultDouble));
		Assert.assertEquals(defaultDouble, DoubleProperty.get(propertiesMap, notExistKey, defaultDouble));
		Assert.assertEquals(defaultDouble, DoubleProperty.get((Map<String, Property<?>>) null, doubleProperty.getKey(), defaultDouble));
		Assert.assertEquals(defaultDouble, DoubleProperty.get((Map<String, Property<?>>) null, null, defaultDouble));
		Assert.assertEquals(defaultDouble, DoubleProperty.get(Double.class, (Map<String, Property<?>>) null, null, defaultDouble));

		Assert.assertEquals(defaultDate, DateProperty.get((Property<?>) null, defaultDate));
		Assert.assertEquals(defaultDate, DateProperty.get(null, (Property<?>) null, defaultDate));
		Assert.assertEquals(defaultDate, DateProperty.get(propertiesCollection, null, defaultDate));
		Assert.assertEquals(defaultDate, DateProperty.get(propertiesCollection, notExistKey, defaultDate));
		Assert.assertEquals(defaultDate, DateProperty.get((Collection<Property<?>>) null, dateProperty.getKey(), defaultDate));
		Assert.assertEquals(defaultDate, DateProperty.get((Collection<Property<?>>) null, null, defaultDate));
		Assert.assertEquals(defaultDate, DateProperty.get(Date.class, (Collection<Property<?>>) null, null, defaultDate));
		Assert.assertEquals(defaultDate, DateProperty.get(propertiesMap, null, defaultDate));
		Assert.assertEquals(defaultDate, DateProperty.get(propertiesMap, notExistKey, defaultDate));
		Assert.assertEquals(defaultDate, DateProperty.get((Map<String, Property<?>>) null, dateProperty.getKey(), defaultDate));
		Assert.assertEquals(defaultDate, DateProperty.get((Map<String, Property<?>>) null, null, defaultDate));
		Assert.assertEquals(defaultDate, DateProperty.get(Date.class, (Map<String, Property<?>>) null, null, defaultDate));
	}

	/**
	 * {@link Properties} test.
	 */
	@Test
	public void propertiesEnumerationTest() {
		Assert.assertEquals(StringProperty.class, Properties.STRING.getPropertyClass());
		Assert.assertEquals(String.class, Properties.STRING.getPropertyValueClass());

		Assert.assertEquals(BooleanProperty.class, Properties.BOOLEAN.getPropertyClass());
		Assert.assertEquals(Boolean.class, Properties.BOOLEAN.getPropertyValueClass());

		Assert.assertEquals(ShortProperty.class, Properties.SHORT.getPropertyClass());
		Assert.assertEquals(Short.class, Properties.SHORT.getPropertyValueClass());

		Assert.assertEquals(IntegerProperty.class, Properties.INTEGER.getPropertyClass());
		Assert.assertEquals(Integer.class, Properties.INTEGER.getPropertyValueClass());

		Assert.assertEquals(LongProperty.class, Properties.LONG.getPropertyClass());
		Assert.assertEquals(Long.class, Properties.LONG.getPropertyValueClass());

		Assert.assertEquals(FloatProperty.class, Properties.FLOAT.getPropertyClass());
		Assert.assertEquals(Float.class, Properties.FLOAT.getPropertyValueClass());

		Assert.assertEquals(DoubleProperty.class, Properties.DOUBLE.getPropertyClass());
		Assert.assertEquals(Double.class, Properties.DOUBLE.getPropertyValueClass());

		Assert.assertEquals(DateProperty.class, Properties.DATE.getPropertyClass());
		Assert.assertEquals(Date.class, Properties.DATE.getPropertyValueClass());
	}

	/**
	 * Test for other cases.
	 */
	@Test
	public void otherCasesTest() {
		final StringProperty stringProp = new StringProperty();
		Assert.assertEquals(null, stringProp.getKey());
		Assert.assertEquals(null, stringProp.getValue());

		stringProp.setKey(stringProperty.getKey());
		stringProp.setValue(stringProperty.getValue());
		Assert.assertEquals(stringProperty.getKey(), stringProp.getKey());
		Assert.assertEquals(stringProperty.getValue(), stringProp.getValue());

		// equals + hashCode
		Assert.assertTrue(stringProp.equals(stringProp));
		Assert.assertFalse(stringProp.equals(null));
		Assert.assertFalse(stringProp.equals(new Object()));
		Assert.assertTrue(new StringProperty().equals(new StringProperty()));
		Assert.assertFalse(new StringProperty().equals(new StringProperty(notExistKey, defaultString)));
		Assert.assertFalse(new StringProperty(notExistKey + notExistKey, defaultString).equals(new StringProperty(notExistKey, defaultString)));

		Assert.assertTrue(stringProperty.equals(stringProp));
		Assert.assertFalse(stringProperty.equals(new StringProperty()));

		Assert.assertEquals(stringProperty.hashCode(), stringProp.hashCode());
		Assert.assertNotEquals(stringProperty.hashCode(), new StringProperty().hashCode());

		// toString
		Assert.assertNotNull(stringProp.toString());
		Assert.assertTrue(new StringProperty().toString().startsWith(StringProperty.class.getSimpleName()));
		Assert.assertTrue(new BooleanProperty().toString().startsWith(BooleanProperty.class.getSimpleName()));

		// other rare cases
		Assert.assertEquals(defaultString, StringProperty.get(new StringProperty(), defaultString));

		final List<Property<?>> propsList = new ArrayList<>();
		propsList.add(null);
		propsList.add(new StringProperty(notExistKey, null));
		propsList.add(new StringProperty(stringProperty.getKey(), null));
		Assert.assertEquals(defaultString, StringProperty.get(propsList, stringProperty.getKey(), defaultString));
		Assert.assertEquals(defaultLong, LongProperty.get(propsList, stringProperty.getKey(), defaultLong));

		propsList.clear();
		propsList.add(new LongProperty(stringProperty.getKey(), null));
		Assert.assertEquals(defaultString, StringProperty.get(propsList, stringProperty.getKey(), defaultString));
		Assert.assertEquals(defaultLong, LongProperty.get(propsList, stringProperty.getKey(), defaultLong));

		final Map<String, Property<?>> propsMap = new HashMap<>();
		propsMap.put(stringProperty.getKey(), new StringProperty(stringProperty.getKey(), null));
		Assert.assertEquals(defaultString, StringProperty.get(propsMap, stringProperty.getKey(), defaultString));
		Assert.assertEquals(defaultLong, LongProperty.get(propsMap, stringProperty.getKey(), defaultLong));

		propsMap.clear();
		propsMap.put(stringProperty.getKey(), new LongProperty(stringProperty.getKey(), null));
		Assert.assertEquals(defaultString, StringProperty.get(propsMap, stringProperty.getKey(), defaultString));
		Assert.assertEquals(defaultLong, LongProperty.get(propsMap, stringProperty.getKey(), defaultLong));

		// additional cases for code coverage
		Assert.assertNotEquals(stringProperty, new StringProperty());
		Assert.assertNotEquals(booleanProperty, new BooleanProperty());
		Assert.assertNotEquals(shortProperty, new ShortProperty());
		Assert.assertNotEquals(integerProperty, new IntegerProperty());
		Assert.assertNotEquals(longProperty, new LongProperty());
		Assert.assertNotEquals(floatProperty, new FloatProperty());
		Assert.assertNotEquals(doubleProperty, new DoubleProperty());
		Assert.assertNotEquals(dateProperty, new DateProperty());
	}

	/**
	 * Test for property types resolving.
	 */
	@Test
	public void propertyResolveTypeTest() {
		Assert.assertNull(Properties.resolveByName(EMPTY));
		Assert.assertNull(Properties.resolveByName(null));
		Assert.assertNull(Properties.resolveByName("notExistType"));

		Assert.assertNotNull(Properties.resolveByName(Properties.INTEGER.name()));
		Assert.assertNotNull(Properties.resolveByName("sHoRt"));
		Assert.assertEquals(Properties.BOOLEAN, Properties.resolveByName(Properties.BOOLEAN.name()));
		Assert.assertEquals(Properties.DATE, Properties.resolveByName("dAtE"));

		Assert.assertNull(Properties.resolveByPropertyClass(null));
		Assert.assertNull(Properties.resolveByPropertyClass(WrongProperty.class));
		Assert.assertNotNull(Properties.resolveByPropertyClass(IntegerProperty.class));
	}

	/**
	 * Test for error cases.
	 */
	@Test
	public void errorCasesTest() {
		try {
			new WrongProperty();
			Assert.fail();
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("aType argument is null", e.getMessage());
		}
	}

	/**
	 * Property implementation for testing error cases.
	 * 
	 * @author Alexandr Bolbat
	 */
	private static class WrongProperty extends AbstractProperty<String> {

		/**
		 * Generated SerialVersionUID.
		 */
		private static final long serialVersionUID = -1196412854075195196L;

		/**
		 * Default constructor.
		 */
		protected WrongProperty() {
			super(null, null, null);
		}

	}

}
