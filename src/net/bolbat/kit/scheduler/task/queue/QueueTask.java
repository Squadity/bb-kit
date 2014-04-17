package net.bolbat.kit.scheduler.task.queue;

import java.util.List;

import net.bolbat.kit.scheduler.Task;
import net.bolbat.kit.scheduler.task.LoadingException;
import net.bolbat.kit.scheduler.task.ProcessingException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Quartz job implementation for {@link Task} execution.
 *
 * @author ivanbatura
 */
public final class QueueTask implements Task {

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
		QueueLoader loader = QueueLoader.class.cast(loaderObj);

		Object processorObj = context.getJobDetail().getJobDataMap().get(QueueConstants.PROCESSOR);
		if (!(processorObj instanceof QueueProcessor)) {
			LOGGER.error("execute(context) fail. No configured QueueProcessor.");
			return;
		}
		QueueProcessor processor = QueueProcessor.class.cast(processorObj);

		ProcessingMode processingMode;
		Object processingModeObj = context.getJobDetail().getJobDataMap().get(QueueConstants.PROCESSING_MODE);
		if (!(processingModeObj instanceof ProcessingMode)) {
			LOGGER.warn("execute(context) fail. No configured ProcessingMode.");
			return;
		}
		processingMode = ProcessingMode.class.cast(processingModeObj);

		try {
			debug("executing " + QueueTask.class);
			List<Object> result = loader.load();
			debug("loaded " + (result != null ? result.size() : 0) + " elements");

			if (result == null || result.isEmpty())
				return;

			if (processingMode == ProcessingMode.SYNC) {
				for (Object element : result) {
					try {
						debug("processing element[" + element + "]");
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

			// TODO: put result to queue to process in ASYNC mode
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
