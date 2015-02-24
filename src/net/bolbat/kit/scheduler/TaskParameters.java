package net.bolbat.kit.scheduler;

import java.util.HashMap;

/**
 * Task parameters for json mapper.
 *
 * @author ivanbatura
 */
public class TaskParameters extends HashMap<String, TaskParameterValue> {
	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = 6103416221595460904L;

	/**
	 * Get string value.
	 *
	 * @param key
	 * 		key
	 * @return {@link String} value
	 */
	public String getString(final String key) {
		final TaskParameterValue result = super.get(key);
		if (result instanceof StringParameterValue)
			return ((StringParameterValue) result).getValue();
		return null;
	}

	/**
	 * Get integer value.
	 *
	 * @param key
	 * 		key
	 * @return {@link Integer} value
	 */
	public Integer getInteger(final String key) {
		final TaskParameterValue result = super.get(key);
		if (result instanceof IntegerParameterValue)
			return ((IntegerParameterValue) result).getValue();
		return null;
	}

	/**
	 * Get long value.
	 *
	 * @param key
	 * 		key
	 * @return {@link Long} value
	 */
	public Long getLong(final String key) {
		final TaskParameterValue result = super.get(key);
		if (result instanceof LongParameterValue)
			return ((LongParameterValue) result).getValue();
		return null;
	}

	/**
	 * Get float value.
	 *
	 * @param key
	 * 		key
	 * @return {@link Float} value
	 */
	public Float getFloat(final String key) {
		final TaskParameterValue result = super.get(key);
		if (result instanceof FloatParameterValue)
			return ((FloatParameterValue) result).getValue();
		return null;
	}

	/**
	 * Get double value.
	 *
	 * @param key
	 * 		key
	 * @return {@link Double} value
	 */
	public Double getDouble(final String key) {
		final TaskParameterValue result = super.get(key);
		if (result instanceof DoubleParameterValue)
			return ((DoubleParameterValue) result).getValue();
		return null;
	}

	/**
	 * Get boolean value.
	 *
	 * @param key
	 * 		key
	 * @return {@link Boolean} value
	 */
	public Boolean getBoolean(final String key) {
		final TaskParameterValue result = super.get(key);
		if (result instanceof BooleanParameterValue)
			return ((BooleanParameterValue) result).getValue();
		return null;
	}

	/**
	 * Set string value.
	 *
	 * @param key
	 * 		key
	 * @param value
	 * 		{@link String} value
	 * @return {@link String} value
	 */
	public String putString(final String key, final String value) {
		final StringParameterValue result = (StringParameterValue) super.put(key, new StringParameterValue(value));
		return result != null ? result.getValue() : null;
	}

	/**
	 * Set integer value.
	 *
	 * @param key
	 * 		key
	 * @param value
	 * 		{@link Integer} value
	 * @return {@link Integer} value
	 */
	public Integer putInteger(final String key, final Integer value) {
		final IntegerParameterValue result = (IntegerParameterValue) super.put(key, new IntegerParameterValue(value));
		return result != null ? result.getValue() : null;
	}

	/**
	 * Set long value.
	 *
	 * @param key
	 * 		key
	 * @param value
	 * 		{@link Long} value
	 * @return {@link Long} value
	 */
	public Long putLong(final String key, final Long value) {
		final LongParameterValue result = (LongParameterValue) super.put(key, new LongParameterValue(value));
		return result != null ? result.getValue() : null;
	}

	/**
	 * Set float value.
	 *
	 * @param key
	 * 		key
	 * @param value
	 * 		{@link Float} value
	 * @return {@link Float} value
	 */
	public Float putFloat(final String key, final Float value) {
		final FloatParameterValue result = (FloatParameterValue) super.put(key, new FloatParameterValue(value));
		return result != null ? result.getValue() : null;
	}

	/**
	 * Set double value.
	 *
	 * @param key
	 * 		key
	 * @param value
	 * 		{@link Double} value
	 * @return {@link Double} value
	 */
	public Double putDouble(final String key, final Double value) {
		final DoubleParameterValue result = (DoubleParameterValue) super.put(key, new DoubleParameterValue(value));
		return result != null ? result.getValue() : null;
	}

	/**
	 * Set boolean value.
	 *
	 * @param key
	 * 		key
	 * @param value
	 * 		{@link Boolean} value
	 * @return {@link Boolean} value
	 */
	public Boolean putBoolean(final String key, final Boolean value) {
		final BooleanParameterValue result = (BooleanParameterValue) super.put(key, new BooleanParameterValue(value));
		return result != null ? result.getValue() : null;
	}

	/**
	 * Implementation of the {@link TaskParameterValue} for String value.
	 */
	public static final class StringParameterValue implements TaskParameterValue {
		/**
		 * Generated serial uid.
		 */
		private static final long serialVersionUID = 3965335205805026125L;
		/**
		 * Value.
		 */
		private String value;

		/**
		 * Default constructor.
		 */
		public StringParameterValue() {
		}

		/**
		 * Constructor.
		 *
		 * @param value
		 * 		value
		 */
		private StringParameterValue(final String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	/**
	 * Implementation of the {@link TaskParameterValue} for Integer value.
	 */
	public static final class IntegerParameterValue implements TaskParameterValue {
		/**
		 * Generated serial uid.
		 */
		private static final long serialVersionUID = -2461172968341360443L;

		/**
		 * Value.
		 */
		private Integer value;

		/**
		 * Default constructor.
		 */
		public IntegerParameterValue() {
		}

		/**
		 * Constrictor.
		 *
		 * @param value
		 * 		value
		 */
		private IntegerParameterValue(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}
	}

	/**
	 * Implementation of the {@link TaskParameterValue} for Long value.
	 */
	public static final class LongParameterValue implements TaskParameterValue {
		/**
		 * Generated serial uid.
		 */
		private static final long serialVersionUID = 6218450551848164377L;

		/**
		 * Value.
		 */
		private Long value;

		/**
		 * Default constructor.
		 */
		public LongParameterValue() {
		}

		/**
		 * Constrictor.
		 *
		 * @param value
		 * 		value
		 */
		private LongParameterValue(Long value) {
			this.value = value;
		}

		public Long getValue() {
			return value;
		}

		public void setValue(Long value) {
			this.value = value;
		}
	}

	/**
	 * Implementation of the {@link TaskParameterValue} for Float value.
	 */
	public static final class FloatParameterValue implements TaskParameterValue {
		/**
		 * Generated serial uid.
		 */
		private static final long serialVersionUID = 6218450551848164377L;

		/**
		 * Value.
		 */
		private Float value;

		/**
		 * Default constructor.
		 */
		public FloatParameterValue() {
		}

		/**
		 * Constrictor.
		 *
		 * @param value
		 * 		value
		 */
		private FloatParameterValue(Float value) {
			this.value = value;
		}

		public Float getValue() {
			return value;
		}

		public void setValue(Float value) {
			this.value = value;
		}
	}

	/**
	 * Implementation of the {@link TaskParameterValue} for Double value.
	 */
	public static final class DoubleParameterValue implements TaskParameterValue {
		/**
		 * Generated serial uid.
		 */
		private static final long serialVersionUID = 6218450551848164377L;

		/**
		 * Value.
		 */
		private Double value;

		/**
		 * Default constructor.
		 */
		public DoubleParameterValue() {
		}

		/**
		 * Constrictor.
		 *
		 * @param value
		 * 		value
		 */
		private DoubleParameterValue(Double value) {
			this.value = value;
		}

		public Double getValue() {
			return value;
		}

		public void setValue(Double value) {
			this.value = value;
		}
	}

	/**
	 * Implementation of the {@link TaskParameterValue} for Boolean value.
	 */
	public static final class BooleanParameterValue implements TaskParameterValue {
		/**
		 * Generated serial uid.
		 */
		private static final long serialVersionUID = 6218450551848164377L;

		/**
		 * Value.
		 */
		private  Boolean value;

		/**
		 * Default constructor.
		 */
		public BooleanParameterValue() {
		}

		/**
		 * Constrictor.
		 *
		 * @param value
		 * 		value
		 */
		private BooleanParameterValue(Boolean value) {
			this.value = value;
		}

		public Boolean getValue() {
			return value;
		}

		public void setValue(Boolean value) {
			this.value = value;
		}
	}
}
