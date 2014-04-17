package net.bolbat.kit.scheduler.task.queue;

import java.io.Serializable;
import java.util.List;

import net.bolbat.kit.scheduler.task.LoadingException;

/**
 * Custom loader interface. Scheduler invoke loader by it's schedule and get required elements from it.
 * 
 * @author ivanbatura
 */
public interface QueueLoader extends Serializable{

	/**
	 * Load elements to the queue.
	 * 
	 * @return {@link java.util.List} of {@link Object}
	 * @throws net.bolbat.kit.scheduler.task.LoadingException
	 */
	List<Object> load() throws LoadingException;

}
