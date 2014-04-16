package net.bolbat.kit.scheduler.task.schedulequeue;

import java.util.List;

import net.bolbat.kit.scheduler.ScheduledJob;
import net.bolbat.kit.scheduler.task.Loader;
import net.bolbat.kit.scheduler.task.LoadingException;
import net.bolbat.kit.scheduler.task.ProcessingException;
import net.bolbat.kit.scheduler.task.ProcessingMode;
import net.bolbat.kit.scheduler.task.Processor;
import net.bolbat.kit.scheduler.task.ScheduledConstants;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Quartz job implementation for {@link net.bolbat.kit.scheduler.task.Loader} execution.
 *
 * @author ivanbatura
 */
public final class ScheduledQueueJob implements ScheduledJob {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledQueueJob.class);

	@Override
	public void execute(final JobExecutionContext context) throws JobExecutionException {
		Object loaderObj = context.getJobDetail().getJobDataMap().get(ScheduledConstants.LOADER);
		if (!(loaderObj instanceof Loader)) {
			LOGGER.error("execute(context) fail. No configured Loader.");
			return;
		}
		Loader loader = Loader.class.cast(loaderObj);


		Object processorObj = context.getJobDetail().getJobDataMap().get(ScheduledConstants.PROCESSOR);
		if (!(processorObj instanceof Processor)) {
			LOGGER.error("execute(context) fail. No configured Processor.");
			return;
		}
		Processor processor = Processor.class.cast(processorObj);

		ProcessingMode processingMode;
		Object processingModeObj = context.getJobDetail().getJobDataMap().get(ScheduledConstants.PROCESSING_MODE);
		if (!(processingModeObj instanceof ProcessingMode)) {
			LOGGER.warn("execute(context) fail. No configured ProcessingMode.");
			return;
		}
		processingMode = ProcessingMode.class.cast(processingModeObj);

		try {
			debug("executing " + ScheduledQueueJob.class);
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
