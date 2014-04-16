package net.bolbat.kit.scheduler.task.process;

import net.bolbat.kit.scheduler.ScheduledJob;
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
public final class ProcessJob implements ScheduledJob {

	/**
	 * {@link org.slf4j.Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessJob.class);

	@Override
	public void execute(final JobExecutionContext context) throws JobExecutionException {
		Object processorObj = context.getJobDetail().getJobDataMap().get(ScheduledConstants.PROCESSOR);
		if (!(processorObj instanceof Processor)) {
			LOGGER.error("execute(context) fail. No configured Processor.");
			return;
		}
		Processor processor = Processor.class.cast(processorObj);

		Object processingModeObj = context.getJobDetail().getJobDataMap().get(ScheduledConstants.PROCESSING_MODE);
		if (!(processingModeObj instanceof ProcessingMode)) {
			LOGGER.warn("execute(context) fail. No configured ProcessingMode.");
			return;
		}
		debug("executing " + ProcessJob.class);
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
