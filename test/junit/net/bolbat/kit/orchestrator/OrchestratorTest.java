package net.bolbat.kit.orchestrator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import net.bolbat.kit.orchestrator.exception.ConcurrentOverflowException;
import net.bolbat.kit.orchestrator.exception.ExecutionTimeoutException;
import net.bolbat.kit.orchestrator.exception.ExecutorOverflowException;
import net.bolbat.kit.orchestrator.exception.OrchestrationException;
import net.bolbat.kit.service.ServiceException;
import net.bolbat.kit.service.ServiceRuntimeException;

/**
 * {@link Orchestrator} test.
 * 
 * @author Alexandr Bolbat
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrchestratorTest {

	private static CallableService service;

	@BeforeClass
	public static void beforeClass() {
		service = OrchestratorFactory.getDefault().init(new CallableServiceImpl());
	}

	@AfterClass
	public static void afterClass() {
		OrchestratorFactory.tearDown();
	}

	@Test
	public void threadNameForDisabledOrchestration() {
		final String threadName = service.callDirect();
		Assert.assertNotNull(threadName);
		Assert.assertEquals(Thread.currentThread().getName(), threadName);
	}

	@Test
	public void threadNameForEnabledOrchestration() {
		final String threadName = service.callOrchestrated();
		Assert.assertNotNull(threadName);
		Assert.assertNotEquals(Thread.currentThread().getName(), threadName);
		Assert.assertTrue(threadName.contains("-n[CallableServiceImpl.callOrchestrated()]-"));
	}

	@Test
	public void threadNameForOrchestrationWithClassExecutor() {
		final String threadName = service.callOrchestratedByClassExecutor();
		Assert.assertNotNull(threadName);
		Assert.assertNotEquals(Thread.currentThread().getName(), threadName);
		Assert.assertTrue(threadName.contains("-n[CallableServiceImpl]-"));
	}

	@Test
	public void threadNameForOrchestrationWithSystemExecutor() {
		final String threadName = service.callOrchestratedBySystemExecutor();
		Assert.assertNotNull(threadName);
		Assert.assertNotEquals(Thread.currentThread().getName(), threadName);
		Assert.assertTrue(threadName.startsWith("Orchestrator[system]-"));
	}

	@Test
	public void callOrchestratedByClassWithServiceException() {
		try {
			service.callOrchestratedByClassWithServiceException();
			Assert.fail();
		} catch (final ServiceException e) {
			Assert.assertTrue(e.getMessage().equals("custom checked service exception"));
		}
	}

	@Test
	public void callOrchestratedByClassWithServiceRuntimeException() {
		try {
			service.callOrchestratedByClassWithServiceRuntimeException();
			Assert.fail();
		} catch (final ServiceRuntimeException e) {
			Assert.assertTrue(e.getMessage().equals("custom runtime service exception"));
		}
	}

	@Test
	public void executionTimeOut() {
		service.executeWithTimeOut(1, TimeUnit.MILLISECONDS);

		try {
			service.executeWithTimeOut(10, TimeUnit.MILLISECONDS);
			Assert.fail();
		} catch (final ExecutionTimeoutException e) {
			System.out.println("DEBUG: " + e);
			Assert.assertTrue(e.getMessage().endsWith("message[timeout is reached]"));
		}
	}

	@Test
	public void executionOnSmallQueue() throws Exception {
		final CountDownLatch startLatch = new CountDownLatch(1);
		final CountDownLatch finishLatch = new CountDownLatch(2);

		final ExecutorService executor = Executors.newCachedThreadPool();
		final SmallQueueCallTask task = new SmallQueueCallTask(startLatch, finishLatch);
		final List<Future<Boolean>> futures = new ArrayList<>();
		futures.add(executor.submit(task));
		futures.add(executor.submit(task));

		startLatch.countDown();
		finishLatch.await();

		int succesfull = 0;
		int failed = 0;
		for (final Future<Boolean> future : futures) {
			if (future.get())
				succesfull++;
			else
				failed++;
		}

		Assert.assertEquals(1, succesfull);
		Assert.assertEquals(1, failed);
		Assert.assertEquals(true, task.call());
	}

	@Test
	public void executionOnConcurrentLimit() throws Exception {
		final CountDownLatch startLatch = new CountDownLatch(1);
		final CountDownLatch finishLatch = new CountDownLatch(2);

		final ExecutorService executor = Executors.newCachedThreadPool();
		final ConcurrentLimitCallTask task = new ConcurrentLimitCallTask(startLatch, finishLatch);
		final List<Future<Boolean>> futures = new ArrayList<>();
		futures.add(executor.submit(task));
		futures.add(executor.submit(task));

		startLatch.countDown();
		finishLatch.await();

		int succesfull = 0;
		int failed = 0;
		for (final Future<Boolean> future : futures) {
			if (future.get())
				succesfull++;
			else
				failed++;
		}

		Assert.assertEquals(1, succesfull);
		Assert.assertEquals(1, failed);
		Assert.assertEquals(true, task.call());
	}

	private static class SmallQueueCallTask implements Callable<Boolean> {

		private final CountDownLatch startLatch;
		private final CountDownLatch finishLatch;

		public SmallQueueCallTask(final CountDownLatch aStartLatch, final CountDownLatch aFinishLatch) {
			this.startLatch = aStartLatch;
			this.finishLatch = aFinishLatch;
		}

		@Override
		public Boolean call() throws Exception {
			try {
				startLatch.await();
			} catch (final InterruptedException e) {
				throw new OrchestrationException("execution is interrupted", e);
			}

			try {
				service.executeWithTimeOutOnSmallQueue(5, TimeUnit.MILLISECONDS);
				return true;
			} catch (final ExecutorOverflowException e) {
				System.out.println("DEBUG: " + e);
				Assert.assertTrue(e.getMessage().endsWith("message[executor queue is full]"));
				return false;
			} finally {
				finishLatch.countDown();
			}
		}
	}

	private static class ConcurrentLimitCallTask implements Callable<Boolean> {

		private final CountDownLatch startLatch;
		private final CountDownLatch finishLatch;

		public ConcurrentLimitCallTask(final CountDownLatch aStartLatch, final CountDownLatch aFinishLatch) {
			this.startLatch = aStartLatch;
			this.finishLatch = aFinishLatch;
		}

		@Override
		public Boolean call() throws Exception {
			try {
				startLatch.await();
			} catch (final InterruptedException e) {
				throw new OrchestrationException("execution is interrupted", e);
			}

			try {
				service.executeWithTimeOutAndConcurrentLimit(5, TimeUnit.MILLISECONDS);
				return true;
			} catch (final ConcurrentOverflowException e) {
				System.out.println("DEBUG: " + e);
				Assert.assertTrue(e.getMessage().endsWith("message[concurrent executions limit is reached]"));
				return false;
			} finally {
				finishLatch.countDown();
			}
		}
	}

}
