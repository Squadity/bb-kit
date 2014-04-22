package net.bolbat.kit.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import net.bolbat.kit.scheduler.task.LoadingException;
import net.bolbat.kit.scheduler.task.queue.QueueLoader;

/**
 * {@link net.bolbat.kit.scheduler.task.queue.QueueLoader} testing implementation.
 *
 * @author ivanbatura
 */
public class RandomGenerationLoader implements QueueLoader<String> {
	/**
	 * Test serial UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Maximum elements to load.
	 */
	private static final int MAX_TO_LOAD = 100;

	/**
	 * Loaded elements amount.
	 */
	private final AtomicInteger loaded = new AtomicInteger(0);

	/**
	 * Synchronization lock.
	 */
	private final Object LOCK = new Object();

	@Override
	public List<String> load() throws LoadingException {
		List<String> result = new ArrayList<String>();

		if (loaded.get() < MAX_TO_LOAD) {
			synchronized (LOCK) {
				if (loaded.get() < MAX_TO_LOAD) {
					result.add(System.currentTimeMillis() + "_obj_" + loaded);
					loaded.incrementAndGet();
				}
			}
		}

		return result;
	}

	public int getLoaded() {
		return loaded.get();
	}

}
