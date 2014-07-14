package net.bolbat.kit.event.guava;

import static net.bolbat.utils.lang.StringUtils.isNotEmpty;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import net.bolbat.kit.lucene.LuceneStoreManager;

import com.google.common.eventbus.EventBus;

/**
 * {@link EventBus} manager.
 * 
 * @author Alexandr Bolbat
 */
public final class EventBusManager {

	/**
	 * Default {@link EventBus} name.
	 */
	public static final String DEFAULT_NAME = UUID.randomUUID().toString();

	/**
	 * Internal registry storage.
	 */
	private static final Map<String, EventBus> STORAGE = new ConcurrentHashMap<String, EventBus>();

	/**
	 * Synchronization lock.
	 */
	private static final Object LOCK = new Object();

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private EventBusManager() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Get default {@link EventBus}.
	 * 
	 * @return {@link EventBus}
	 */
	public static EventBus getEventBus() {
		return getEventBus(DEFAULT_NAME);
	}

	/**
	 * Get {@link EventBus} instance for given name.
	 * 
	 * @param name
	 *            name
	 * @return {@link EventBus}
	 */
	public static EventBus getEventBus(final String name) {
		final String busName = isNotEmpty(name) ? name : DEFAULT_NAME;
		EventBus bus = STORAGE.get(busName);
		if (bus != null)
			return bus;

		synchronized (LOCK) {
			bus = STORAGE.get(busName); // double check
			if (bus != null)
				return bus;

			bus = new EventBus(busName);
			STORAGE.put(busName, bus);
			return bus;
		}
	}

	/**
	 * Tear down {@link LuceneStoreManager} state.
	 */
	public static void tearDown() {
		STORAGE.clear();
	}

}
