package net.bolbat.kit.scheduler.task;

/**
 * Custom processor interface. Processor invoking for each queued element.
 * 
 * @author ivanbatura
 */
public interface Processor {

	/**
	 * Process queued element.
	 * 
	 * @param element
	 *            - queued element
	 * @throws ProcessingException
	 */
	void process(Object element) throws ProcessingException;

}
