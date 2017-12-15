package net.bolbat.kit.orchestrator;

import static net.bolbat.utils.lang.StringUtils.EMPTY;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import net.bolbat.utils.concurrency.ThreadFactoryBuilder;

/**
 * {@link Orchestrator} performance test.
 * 
 * @author Alexandr Bolbat
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrchestratorTestPerformance {

	private static final int EXECUTIONS = 1000;
	private static final int THREADS = 4;

	private static final String EXECUTOR_NAME_FORMAT = "test-pool-%d";
	private static final ThreadFactory EXECUTOR_FACTORY = new ThreadFactoryBuilder().setNameFormat(EXECUTOR_NAME_FORMAT).build();

	private static ExecutorService executor;
	private static CallableService directService;
	private static CallableService orchestratedService;

	// runtime values - used by assertions
	private static double case1Time = 0;
	private static double case1Avg = 0;
	private static double case3Time = 0;
	private static double case3Avg = 0;
	private static double case5Time = 0;
	private static double case5Avg = 0;

	@BeforeClass
	public static void beforeClass() {
		executor = Executors.newFixedThreadPool(THREADS, EXECUTOR_FACTORY);
		directService = new CallableServiceImpl();
		orchestratedService = OrchestratorFactory.getDefault().init(new CallableServiceImpl());
	}

	@AfterClass
	public static void afterClass() {
		executor.shutdownNow();
		OrchestratorFactory.tearDown();
	}

	@Test
	public void case1Direct() throws Exception {
		execute(Cases.CASE1, EXECUTIONS, 1, new Callable<String>() {
			@Override
			public String call() throws Exception {
				return directService.callDirect();
			}
		});
	}

	@Test
	public void case2MultithreadedDirect() throws Exception {
		execute(Cases.CASE2, EXECUTIONS, THREADS, new Callable<String>() {
			@Override
			public String call() throws Exception {
				return directService.callDirect();
			}
		});
	}

	@Test
	public void case3OrchestratedDirect() throws Exception {
		execute(Cases.CASE3, EXECUTIONS, 1, new Callable<String>() {
			@Override
			public String call() throws Exception {
				return orchestratedService.callDirect();
			}
		});
	}

	@Test
	public void case4MultithreadedOrchestratedDirect() throws Exception {
		execute(Cases.CASE4, EXECUTIONS, THREADS, new Callable<String>() {
			@Override
			public String call() throws Exception {
				return orchestratedService.callDirect();
			}
		});
	}

	@Test
	public void case5Orchestrated() throws Exception {
		execute(Cases.CASE5, EXECUTIONS, 1, new Callable<String>() {
			@Override
			public String call() throws Exception {
				return orchestratedService.callOrchestrated();
			}
		});
	}

	@Test
	public void case6MultithreadedOrchestrated() throws Exception {
		execute(Cases.CASE6, EXECUTIONS, THREADS, new Callable<String>() {
			@Override
			public String call() throws Exception {
				return orchestratedService.callOrchestrated();
			}
		});
	}

	private void execute(final Cases c, final int executions, final int threads, final Callable<String> callable) throws Exception {
		final String callableThreadName = callable.call();

		long startTime = 0;
		long endTime = 0;
		BigDecimal avgTime = null;

		if (threads > 1) {
			final CountDownLatch startLatch = new CountDownLatch(threads + 1);
			final CountDownLatch finishLatch = new CountDownLatch(threads);
			final List<Future<Long>> futures = new ArrayList<>();
			final int executionsPerThread = executions / threads;

			for (int i = 0; i < threads; i++) {
				futures.add(executor.submit(new CallableTask(startLatch, finishLatch, executionsPerThread, callable)));
				startLatch.countDown();
			}

			startTime = System.nanoTime();

			startLatch.countDown(); // starting execution
			finishLatch.await(); // waiting when they finish

			endTime = System.nanoTime();

			// calculate real avg
			avgTime = new BigDecimal(0);
			for (final Future<Long> future : futures)
				avgTime = avgTime.add(new BigDecimal(future.get()).divide(new BigDecimal(executionsPerThread), 6, RoundingMode.HALF_UP));

			avgTime = avgTime.divide(new BigDecimal(threads), 6, RoundingMode.HALF_UP);
			avgTime = avgTime.divide(new BigDecimal(1000000L), 6, RoundingMode.HALF_UP);
		} else {
			startTime = System.nanoTime();

			for (int i = 0; i < executions; i++)
				callable.call();

			endTime = System.nanoTime();
		}

		final BigDecimal executionTime = new BigDecimal(endTime - startTime).divide(new BigDecimal(1000000L), 6, RoundingMode.HALF_UP);
		if (avgTime == null)
			avgTime = executionTime.divide(new BigDecimal(executions), 6, RoundingMode.HALF_UP);

		final StringBuilder sb = new StringBuilder();
		sb.append("case[" + c.getName() + "]");
		sb.append(" executions[" + executions + "]").append(" threads[" + threads + "]");
		sb.append(" in[" + executionTime + "ms]").append(" avg[" + avgTime + "ms]");
		sb.append("\n\t").append(" execution thread[" + callableThreadName + "]");
		sb.append("\n");
		System.out.println(sb.toString());

		if (1000 >= executions && 4 >= threads) // enabling assertions only for tuned 'executions' and 'threads' amount
			validateResult(c, executionTime.doubleValue(), avgTime.doubleValue());
	}

	public void validateResult(final Cases c, final double time, final double avg) {
		double maxTime = 0;
		double maxAvg = 0;
		String timeErrorMsg = EMPTY;
		String avgErrorMsg = EMPTY;
		switch (c) {
			case CASE1:
				case1Time = time;
				case1Avg = avg;
				break;
			case CASE2: // multi-threaded
				maxTime = case1Time * 2; // basically maxTime should be less then case1Time, but sometimes it a little bit higher on small executions amount
				timeErrorMsg = "time[" + time + "] should be less than maxTime[" + maxTime + " (" + case1Time + " * " + 2 + ")]";
				Assert.assertTrue(timeErrorMsg, time < maxTime);

				// in multi-threaded execution 'avg' can be bigger, but not much
				maxAvg = case1Avg * 3;
				avgErrorMsg = "avg[" + avg + "] should be less than maxAvg[" + maxAvg + " (" + case1Avg + " * " + 3 + ")]";
				Assert.assertTrue(avgErrorMsg, avg < maxAvg);
				break;
			case CASE3:
				case3Time = time;
				case3Avg = avg;

				maxTime = case1Time * 10;
				timeErrorMsg = "time[" + time + "] should be less than maxTime[" + maxTime + " (" + case1Time + " * " + 10 + ")]";
				Assert.assertTrue(timeErrorMsg, time < maxTime);

				maxAvg = case1Avg * 10;
				avgErrorMsg = "avg[" + avg + "] should be less than maxAvg[" + maxAvg + " (" + case1Avg + " * " + 10 + ")]";
				Assert.assertTrue(avgErrorMsg, avg < maxAvg);
				break;
			case CASE4: // multi-threaded
				maxTime = case3Time * 2; // basically maxTime should be less then case1Time, but sometimes it a little bit higher on small executions amount
				timeErrorMsg = "time[" + time + "] should be less than maxTime[" + maxTime + " (" + case3Time + " * " + 2 + ")]";
				Assert.assertTrue(timeErrorMsg, time < maxTime);

				// in multi-threaded execution 'avg' can be bigger, but not much
				maxAvg = case3Avg * 3;
				avgErrorMsg = "avg[" + avg + "] should be less than maxAvg[" + maxAvg + " (" + case3Avg + " * " + 3 + ")]";
				Assert.assertTrue(avgErrorMsg, avg < maxAvg);
				break;
			case CASE5:
				case5Time = time;
				case5Avg = avg;

				maxTime = case1Time * 150;
				timeErrorMsg = "time[" + time + "] should be less than maxTime[" + maxTime + " (" + case1Time + " * " + 150 + ")]";
				Assert.assertTrue(timeErrorMsg, time < maxTime);

				maxAvg = case1Avg * 150;
				avgErrorMsg = "avg[" + avg + "] should be less than maxAvg[" + maxAvg + " (" + case1Avg + " * " + 150 + ")]";
				Assert.assertTrue(avgErrorMsg, avg < maxAvg);
				break;
			case CASE6: // multi-threaded
				maxTime = case5Time * 2; // basically maxTime should be less then case1Time, but sometimes it a little bit higher on small executions amount
				timeErrorMsg = "time[" + time + "] should be less than maxTime[" + maxTime + " (" + case5Time + " * " + 2 + ")]";
				Assert.assertTrue(timeErrorMsg, time < maxTime);

				// in multi-threaded execution 'avg' can be bigger, but not much
				maxAvg = case5Avg * 3;
				avgErrorMsg = "avg[" + avg + "] should be less than maxAvg[" + maxAvg + " (" + case5Avg + " * " + 3 + ")]";
				Assert.assertTrue(avgErrorMsg, avg < maxAvg);
				break;
			default:
				break;
		}
	}

	public static class CallableTask implements Callable<Long> {

		private final CountDownLatch startLatch;
		private final CountDownLatch finishLatch;
		private final int executions;
		private final Callable<String> callable;

		public CallableTask(final CountDownLatch startLatch, final CountDownLatch finishLatch, final int executions, final Callable<String> callable) {
			this.startLatch = startLatch;
			this.finishLatch = finishLatch;
			this.executions = executions;
			this.callable = callable;
		}

		@Override
		public Long call() throws Exception {
			try {
				startLatch.await();
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}

			try {
				final long startTime = System.nanoTime();

				for (int i = 0; i < executions; i++)
					callable.call();

				return System.nanoTime() - startTime;
			} finally {
				finishLatch.countDown();
			}
		}

	}

	public enum Cases {

		CASE1("DIRECT"), //
		CASE2("M-DIRECT"), //
		CASE3("ORCHESTRATED-DIRECT"), //
		CASE4("M-ORCHESTRATED-DIRECT"), //
		CASE5("ORCHESTRATED"), //
		CASE6("M-ORCHESTRATED");

		private final String name;

		private Cases(final String aName) {
			this.name = aName;
		}

		public String getName() {
			return name;
		}

	}

}
