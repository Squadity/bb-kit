package net.bolbat.kit.scheduler.task.queue;

import java.io.Serializable;
import java.util.List;

import net.bolbat.kit.scheduler.task.LoadingException;

/**
 * Custom loader interface. Scheduler invoke loader by it's schedule and get required elements from it.
 *
 * @param <T>
 * 		type of loading elements
 * @author ivanbatura
 */
public interface QueueLoader<T> extends Serializable {

	/**
	 * Load elements to the queue.
	 *
	 * @return {@link List} of {@link T}
	 * @throws LoadingException
	 */
	List<T> load() throws LoadingException;

}
