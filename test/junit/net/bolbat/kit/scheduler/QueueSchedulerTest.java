package net.bolbat.kit.scheduler;

import net.bolbat.kit.scheduler.task.queue.ProcessingMode;
import net.bolbat.kit.scheduler.task.queue.QueueTaskBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link net.bolbat.kit.scheduler.task.queue.QueueTask} test.
 *
 * @author ivanbatura
 */
public class QueueSchedulerTest {

	/**
	 * Testing {@link Scheduler} instance.
	 */
	private Scheduler queue;

	/**
	 * Test loader. It's generating elements on the fly.
	 */
	private RandomGenerationLoader loader;

	/**
	 * Testing processor. It's counting incoming elements and writing it's to system out.
	 */
	private SystemOutProcessor processor;

	/**
	 * Initialization executed before each test.
	 */
	@Before
	public void before() throws SchedulerException {
		//initialization
		processor = new SystemOutProcessor();
		loader = new RandomGenerationLoader();
	}

	/**
	 * Initialization executed after each test.
	 */
	@After
	public void after() {
		if (queue != null)
			queue.tearDown();
	}

	/**
	 * Complex test for interval based schedule.
	 */
	@Test
	public void complexTestForSyncModeIntervalScheduleTest() throws SchedulerException, InterruptedException {
		QueueTaskBuilder<String> builder = new QueueTaskBuilder<String>();
		builder.loader(loader);
		builder.processor(processor);
		builder.processingMode(ProcessingMode.SYNC);
		builder.configuration("quartz.properties");
		builder.configurationType(SchedulerConfigurationType.PROPERTY);
		queue = SchedulerFactory.create(builder.build());

		queue.schedule(1L);
		Assert.assertTrue(queue.isStarted()); // should be already started

		Thread.sleep(10L);
		Assert.assertFalse(queue.isPaused()); // shouldn't be paused

		Thread.sleep(300L);
		Assert.assertEquals("Loaded and processed elements amount should be the same.", loader.getLoaded(), processor.getProcessed());

		queue.pause();
		Assert.assertTrue(queue.isPaused()); // should be paused

		queue.resume();
		Assert.assertFalse(queue.isPaused()); // shouldn't be paused

		Assert.assertTrue(queue.isStarted()); // will be false after tear down
	}

	/**
	 * Complex test for cron based schedule.
	 */
	@Test
	public void complexTestForSyncModeCronScheduleTest() throws SchedulerException, InterruptedException {
		QueueTaskBuilder<String> builder = new QueueTaskBuilder<String> ();
		builder.processor(processor).loader(loader).configuration("quartz.properties").configurationType(SchedulerConfigurationType.PROPERTY);
		queue = SchedulerFactory.create(builder.build());


		queue.schedule("0/1 * * * * ?");
		Assert.assertTrue(queue.isStarted()); // should be already started

		Thread.sleep(1000L);
		Assert.assertFalse(queue.isPaused()); // shouldn't be paused

		Thread.sleep(1500L);
		Assert.assertEquals("Loaded and processed elements amount should be the same.", loader.getLoaded(), processor.getProcessed());

		queue.pause();
		Assert.assertTrue(queue.isPaused()); // should be paused

		queue.resume();
		Assert.assertFalse(queue.isPaused()); // shouldn't be paused

		Assert.assertTrue(queue.isStarted()); // will be false after tear down
	}
}
