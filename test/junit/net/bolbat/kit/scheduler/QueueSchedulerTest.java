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
	 * Testing parameters.
	 */
	private TaskParameters parameters;

	/**
	 * Test parameter name.
	 */
	private static final String testParameterName = "test";

	/**
	 * Test parameter.
	 */
	private static final String testParameter = "test";

	/**
	 * Initialization executed before each test.
	 */
	@Before
	public void before() throws SchedulerException {
		//initialization
		processor = new SystemOutProcessor();
		loader = new RandomGenerationLoader();
		parameters = new TaskParameters();
		parameters.putString(testParameterName, testParameter);

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
		QueueTaskBuilder<String> builder = new QueueTaskBuilder<>();
		builder.loaderClass(loader.getClass());
		builder.processorClass(processor.getClass());
		builder.processingMode(ProcessingMode.SYNC);
		builder.configuration("quartz.properties");
		builder.configurationType(SchedulerConfigurationType.PROPERTY);
		builder.parameters(parameters);
		queue = SchedulerFactory.create(builder.build());

		queue.schedule(1L);
		Assert.assertTrue(queue.isStarted()); // should be already started

		Thread.sleep(10L);
		Assert.assertFalse(queue.isPaused()); // shouldn't be paused

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
		QueueTaskBuilder<String> builder = new QueueTaskBuilder<>();
		builder.processorClass(processor.getClass()).loaderClass(loader.getClass()).configuration("quartz.properties").configurationType(SchedulerConfigurationType.PROPERTY).parameters(parameters);
		queue = SchedulerFactory.create(builder.build());

		queue.schedule("0/1 * * * * ?");
		Assert.assertTrue(queue.isStarted()); // should be already started

		Thread.sleep(1000L);
		Assert.assertFalse(queue.isPaused()); // shouldn't be paused

		queue.pause();
		Assert.assertTrue(queue.isPaused()); // should be paused

		queue.resume();
		Assert.assertFalse(queue.isPaused()); // shouldn't be paused

		Assert.assertTrue(queue.isStarted()); // will be false after tear down
	}
}
