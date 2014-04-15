package net.bolbat.kit.scheduledqueue;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link ScheduledQueue} test.
 *
 * @author ivanbatura
 */
public class ScheduledQueueTest {

	/**
	 * Testing {@link ScheduledQueue} instance.
	 */
	private ScheduledQueue queue;

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
	public void before() throws ScheduledQueueException {
		//initialization
		processor = new SystemOutProcessor();
		loader = new RandomGenerationLoader();
	}

	/**
	 * Initialization executed after each test.
	 */
	@After
	public void after() {
		if(queue!=null)
			queue.tearDown();
	}

	/**
	 * Complex test for interval based schedule.
	 */
	@Test
	public void complexTestForSyncModeIntervalScheduleTest() throws ScheduledQueueException, InterruptedException {
		queue = ScheduledQueueFactory.create("quartz.properties", loader, processor);

		queue.setMode(ProcessingMode.SYNC);
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
	public void complexTestForSyncModeCronScheduleTest() throws ScheduledQueueException, InterruptedException {
		queue = ScheduledQueueFactory.create("quartz.properties", loader, processor);

		queue.setMode(ProcessingMode.SYNC);
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

	/**
	 * Complex test for interval based schedule.
	 */
	@Test
	public void withoutLoaderScheduleTest() throws ScheduledQueueException, InterruptedException {
		queue = ScheduledQueueFactory.create("quartz.properties", processor);

		queue.setMode(ProcessingMode.SYNC);
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
	 * Complex test for schedule without file configuration
	 */
	@Test
	public void withoutConfigScheduleTest() throws ScheduledQueueException, InterruptedException {
		queue = ScheduledQueueFactory.create(loader, processor);

		queue.setMode(ProcessingMode.SYNC);
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

	/**
	 * Complex test for schedule without file configuration
	 */
	@Test
	public void configTest() throws ScheduledQueueException, InterruptedException {
		String threadCount = ScheduledConfigurationFactory.getConfiguration().getProperty(ScheduledConfigurationFactory.PARAM_THREAD_POOL_TREAD_COUNT);
		Assert.assertEquals("Incorrect config thread count value", 2, Integer.parseInt(threadCount));
	}

}
