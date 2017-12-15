package net.bolbat.kit.orchestrator.exception;

import net.bolbat.kit.orchestrator.OrchestrationConstants;
import net.bolbat.kit.orchestrator.impl.ExecutionInfo;

/**
 * Exception for case when concurrent executions limit is reached.
 * 
 * @author Alexandr Bolbat
 */
public class ConcurrentOverflowException extends OrchestrationException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 7520443670356460741L;

	/**
	 * Error message.
	 */
	private static final String MESSAGE = "concurrent executions limit is reached";

	/**
	 * Public constructor.
	 * 
	 * @param info
	 *            {@link ExecutionInfo}
	 */
	public ConcurrentOverflowException(final ExecutionInfo info) {
		super(String.format(OrchestrationConstants.ERR_MSG_TEMPLATE, info.getId(), info.getName(), info.getActualLimitsConfig(), info.getActualExecutorConfig(),
				MESSAGE));
	}

}
