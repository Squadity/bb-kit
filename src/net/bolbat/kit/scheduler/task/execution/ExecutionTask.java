package net.bolbat.kit.scheduler.task.execution;

import net.bolbat.kit.scheduler.SchedulerConstants;
import net.bolbat.kit.scheduler.Task;
import net.bolbat.kit.scheduler.task.ConfigurableTask;
import net.bolbat.kit.scheduler.task.ProcessingException;
import net.bolbat.utils.logging.LoggingUtils;
import net.bolbat.utils.reflect.Instantiator;
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
		final Object taskConfiguration = context.getJobDetail().getJobDataMap().get(SchedulerConstants.PARAM_NAME_TASK_CONFIGURATION);
		if (!(taskConfiguration instanceof ExecutionTaskConfiguration)) {
			LOGGER.error("execute(context) fail. No configured ExecutionTaskConfiguration.");
			return;
		}
		final ExecutionTaskConfiguration configuration = ExecutionTaskConfiguration.class.cast(taskConfiguration);

		final ExecutionProcessor processor = Instantiator.instantiate(configuration.getProcessorClass());
		// Configure parameter
		if (processor instanceof ConfigurableTask)
			ConfigurableTask.class.cast(processor).configure(configuration.getParameters());

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
