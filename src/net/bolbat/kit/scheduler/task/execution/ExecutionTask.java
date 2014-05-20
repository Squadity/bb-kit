package net.bolbat.kit.scheduler.task.execution;

import net.bolbat.kit.scheduler.Task;
import net.bolbat.kit.scheduler.TaskParameters;
import net.bolbat.kit.scheduler.task.ConfigurableTask;
import net.bolbat.kit.scheduler.task.ProcessingException;
import net.bolbat.kit.scheduler.task.queue.QueueProcessor;
import net.bolbat.utils.logging.LoggingUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Task implementation of the {@link Task} to execute processor once.
 *
 * @author ivanbatura
 */
public final class ExecutionTask implements Task {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionTask.class);

	@Override
	public void execute(final JobExecutionContext context) throws JobExecutionException {
		final Object processorObj = context.getJobDetail().getJobDataMap().get(ExecutionConstants.PROCESSOR);

		// Configure parameter
		if (processorObj instanceof ConfigurableTask) {
			final Object parametersObj = context.getJobDetail().getJobDataMap().get(ExecutionConstants.PARAMETERS);
			if (!(parametersObj instanceof TaskParameters))
				LOGGER.error("execute(context) fail. No configured Parameters.");
			else
				ConfigurableTask.class.cast(processorObj).configure(TaskParameters.class.cast(parametersObj));

		}

		if (!(processorObj instanceof QueueProcessor)) {
			LOGGER.error("execute(context) fail. No configured Processor.");
			return;
		}
		final ExecutionProcessor processor = ExecutionProcessor.class.cast(processorObj);

		LoggingUtils.debug(LOGGER, "executing " + ExecutionTask.class);
		try {
			processor.process();
		} catch (ProcessingException e) {
			LOGGER.error("execute(context) processing fail.", e);
			//CHECKSTYLE:OFF
		} catch (Exception e) {
			//CHECKSTYLE:ON
			LOGGER.error("execute(context) processing fail.", e);
		}
	}
}
