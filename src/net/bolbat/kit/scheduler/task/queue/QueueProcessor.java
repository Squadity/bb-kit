package net.bolbat.kit.scheduler.task.queue;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import net.bolbat.kit.scheduler.task.ProcessingException;

/**
 * Custom processor interface. QueueProcessor invoking for each queued element.
 *
 * @param <T>
 * 		type of processing elements
 * @author ivanbatura
 */
@JsonTypeInfo (use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface QueueProcessor<T> extends Serializable {

	/**
	 * Process queued element.
	 *
	 * @param element
	 * 		queued element
	 * @throws ProcessingException
	 */
	void process(T element) throws ProcessingException;

}
