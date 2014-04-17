package net.bolbat.kit.scheduler.task.queue;

import java.io.Serializable;

import net.bolbat.kit.scheduler.task.ProcessingException;

/**
 * Custom processor interface. QueueProcessor invoking for each queued element.
 * 
 * @author ivanbatura
 */
public interface QueueProcessor extends Serializable {

	/**
	 * Process queued element.
	 * 
	 * @param element
	 *            - queued element
	 * @throws net.bolbat.kit.scheduler.task.ProcessingException
	 */
	void process(Object element) throws ProcessingException;

}
