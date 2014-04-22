package net.bolbat.kit.scheduler.task.queue;

import java.util.List;

import net.bolbat.kit.scheduler.Task;
import net.bolbat.kit.scheduler.task.LoadingException;
import net.bolbat.kit.scheduler.task.ProcessingException;
import net.bolbat.utils.logging.LoggingUtils;
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
		Object loaderObj = context.getJobDetail().getJobDataMap().get(QueueConstants.LOADER);
		if (!(loaderObj instanceof QueueLoader)) {
			LOGGER.error("execute(context) fail. No configured QueueLoader.");
			return;
		}
		@SuppressWarnings ("unchecked")
		QueueLoader<T> loader = QueueLoader.class.cast(loaderObj);

		Object processorObj = context.getJobDetail().getJobDataMap().get(QueueConstants.PROCESSOR);
		if (!(processorObj instanceof QueueProcessor)) {
			LOGGER.error("execute(context) fail. No configured QueueProcessor.");
			return;
		}
		@SuppressWarnings ("unchecked")
		QueueProcessor<T> processor = QueueProcessor.class.cast(processorObj);

		ProcessingMode processingMode;
		Object processingModeObj = context.getJobDetail().getJobDataMap().get(QueueConstants.PROCESSING_MODE);
		if (!(processingModeObj instanceof ProcessingMode)) {
			LOGGER.warn("execute(context) fail. No configured ProcessingMode.");
			return;
		}
		processingMode = ProcessingMode.class.cast(processingModeObj);

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
					} catch (ProcessingException e) {
						LOGGER.error("execute(context) processing fail. Skipping element[" + element + "].", e);
						//CHECKSTYLE:OFF
					} catch (Exception e) {
						//CHECKSTYLE:ON
						LOGGER.error("execute(context) processing fail. Skipping element[" + element + "].", e);
					}
				}
			}

			// TODO: put result to queue to process in ASYNC mode.
		} catch (LoadingException e) {
			String message = "execute(context) loading fail.";
			LOGGER.error(message, e);
			//CHECKSTYLE:OFF
		} catch (Exception e) {
			//CHECKSTYLE:ON
			String message = "execute(context) fail";
			LOGGER.error(message, e);
			throw new JobExecutionException(message, e);
		}
	}
}
