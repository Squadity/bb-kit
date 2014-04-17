package net.bolbat.kit.scheduler.task.execution;

import java.io.Serializable;

import net.bolbat.kit.scheduler.task.ProcessingException;

/**
 * Custom processor interface. QueueProcessor invoking for element.
 * 
 * @author ivanbatura
 */
public interface ExecutionProcessor extends Serializable {

	/**
	 * Process queued element.
	 * 
	 * @param element
	 *            - queued element
	 * @throws net.bolbat.kit.scheduler.task.ProcessingException
	 */
	void process(Object element) throws ProcessingException;

}
