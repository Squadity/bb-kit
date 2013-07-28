package net.bolbat.kit.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * {@link ServiceFactory} configuration.
 * 
 * @author Alexandr Bolbat
 */
public final class Configuration implements Serializable {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -5686326471213987349L;

	/**
	 * Configuration parameters store.
	 */
	private final Map<String, Serializable> parameters = new HashMap<String, Serializable>();

	/**
	 * Default constructor.
	 */
	private Configuration() {
	}

	/**
	 * Create new configuration.
	 * 
	 * @return {@link Configuration} instance
	 */
	public static Configuration create() {
		return new Configuration();
	}

	/**
	 * Set parameter.
	 * 
	 * @param key
	 *            parameter key
	 * @param value
	 *            parameter value
	 */
	public void set(final String key, final Serializable value) {
		parameters.put(key, value);
	}

	/**
	 * Remove parameter.
	 * 
	 * @param key
	 *            parameter key
	 */
	public void remove(final String key) {
		parameters.remove(key);
	}

	/**
	 * Get parameters key's.
	 * 
	 * @return {@link Set}
	 */
	public Set<String> getKeys() {
		return new HashSet<String>(parameters.keySet());
	}

	/**
	 * Get parameter value.
	 * 
	 * @param key
	 *            parameter key
	 * @return {@link Serializable}
	 */
	public Serializable get(final String key) {
		return parameters.get(key);
	}

	/**
	 * Get parameter value as {@link String}.
	 * 
	 * @param key
	 *            parameter key
	 * @return {@link String} or <code>null</code>
	 */
	public String getString(final String key) {
		Serializable result = get(key);
		return result != null ? String.valueOf(result) : null;
	}

	/**
	 * Reset configuration.
	 */
	public void reset() {
		parameters.clear();
	}

}
