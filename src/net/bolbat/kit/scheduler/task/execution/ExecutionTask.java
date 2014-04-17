package net.bolbat.kit.scheduler.task.execution;

import net.bolbat.kit.scheduler.Task;
import net.bolbat.kit.scheduler.task.ProcessingException;
import net.bolbat.kit.scheduler.task.queue.QueueProcessor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Quartz job implementation for {@link net.bolbat.kit.scheduler.task.queue.QueueLoader} execution.
 *
 * @author ivanbatura
 */
public final class ExecutionTask implements Task {

	/**
	 * {@link org.slf4j.Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionTask.class);

	@Override
	public void execute(final JobExecutionContext context) throws JobExecutionException {
		Object processorObj = context.getJobDetail().getJobDataMap().get(ExecutionConstants.PROCESSOR);
		if (!(processorObj instanceof QueueProcessor)) {
			LOGGER.error("execute(context) fail. No configured QueueProcessor.");
			return;
		}
		QueueProcessor processor = QueueProcessor.class.cast(processorObj);

		debug("executing " + ExecutionTask.class);
		try {
			processor.process(null);
		} catch (ProcessingException e) {
			LOGGER.error("execute(context) processing fail.", e);
			//CHECKSTYLE:OFF
		} catch (Exception e) {
			//CHECKSTYLE:ON
			LOGGER.error("execute(context) processing fail.", e);
		}
	}

	/**
	 * Writing debug message to log if debug is enabled.
	 *
	 * @param message
	 * 		- message to write
	 */

	private static void debug(final String message) {
		if (LOGGER.isDebugEnabled())
			LOGGER.debug(message);
	}

}
