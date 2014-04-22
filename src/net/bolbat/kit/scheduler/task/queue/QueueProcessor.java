package net.bolbat.kit.scheduler.task.queue;

import java.io.Serializable;

import net.bolbat.kit.scheduler.task.ProcessingException;

/**
 * Custom processor interface. QueueProcessor invoking for each queued element.
 *
 * @param <T>
 * 		type of processing elements
 * @author ivanbatura
 */
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
