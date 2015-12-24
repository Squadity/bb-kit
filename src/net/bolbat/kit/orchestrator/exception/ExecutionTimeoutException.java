package net.bolbat.kit.orchestrator.exception;

import net.bolbat.kit.orchestrator.OrchestrationConstants;
import net.bolbat.kit.orchestrator.impl.ExecutionInfo;

/**
 * Exception for case when execution timeout exceeded.
 * 
 * @author Alexandr Bolbat
 */
public class ExecutionTimeoutException extends OrchestrationException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -6685376994727330874L;

	/**
	 * Error message.
	 */
	private static final String MESSAGE = "timeout is reached";

	/**
	 * Public constructor.
	 * 
	 * @param info
	 *            {@link ExecutionInfo}
	 */
	public ExecutionTimeoutException(final ExecutionInfo info) {
		super(String.format(OrchestrationConstants.ERR_MSG_TEMPLATE, info.getId(), info.getName(), info.getActualLimitsConfig(), info.getActualExecutorConfig(),
				MESSAGE));
	}

}
