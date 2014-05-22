package net.bolbat.kit.scheduler.task.queue;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import net.bolbat.kit.scheduler.task.LoadingException;

/**
 * Custom loader interface. Scheduler invoke loader by it's schedule and get required elements from it.
 *
 * @param <T>
 * 		type of loading elements
 * @author ivanbatura
 */
@JsonTypeInfo (use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface QueueLoader<T> extends Serializable {

	/**
	 * Load elements to the queue.
	 *
	 * @return {@link List} of {@link T}
	 * @throws LoadingException
	 */
	List<T> load() throws LoadingException;

}
