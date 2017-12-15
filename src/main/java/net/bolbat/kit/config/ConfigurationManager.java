package net.bolbat.kit.config;

import static net.bolbat.utils.lang.StringUtils.isNotEmpty;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.configureme.Environment;
import org.configureme.annotations.AfterConfiguration;
import org.configureme.annotations.AfterInitialConfiguration;
import org.configureme.environments.DynamicEnvironment;
import org.configureme.sources.ConfigurationSourceKey.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.bolbat.utils.reflect.ClassUtils;

/**
 * Configuration manager.
 * 
 * @author Alexandr Bolbat
 */
public final class ConfigurationManager {

	/**
	 * {@link Logger} instance.
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationManager.class);

	/**
	 * Configurations storage.
	 */
	private static final Map<Key<?>, Object> STORAGE = new ConcurrentHashMap<>();

	/**
	 * Instance creation lock.
	 */

	private static final Object LOCK = new Object();

	/**
	 * Private constructor for preventing class instantiation.
	 */
	private ConfigurationManager() {
		throw new IllegalAccessError("Can't instantiate.");
	}

	/**
	 * Get configuration.
	 *
	 * @param type
	 *            type, required
	 * @return configuration instance
	 */
	public static <T> T getInstance(final Class<T> type) {
		return getInstance(type, null, null);
	}

	/**
	 * Get configuration configured with given configuration name.
	 *
	 * @param type
	 *            type, required
	 * @param name
	 *            configuration name, optional
	 * @return configuration instance
	 */
	public static <T> T getInstanceForConf(final Class<T> type, final String name) {
		return getInstance(type, name, null);
	}

	/**
	 * Get configuration configured for given environment.
	 *
	 * @param type
	 *            type, required
	 * @param environment
	 *            environment, optional
	 * @return configuration instance
	 */
	public static <T> T getInstanceForEnv(final Class<T> type, final String environment) {
		return getInstance(type, null, environment);
	}

	/**
	 * Get configuration configured with given configuration name and for given environment.
	 *
	 * @param type
	 *            type, required
	 * @param name
	 *            configuration name, optional
	 * @param environment
	 *            environment, optional
	 * @return configuration instance
	 */
	public static <T> T getInstance(final Class<T> type, final String name, final String environment) {
		if (type == null)
			throw new IllegalArgumentException("type argument is null");

		final Key<T> key = Key.create(type, name, environment);
		Object instance = STORAGE.get(key);
		if (instance != null)
			return type.cast(instance);

		synchronized (LOCK) {
			instance = STORAGE.get(key);
			if (instance != null)
				return type.cast(instance);

			try {
				final org.configureme.ConfigurationManager cm = org.configureme.ConfigurationManager.INSTANCE;
				final Environment env = environment != null ? DynamicEnvironment.parse(environment) : cm.getDefaultEnvironment();
				instance = type.newInstance();

				if (isNotEmpty(name))
					cm.configureAs(instance, env, name, Format.JSON);
				else
					cm.configure(instance, env, Format.JSON);

				// CHECKSTYLE:OFF
			} catch (final InstantiationException e) {
				throw new RuntimeException(e);
			} catch (final IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (final ConfigurationRuntimeException e) {
				throw e; // throwing up, this exception can be used as runtime exception in configuration constructor or "after configuration" methods
			} catch (final RuntimeException e) {
				// CHECKSTYLE:ON
				relayOnDefaults(instance, e.getMessage());
			}

			STORAGE.put(key, instance);

			if (LOGGER.isDebugEnabled())
				LOGGER.debug("Configured with[" + instance + "]");
		}

		return type.cast(instance);
	}

	/**
	 * Relaying on configuration defaults.
	 * 
	 * @param instance
	 *            configuration instance
	 * @param cause
	 *            relaying cause
	 */
	private static void relayOnDefaults(final Object instance, final String cause) {
		LOGGER.warn("Configuration fail[" + cause + "]. Relaying on defaults.");

		ClassUtils.execute(instance, AfterConfiguration.class, AfterInitialConfiguration.class);
	}

	/**
	 * Tear down {@link ConfigurationManager} state.
	 */
	public static void tearDown() {
		STORAGE.clear();
	}

	/**
	 * Configuration key.
	 * 
	 * @author Alexandr Bolbat
	 * 
	 * @param <T>
	 *            configuration type
	 */
	private static class Key<T> implements Serializable {

		/**
		 * Generated SerialVersionUID.
		 */
		private static final long serialVersionUID = -1030330103453083666L;

		/**
		 * Delimiter.
		 */
		public static final String DELIMITER = "|";

		/**
		 * Default name.
		 */
		public static final String DEFAULT_NAME = "^";

		/**
		 * Current runtime environment.
		 */
		public static final String CURRENT_ENV = DEFAULT_NAME;

		/**
		 * Type.
		 */
		private final Class<T> type;

		/**
		 * Configuration name.
		 */
		private final String name;

		/**
		 * Environment.
		 */
		private final String environment;

		/**
		 * Private constructor.
		 * 
		 * @param aType
		 *            type
		 * @param aName
		 *            configuration name
		 * @param aEnvironment
		 *            environment
		 */
		private Key(final Class<T> aType, final String aName, final String aEnvironment) {
			if (aType == null)
				throw new IllegalArgumentException("aType argument is null");

			this.type = aType;
			this.name = isNotEmpty(aName) ? aName : DEFAULT_NAME;
			this.environment = isNotEmpty(aEnvironment) ? aEnvironment : CURRENT_ENV;
		}

		/**
		 * Create configuration {@link Key}.
		 * 
		 * @param aType
		 *            type
		 * @param aName
		 *            configuration name
		 * @param aEnvironment
		 *            environment
		 * @return {@link Key}
		 */
		public static <T> Key<T> create(final Class<T> aType, final String aName, final String aEnvironment) {
			return new Key<>(aType, aName, aEnvironment);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + type.getName().hashCode();
			result = prime * result + name.hashCode();
			result = prime * result + environment.hashCode();
			return result;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof Key))
				return false;
			final Key<?> other = (Key<?>) obj;
			return type.getName().equals(other.type.getName()) && name.equals(other.name) && environment.equals(other.environment);
		}

		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
			builder.append("[");
			builder.append(type.getName());
			builder.append(DELIMITER);
			builder.append(name);
			builder.append(DELIMITER);
			builder.append(environment);
			builder.append("]");
			return builder.toString();
		}

	}

}
