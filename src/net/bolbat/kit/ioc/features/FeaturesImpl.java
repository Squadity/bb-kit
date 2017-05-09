package net.bolbat.kit.ioc.features;

import java.util.EnumMap;
import java.util.Map;

import net.bolbat.kit.ioc.Manager.Feature;

/**
 * {@link Features} implementation.
 * 
 * @author Alexandr Bolbat
 */
public class FeaturesImpl implements Features {

	/**
	 * Synchronization lock.
	 */
	private final Object LOCK = new Object();

	/**
	 * Configuration storage.
	 */
	private transient Map<Feature, Boolean> features = new EnumMap<>(Feature.class);

	/**
	 * Package protected constructor.
	 */
	public FeaturesImpl() {
		enable(Feature.AUTO_IMPL_DISCOVERY);
	}

	/**
	 * Enable features.
	 * 
	 * @param toEnable
	 *            {@link Feature} array
	 */
	@Override
	public void enable(final Feature... toEnable) {
		if (toEnable == null || toEnable.length == 0)
			return;

		synchronized (LOCK) {
			final Map<Feature, Boolean> newConfiguration = new EnumMap<>(features);
			for (final Feature feature : toEnable)
				if (feature != null)
					newConfiguration.put(feature, Boolean.TRUE);

			features = newConfiguration;
		}
	}

	/**
	 * Disable features.
	 * 
	 * @param toDisable
	 *            {@link Feature} array
	 */
	@Override
	public void disable(final Feature... toDisable) {
		if (toDisable == null || toDisable.length == 0)
			return;

		synchronized (LOCK) {
			final Map<Feature, Boolean> newConfiguration = new EnumMap<>(features);
			for (final Feature feature : toDisable)
				if (feature != null)
					newConfiguration.put(feature, Boolean.FALSE);

			features = newConfiguration;
		}
	}

	/**
	 * Is feature enabled.
	 * 
	 * @param feature
	 *            {@link Feature}
	 * @return <code>true</code> if enabled or <code>false</code>
	 */
	@Override
	public boolean isEnabled(final Feature feature) {
		return Boolean.TRUE == features.get(feature);
	}

}
