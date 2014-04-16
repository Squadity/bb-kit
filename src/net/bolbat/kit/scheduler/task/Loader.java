package net.bolbat.kit.scheduler.task;

import java.util.List;

/**
 * Custom loader interface. Scheduler invoke loader by it's schedule and get required elements from it.
 * 
 * @author ivanbatura
 */
public interface Loader {

	/**
	 * Load elements to the queue.
	 * 
	 * @return {@link java.util.List} of {@link Object}
	 * @throws LoadingException
	 */
	List<Object> load() throws LoadingException;

}
