package net.bolbat.kit.scheduler;

import java.util.concurrent.atomic.AtomicInteger;

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
public class SystemOutProcessor implements QueueProcessor, ExecutionProcessor {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemOutProcessor.class);
	/**
	 * Test serial UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Processed elements amount.
	 */
	private final AtomicInteger processed = new AtomicInteger(0);

	@Override
	public void process(final Object element) throws ProcessingException {
		LOGGER.info("Element[" + element + "], processed at: " + System.currentTimeMillis());
		processed.incrementAndGet();
	}

	public int getProcessed() {
		return processed.get();
	}

}
