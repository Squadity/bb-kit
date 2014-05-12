package net.bolbat.kit.scheduler;

import net.bolbat.kit.scheduler.task.execution.ExecutionTaskBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link net.bolbat.kit.scheduler.task.execution.ExecutionTask} test.
 *
 * @author ivanbatura
 */
public class ExecutionSchedulerTest {

	/**
	 * Testing {@link net.bolbat.kit.scheduler.Scheduler} instance.
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
	public void executionScheduleModeIntervalTest() throws SchedulerException, InterruptedException {
		ExecutionTaskBuilder builder = new ExecutionTaskBuilder();
		builder.processor(processor).configuration("quartz.properties").configurationType(SchedulerConfigurationType.PROPERTY);
		;
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
	 * Complex test for interval based schedule.
	 */
	@Test
	public void executionScheduleModeCronTest() throws SchedulerException, InterruptedException {
		ExecutionTaskBuilder builder = new ExecutionTaskBuilder();
		builder.processor(processor).configuration("quartz.properties").configurationType(SchedulerConfigurationType.PROPERTY);
		queue = SchedulerFactory.create(builder.build());

		queue.schedule("0/1 * * * * ?");
		Assert.assertTrue(queue.isStarted()); // should be already started

		Thread.sleep(1000L);
		Assert.assertFalse(queue.isPaused()); // shouldn't be paused

		Thread.sleep(1500L);
		Assert.assertTrue("Loaded and processed elements amount should be the same.", processor.getProcessed() > 0);

		queue.pause();
		Assert.assertTrue(queue.isPaused()); // should be paused

		queue.resume();
		Assert.assertFalse(queue.isPaused()); // shouldn't be paused

		Assert.assertTrue(queue.isStarted()); // will be false after tear down
	}
}
