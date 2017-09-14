package net.bolbat.kit.orchestrator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import net.bolbat.kit.orchestrator.exception.ConcurrentOverflowException;
import net.bolbat.kit.orchestrator.exception.ExecutionTimeoutException;
import net.bolbat.kit.orchestrator.exception.ExecutorOverflowException;
import net.bolbat.kit.orchestrator.exception.OrchestrationException;
import net.bolbat.kit.service.ServiceException;
import net.bolbat.kit.service.ServiceRuntimeException;
import net.bolbat.utils.annotation.Mark.ToDo;

/**
 * {@link Orchestrator} test.
 * 
 * @author Alexandr Bolbat
 */
@ToDo("add test cases for orchestration configured from file configuration")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrchestratorTest {

	private static CallableService service;

	@BeforeClass
	public static void beforeClass() {
		service = OrchestratorFactory.getDefault().init(new CallableServiceImpl());
		service.callAsyncVoid(0, TimeUnit.MILLISECONDS); // warmup async executor
	}

	@AfterClass
	public static void afterClass() {
		OrchestratorFactory.tearDown();
	}

	@Before
	public void before() {
		after();
	}

	@After
	public void after() {
		service.resetCallResponce();
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
	public void callOnMethodLimitsAndClassExecutor() throws Exception {
		// validating executor
		final String threadName = service.callWithMethodLimitsAndClassExecutor(0, TimeUnit.MILLISECONDS);
		Assert.assertNotNull(threadName);
		Assert.assertNotEquals(Thread.currentThread().getName(), threadName);
		Assert.assertTrue(threadName.contains("-n[CallableServiceImpl]-"));

		// validating concurrency limits
		final CountDownLatch startLatch = new CountDownLatch(1);
		final CountDownLatch finishLatch = new CountDownLatch(2);

		final ExecutorService executor = Executors.newCachedThreadPool();
		final CallTask task = new CallTask(startLatch, finishLatch, new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				service.callWithMethodLimitsAndClassExecutor(1, TimeUnit.MILLISECONDS);
				return null;
			}
		});
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

		// validating time limits
		service.callWithMethodLimitsAndClassExecutor(1, TimeUnit.MILLISECONDS);

		try {
			service.callWithMethodLimitsAndClassExecutor(25, TimeUnit.MILLISECONDS);
			Assert.fail();
		} catch (final ExecutionTimeoutException e) {
			final boolean checkResult = e.getMessage().endsWith("message[timeout is reached]");
			Assert.assertTrue(checkResult);
			if (!checkResult) // debug logging
				System.out.println("DEBUG: " + e);
		}
	}

	@Test
	public void callOnMethodExecutor() throws Exception {
		// validating executor
		final String threadName = service.callWithMethodExecutor(0, TimeUnit.MILLISECONDS);
		Assert.assertNotNull(threadName);
		Assert.assertNotEquals(Thread.currentThread().getName(), threadName);
		Assert.assertTrue(threadName.contains("-n[CallableServiceImpl.callWithMethodExecutor(long,java.util.concurrent.TimeUnit)]-"));

		// validating executor queue size
		final CountDownLatch startLatch = new CountDownLatch(1);
		final CountDownLatch finishLatch = new CountDownLatch(2);

		final ExecutorService executor = Executors.newCachedThreadPool();
		final CallTask task = new CallTask(startLatch, finishLatch, new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				service.callWithMethodExecutor(5, TimeUnit.MILLISECONDS);
				return null;
			}
		});
		final List<Future<Boolean>> futures = new ArrayList<>();
		for (int i = 0; i < 5; i++)
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

		Assert.assertNotEquals(5, succesfull);
		Assert.assertNotEquals(0, failed);
		Assert.assertNotEquals(5, failed);
		Assert.assertEquals(true, task.call());

		executor.shutdownNow();
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
	public void callTimeOut() {
		service.callWithTimeOut(1, TimeUnit.MILLISECONDS);

		try {
			service.callWithTimeOut(10, TimeUnit.MILLISECONDS);
			Assert.fail();
		} catch (final ExecutionTimeoutException e) {
			final boolean checkResult = e.getMessage().endsWith("message[timeout is reached]");
			Assert.assertTrue(checkResult);
			if (!checkResult) // debug logging
				System.out.println("DEBUG: " + e);
		}
	}

	@Test
	public void callOnSmallQueue() throws Exception {
		final CountDownLatch startLatch = new CountDownLatch(1);
		final CountDownLatch finishLatch = new CountDownLatch(2);

		final ExecutorService executor = Executors.newCachedThreadPool();
		final CallTask task = new CallTask(startLatch, finishLatch, new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				service.callWithTimeOutOnSmallQueue(5, TimeUnit.MILLISECONDS);
				return null;
			}
		});
		final List<Future<Boolean>> futures = new ArrayList<>();
		for (int i = 0; i < 5; i++)
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

		Assert.assertNotEquals(5, succesfull);
		Assert.assertNotEquals(0, failed);
		Assert.assertNotEquals(5, failed);
		Assert.assertEquals(true, task.call());

		executor.shutdownNow();
	}

	@Test
	public void callOnConcurrentLimit() throws Exception {
		final CountDownLatch startLatch = new CountDownLatch(1);
		final CountDownLatch finishLatch = new CountDownLatch(2);

		final ExecutorService executor = Executors.newCachedThreadPool();
		final CallTask task = new CallTask(startLatch, finishLatch, new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				service.callWithTimeOutAndConcurrentLimit(5, TimeUnit.MILLISECONDS);
				return null;
			}
		});
		final List<Future<Boolean>> futures = new ArrayList<>();
		for (int i = 0; i < 5; i++)
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

		Assert.assertNotEquals(5, succesfull);
		Assert.assertNotEquals(0, failed);
		Assert.assertNotEquals(5, failed);
		Assert.assertEquals(true, task.call());

		executor.shutdownNow();
	}

	@Test
	public void callAsyncVoid() {
		service.callAsyncVoid(20, TimeUnit.MILLISECONDS);

		sleep(5, TimeUnit.MILLISECONDS);

		CallResponce resp = service.getCallResponce();
		Assert.assertEquals(CallResponce.State.INITIATED, resp.getState());

		sleep(20, TimeUnit.MILLISECONDS);

		resp = service.getCallResponce();
		Assert.assertEquals(CallResponce.State.EXECUTED, resp.getState());
		Assert.assertTrue("response: " + resp, resp.getThreadName().startsWith("Orchestrator["));
		Assert.assertTrue("response: " + resp, resp.getThreadName().contains("]-n[CallableServiceImpl]-"));
	}

	@Test
	public void callAsyncNotVoid() {
		service.callAsyncNotVoid(5, TimeUnit.MILLISECONDS);

		final CallResponce resp = service.getCallResponce();
		Assert.assertEquals(CallResponce.State.EXECUTED, resp.getState());
		Assert.assertTrue("response: " + resp, resp.getThreadName().startsWith("Orchestrator["));
		Assert.assertTrue("response: " + resp, resp.getThreadName().contains("]-n[CallableServiceImpl]-"));
	}

	@Test
	@Ignore
	public void callAsyncWithNotReachedTimeOut() {
		Assert.fail("Implement me");
	}

	@Test
	@Ignore
	public void callAsyncWithReachedTimeOut() {
		Assert.fail("Implement me");
	}

	@Test
	@Ignore
	public void callAsyncWithNotReachedQueueLimit() {
		Assert.fail("Implement me");
	}

	@Test
	@Ignore
	public void callAsyncWithReachedQueueLimit() {
		Assert.fail("Implement me");
	}

	@Test
	@Ignore
	public void callAsyncWithNotReachedConcurrentLimit() {
		Assert.fail("Implement me");
	}

	@Test
	@Ignore
	public void callAsyncWithReachedConcurrentLimit() {
		Assert.fail("Implement me");
	}

	private static void sleep(final long time, final TimeUnit timeUnit) {
		try {
			Thread.sleep(timeUnit.toMillis(time));
		} catch (final InterruptedException e) {
			throw new RuntimeException("execution is interrupted", e);
		}
	}

	private static class CallTask implements Callable<Boolean> {

		private final CountDownLatch startLatch;
		private final CountDownLatch finishLatch;
		private final Callable<Object> call;

		public CallTask(final CountDownLatch aStartLatch, final CountDownLatch aFinishLatch, final Callable<Object> aCall) {
			this.startLatch = aStartLatch;
			this.finishLatch = aFinishLatch;
			this.call = aCall;
		}

		@Override
		public Boolean call() throws Exception {
			try {
				startLatch.await();
			} catch (final InterruptedException e) {
				throw new OrchestrationException("execution is interrupted", e);
			}

			try {
				call.call();
				return true;
			} catch (final ConcurrentOverflowException e) {
				final boolean checkResult = e.getMessage().endsWith("message[concurrent executions limit is reached]");
				Assert.assertTrue(checkResult);
				if (!checkResult) // debug logging
					System.out.println("DEBUG: " + e);

				return false;
			} catch (final ExecutorOverflowException e) {
				final boolean checkResult = e.getMessage().endsWith("message[executor queue is full]");
				Assert.assertTrue(checkResult);
				if (!checkResult) // debug logging
					System.out.println("DEBUG: " + e);

				return false;
			} finally {
				finishLatch.countDown();
			}
		}
	}

}
