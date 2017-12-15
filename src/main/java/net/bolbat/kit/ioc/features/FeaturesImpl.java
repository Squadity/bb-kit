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
	private final Object lock = new Object();

	/**
	 * Configuration storage.
	 */
	private transient Map<Feature, Boolean> features;

	/**
	 * Public constructor.
	 */
	public FeaturesImpl() {
		this.features = new EnumMap<>(Feature.class);

		configureToDefaults();
	}

	@Override
	public void enable(final Feature... toEnable) {
		if (toEnable == null || toEnable.length == 0)
			return;

		synchronized (lock) {
			final Map<Feature, Boolean> newConfiguration = new EnumMap<>(features);
			for (final Feature feature : toEnable)
				if (feature != null)
					newConfiguration.put(feature, Boolean.TRUE);

			features = newConfiguration;
		}
	}

	@Override
	public void disable(final Feature... toDisable) {
		if (toDisable == null || toDisable.length == 0)
			return;

		synchronized (lock) {
			final Map<Feature, Boolean> newConfiguration = new EnumMap<>(features);
			for (final Feature feature : toDisable)
				if (feature != null)
					newConfiguration.put(feature, Boolean.FALSE);

			features = newConfiguration;
		}
	}

	@Override
	public boolean isEnabled(final Feature feature) {
		return Boolean.TRUE.equals(features.get(feature));
	}

	@Override
	public void clear() {
		features = new EnumMap<>(Feature.class);

		configureToDefaults();
	}

	/**
	 * Configure features to defaults.
	 */
	private void configureToDefaults() {
		for (final Feature feature : Feature.values()) {
			if (feature.isEnabledByDefault()) {
				enable(feature);
			} else {
				disable(feature);
			}
		}
	}

}
