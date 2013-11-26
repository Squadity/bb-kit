package net.bolbat.kit.config;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.configureme.annotations.DontConfigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link Configuration} abstract functionality.
 * 
 * @author Alexandr Bolbat
 */
public abstract class AbstractConfiguration implements Serializable {

	/**
	 * Generated SerialVersionUID.
	 */
	@DontConfigure
	private static final long serialVersionUID = -8251774226206047496L;

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractConfiguration.class);

	/**
	 * Listeners.
	 */
	@DontConfigure
	private final List<ConfigurationListener> listeners = new CopyOnWriteArrayList<ConfigurationListener>();

	/**
	 * Register listener.
	 * 
	 * @param listener
	 *            {@link ConfigurationListener} instance
	 */
	public void registerListener(final ConfigurationListener listener) {
		if (listener != null)
			listeners.add(listener);
	}

	/**
	 * Unregister listener.
	 * 
	 * @param listener
	 *            {@link ConfigurationListener} instance
	 */
	public void unregisterListener(final ConfigurationListener listener) {
		if (listener != null)
			listeners.remove(listener);
	}

	/**
	 * Fire 'configurationChanged' event for registered listeners.
	 */
	protected void fireConfigurationChanged() {
		if (listeners.isEmpty())
			return;

		for (final ConfigurationListener listener : listeners) {
			if (listener == null)
				continue;

			try {
				listener.configurationChanged();
				// CHECKSTYLE:OFF
			} catch (final RuntimeException e) {
				// CHECKSTYLE:ON
				final String cName = this.getClass().getName();
				final String lName = listener.getClass().getName();
				LOGGER.warn("Configuration[" + cName + "] 'configurationChanged' event for listener[" + lName + "] fail.", e);
			}
		}
	}

}
