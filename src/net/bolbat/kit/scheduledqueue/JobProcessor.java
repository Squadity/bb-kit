package net.bolbat.kit.scheduledqueue;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Quartz job implementation for {@link net.bolbat.kit.scheduledqueue.Loader} execution.
 *
 * @author ivanbatura
 */
public final class JobProcessor implements Job {

	/**
	 * {@link org.slf4j.Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(JobProcessor.class);

	/**
	 * Configured {@link net.bolbat.kit.scheduledqueue.Processor}.
	 */
	public static final String PROCESSOR = "processor";

	/**
	 * Configured {@link net.bolbat.kit.scheduledqueue.ProcessingMode}.
	 */
	public static final String PROCESSING_MODE = "processing_mode";

	@Override
	public void execute(final JobExecutionContext context) throws JobExecutionException {
		Object processorObj = context.getJobDetail().getJobDataMap().get(PROCESSOR);
		if (!(processorObj instanceof Processor)) {
			LOGGER.error("execute(context) fail. No configured Processor.");
			return;
		}
		Processor processor = Processor.class.cast(processorObj);

		ProcessingMode processingMode;
		Object processingModeObj = context.getJobDetail().getJobDataMap().get(PROCESSING_MODE);
		if (!(processingModeObj instanceof ProcessingMode)) {
			LOGGER.warn("execute(context) fail. No configured ProcessingMode.");
			return;
		}
		processingMode = ProcessingMode.class.cast(processingModeObj);
		debug("executing " + JobProcessor.class);
		if (processingMode == ProcessingMode.SYNC) {
			try {
				processor.process(null);
			} catch (ProcessingException e) {
				LOGGER.error("execute(context) processing fail.", e);
				//CHECKSTYLE:OFF
			} catch (Exception e) {
				//CHECKSTYLE:ON
				LOGGER.error("execute(context) processing fail.", e);
			}

			return;
		}

		// TODO: put result to queue to process in ASYNC mode
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
