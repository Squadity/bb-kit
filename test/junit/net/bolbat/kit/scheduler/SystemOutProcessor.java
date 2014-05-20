package net.bolbat.kit.scheduler;

import java.util.concurrent.atomic.AtomicInteger;

import net.bolbat.kit.scheduler.task.ConfigurableTask;
import net.bolbat.kit.scheduler.task.ProcessingException;
import net.bolbat.kit.scheduler.task.execution.ExecutionProcessor;
import net.bolbat.kit.scheduler.task.queue.QueueProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * {@link net.bolbat.kit.scheduler.task.queue.QueueProcessor} testing implementation.
 *
 * @author ivanbatura
 */
public class SystemOutProcessor implements QueueProcessor<String>, ExecutionProcessor, ConfigurableTask {

	/**
	 * Test serial UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemOutProcessor.class);

	/**
	 * Processed elements amount.
	 */
	private final AtomicInteger processed = new AtomicInteger(0);

	/**
	 * {@link TaskParameters}.
	 */
	private TaskParameters taskParameters;

	@Override
	public void process(final String element) throws ProcessingException {
		LOGGER.info("Element[" + element + "], processed at: " + System.currentTimeMillis());
		processed.incrementAndGet();
	}

	@Override
	public void process() throws ProcessingException {
		LOGGER.info("Processed at: " + System.currentTimeMillis());
		processed.incrementAndGet();
	}

	public int getProcessed() {
		return processed.get();
	}

	public TaskParameters getParameters() {
		return taskParameters;
	}

	@Override
	public void configure(TaskParameters parameters) {
		taskParameters = parameters;
	}
}
