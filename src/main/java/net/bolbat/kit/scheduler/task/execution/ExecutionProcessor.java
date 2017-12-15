package net.bolbat.kit.scheduler.task.execution;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import net.bolbat.kit.scheduler.task.ProcessingException;

/**
 * Execution processor. Execute process method on its start.
 *
 * @author ivanbatura
 */
@JsonTypeInfo (use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface ExecutionProcessor extends Serializable {

	/**
	 * Process logic.
	 *
	 * @throws ProcessingException
	 */
	void process() throws ProcessingException;

}
