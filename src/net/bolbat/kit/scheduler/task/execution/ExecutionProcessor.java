package net.bolbat.kit.scheduler.task.execution;

import java.io.Serializable;

import net.bolbat.kit.scheduler.task.ProcessingException;

/**
 * Execution processor. Execute process method on its start.
 *
 * @author ivanbatura
 */
public interface ExecutionProcessor extends Serializable {

	/**
	 * Process logic.
	 *
	 * @throws ProcessingException
	 */
	void process() throws ProcessingException;

}
