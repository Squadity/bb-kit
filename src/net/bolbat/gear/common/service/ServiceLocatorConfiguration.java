package net.bolbat.gear.common.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Generic configuration store implementation for {@link ServiceLocator}.
 * 
 * @author Alexandr Bolbat
 */
public final class ServiceLocatorConfiguration implements Serializable {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -5686326471213987349L;

	/**
	 * Configuration parameters store.
	 */
	private final Map<String, Serializable> parameters;

	/**
	 * Default constructor.
	 */
	public ServiceLocatorConfiguration() {
		this.parameters = new HashMap<String, Serializable>();
	}

	/**
	 * Add parameter.
	 * 
	 * @param key
	 *            parameter key
	 * @param value
	 *            parameter value
	 */
	public void addParameter(final String key, final Serializable value) {
		parameters.put(key, value);
	}

	/**
	 * Remove parameter.
	 * 
	 * @param key
	 *            parameter key
	 */
	public void removeParameter(final String key) {
		parameters.remove(key);
	}

	/**
	 * Get parameters key's.
	 * 
	 * @return {@link Set}
	 */
	public Set<String> getParametersKeys() {
		return new HashSet<String>(parameters.keySet());
	}

	/**
	 * Get parameter value.
	 * 
	 * @param key
	 *            parameter key
	 * @return {@link Serializable}
	 */
	public Serializable getParameterValue(final String key) {
		return parameters.get(key);
	}

	/**
	 * Reset configuration.
	 */
	public void reset() {
		parameters.clear();
	}

}
