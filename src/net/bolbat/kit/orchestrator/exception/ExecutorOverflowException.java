package net.bolbat.kit.orchestrator.exception;

import java.util.concurrent.ExecutorService;

import net.bolbat.kit.orchestrator.OrchestrationConstants;
import net.bolbat.kit.orchestrator.impl.ExecutionInfo;

/**
 * Exception for case when {@link ExecutorService} is full.
 * 
 * @author Alexandr Bolbat
 */
public class ExecutorOverflowException extends OrchestrationException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -699166388986266206L;

	/**
	 * Error message.
	 */
	private static final String MESSAGE = "executor queue is full";

	/**
	 * Public constructor.
	 * 
	 * @param info
	 *            {@link ExecutionInfo}
	 */
	public ExecutorOverflowException(final ExecutionInfo info) {
		super(String.format(OrchestrationConstants.ERR_MSG_TEMPLATE, info.getId(), info.getName(), info.getConfig().getLimitsConfig(),
				info.getConfig().getExecutorConfig(), MESSAGE));
	}

}
