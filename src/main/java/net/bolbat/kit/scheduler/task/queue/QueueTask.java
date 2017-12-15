package net.bolbat.kit.scheduler.task.queue;

import java.util.List;

import net.bolbat.kit.scheduler.SchedulerConstants;
import net.bolbat.kit.scheduler.Task;
import net.bolbat.kit.scheduler.task.ConfigurableTask;
import net.bolbat.kit.scheduler.task.LoadingException;
import net.bolbat.kit.scheduler.task.ProcessingException;
import net.bolbat.utils.logging.LoggingUtils;
import net.bolbat.utils.reflect.Instantiator;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Quartz job implementation for {@link Task} execution.
 *
 * @param <T>
 * 		type for queue task
 * @author ivanbatura
 */
public final class QueueTask<T> implements Task {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(QueueTask.class);

	@Override
	public void execute(final JobExecutionContext context) throws JobExecutionException {
		final Object taskConfiguration = context.getJobDetail().getJobDataMap().get(SchedulerConstants.PARAM_NAME_TASK_CONFIGURATION);
		if (!(taskConfiguration instanceof QueueTaskConfiguration)) {
			LOGGER.error("execute(context) fail. No configured QueueTaskConfiguration.");
			return;
		}
		@SuppressWarnings ("unchecked")
		final QueueTaskConfiguration<T> configuration = QueueTaskConfiguration.class.cast(taskConfiguration);

		//loader
		final QueueLoader<T> loader = Instantiator.instantiate(configuration.getLoaderClass());
		// Configure loader parameters
		if (loader instanceof ConfigurableTask)
			ConfigurableTask.class.cast(loader).configure(configuration.getParameters());

		//processor
		final QueueProcessor<T> processor = Instantiator.instantiate(configuration.getProcessorClass());
		// Configure loader parameters
		if (processor instanceof ConfigurableTask)
			ConfigurableTask.class.cast(processor).configure(configuration.getParameters());
		final ProcessingMode processingMode = configuration.getProcessingMode();

		try {
			LoggingUtils.debug(LOGGER, "executing " + QueueTask.class);
			List<T> result = loader.load();
			LoggingUtils.debug(LOGGER, "loaded " + (result != null ? result.size() : 0) + " elements");

			if (result == null || result.isEmpty())
				return;

			if (processingMode == ProcessingMode.SYNC) {
				for (T element : result) {
					try {
						LoggingUtils.debug(LOGGER, "processing element[" + element + "]");
						processor.process(element);
					} catch (final ProcessingException e) {
						LOGGER.error("execute(context) processing fail. Skipping element[" + element + "].", e);
						//CHECKSTYLE:OFF
					} catch (final Exception e) {
						//CHECKSTYLE:ON
						LOGGER.error("execute(context) processing fail. Skipping element[" + element + "].", e);
					}
				}
			}

			// TODO: put result to queue to process in ASYNC mode.
		} catch (final LoadingException e) {
			final String message = "execute(context) loading fail.";
			LOGGER.error(message, e);
			//CHECKSTYLE:OFF
		} catch (final Exception e) {
			//CHECKSTYLE:ON
			final String message = "execute(context) fail";
			LOGGER.error(message, e);
			throw new JobExecutionException(message, e);
		}
	}
}
