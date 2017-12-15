package net.bolbat.kit.ioc.features;

import net.bolbat.kit.ioc.Manager.Feature;

/**
 * Features support.
 * 
 * @author Alexandr Bolbat
 */
public interface Features {

	/**
	 * Enable features.
	 * 
	 * @param toEnable
	 *            {@link Feature} array
	 */
	void enable(Feature... toEnable);

	/**
	 * Disable features.
	 * 
	 * @param toDisable
	 *            {@link Feature} array
	 */
	void disable(Feature... toDisable);

	/**
	 * Is feature enabled.
	 * 
	 * @param feature
	 *            {@link Feature}
	 * @return <code>true</code> if enabled or <code>false</code>
	 */
	boolean isEnabled(Feature feature);

	/**
	 * Clear features configuration and configure to defaults.
	 */
	void clear();

}
