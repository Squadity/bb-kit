package net.bolbat.kit.orchestrator.impl;

import static net.bolbat.utils.lang.Validations.checkArgument;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.bolbat.kit.orchestrator.OrchestrationConfig.ExecutorConfig;
import net.bolbat.kit.orchestrator.OrchestrationConfig.LimitsConfig;
import net.bolbat.kit.orchestrator.OrchestrationConstants;
import net.bolbat.kit.orchestrator.annotation.OrchestrationMode.Mode;
import net.bolbat.kit.orchestrator.exception.ConcurrentOverflowException;
import net.bolbat.kit.orchestrator.exception.ExecutionTimeoutException;
import net.bolbat.kit.orchestrator.exception.ExecutorOverflowException;
import net.bolbat.kit.orchestrator.exception.OrchestrationException;
import net.bolbat.kit.orchestrator.impl.executor.AsyncExecutorServiceFactory;
import net.bolbat.kit.orchestrator.impl.executor.DefaultExecutorServiceFactory;
import net.bolbat.kit.orchestrator.impl.executor.ExecutorServiceFactory;
import net.bolbat.kit.orchestrator.impl.executor.SystemExecutorServiceFactory;

/**
 * Orchestration utilities.
 * 
 * @author Alexandr Bolbat
 */
public final class ExecutionUtils {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionUtils.class);

	/**
	 * Executor for 'ASYNC' executions.
	 */
	private static final ExecutorService ASYNC_EXECUTOR = AsyncExecutorServiceFactory.getInstance().create(null);

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private ExecutionUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Get unique {@link Object} identifier.
	 * 
	 * @param obj
	 *            object instance
	 * @return {@link String}
	 */
	public static String objectId(final Object obj) {
		return Integer.toString(System.identityHashCode(obj));
	}

	/**
	 * Get unique {@link Method} identifier.
	 * 
	 * @param obj
	 *            object instance
	 * @param method
	 *            object method instance
	 * @return {@link String}
	 */
	public static String methodId(final Object obj, final Method method) {
		return ExecutionUtils.objectId(obj) + "-" + ExecutionUtils.objectId(method);
	}

	/**
	 * Create {@link Callable}.
	 * 
	 * @param impl
	 *            implementation
	 * @param method
	 *            method
	 * @param args
	 *            method arguments
	 * @return {@link Callable}
	 */
	public static Callable<Object> createCallable(final Object impl, final Method method, final Object[] args) {
		checkArgument(impl != null, "impl argument is null");
		checkArgument(method != null, "method argument is null");

		return new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				try {
					return method.invoke(impl, args);
				} catch (final InvocationTargetException e) {
					final Throwable cause = e.getCause();
					if (cause != null && cause instanceof Exception)
						throw (Exception) cause;

					throw e;
				}
			}
		};
	}

	/**
	 * Invoke on executor.
	 * 
	 * @param method
	 *            execution method
	 * @param args
	 *            method arguments
	 * @param info
	 *            {@link ExecutionInfo}
	 * @return invocation result
	 * @throws Exception
	 */
	public static Object invoke(final Object instance, final Method method, final Object[] args, final ExecutionInfo info) throws Exception {
		final LimitsConfig limitsConf = info.getActualLimitsConfig();
		final boolean controlConcurrency = limitsConf.getConcurrent() != OrchestrationConstants.CONCURRENT_LIMIT;
		try {
			if (controlConcurrency && limitsConf.getConcurrent() < info.getActualExecutions().incrementAndGet())
				throw new ConcurrentOverflowException(info);

			final Callable<Object> callable = ExecutionCaches.getCallable(instance, method, args);
			final Mode mode = info.getConfig().getModeConfig().getMode();

			// mode 'SYNC'
			if (mode == Mode.SYNC)
				return ExecutionUtils.invoke(callable, limitsConf.getTime(), limitsConf.getTimeUnit(), info.getActualExecutor());

			// this restriction will be removed when ASYNC support will be implemented for methods with any return type
			if (method.getReturnType() != void.class) {
				final StringBuilder sb = new StringBuilder("ASYNC mode currently supported only for 'void' methods");
				sb.append(", invoking in SYNC mode method[").append(method).append("] from[").append(instance.getClass()).append("]");
				LOGGER.warn(sb.toString());
				return ExecutionUtils.invoke(callable, limitsConf.getTime(), limitsConf.getTimeUnit(), info.getActualExecutor());
			}

			// mode 'ASYNC'
			ExecutionUtils.invokeAsync(callable, limitsConf.getTime(), limitsConf.getTimeUnit(), info.getActualExecutor());
			return null;
		} catch (final RejectedExecutionException e) {
			throw new ExecutorOverflowException(info);
		} catch (final TimeoutException e) {
			throw new ExecutionTimeoutException(info);
		} finally {
			if (controlConcurrency)
				info.getActualExecutions().decrementAndGet();
		}
	}

	/**
	 * Invoke callable on {@link ExecutorService} in 'ASYNC' thread without blocking current thread.<br>
	 * All exceptions would be logged with error log level.
	 * 
	 * @param callable
	 *            {@link Callable}
	 * @param time
	 *            maximum execution time
	 * @param timeUnit
	 *            maximum execution time unit
	 * @param executor
	 *            {@link ExecutorService}
	 */
	public static <T> void invokeAsync(final Callable<T> callable, final int time, final TimeUnit timeUnit, final ExecutorService executor) {
		ASYNC_EXECUTOR.submit(new Runnable() {
			@Override
			public void run() {
				try {
					ExecutionUtils.invoke(callable, time, timeUnit, executor);
				} catch (final Exception e) {
					LOGGER.error("invokeAsync(callable, " + time + ", " + timeUnit + ", executor) error", e);
				}
			}
		});
	}

	/**
	 * Invoke callable on {@link ExecutorService}.<br>
	 * {@link RejectedExecutionException} will be thrown if executor couldn't accept task for execution.<br>
	 * {@link TimeoutException} will be thrown if maximum time to wait is reached.<br>
	 * {@link InterruptedException} will be thrown if task execution is interrupted.
	 * 
	 * @param callable
	 *            {@link Callable}
	 * @param time
	 *            maximum execution time
	 * @param timeUnit
	 *            maximum execution time unit
	 * @param executor
	 *            {@link ExecutorService}
	 * @return <T>
	 * @throws Exception
	 */
	public static <T> T invoke(final Callable<T> callable, final int time, final TimeUnit timeUnit, final ExecutorService executor) throws Exception {
		checkArgument(callable != null, "callable argument is null");
		checkArgument(timeUnit != null, "timeUnit argument is null");
		checkArgument(executor != null, "executor argument is null");

		Future<T> future = null;
		try {
			future = executor.submit(callable);
			return time > 0 ? future.get(time, timeUnit) : future.get();
		} catch (final RejectedExecutionException | InterruptedException | TimeoutException e) {
			if (future != null)
				future.cancel(true);

			throw e;
		} catch (final ExecutionException e) {
			final Throwable cause = e.getCause();
			if (cause != null && cause instanceof Exception)
				throw (Exception) cause;

			throw e;
		}
	}

	/**
	 * Create {@link ExecutorService}.
	 * 
	 * @param config
	 *            {@link ExecutorConfig}
	 * @param nameFormatArgs
	 *            thread name format arguments
	 * @return {@link ExecutorService}
	 */
	public static ExecutorService create(final ExecutorConfig config, final Object... nameFormatArgs) {
		checkArgument(config != null, "config argument is null");

		final Class<? extends ExecutorServiceFactory> factory = config.getFactory();
		if (DefaultExecutorServiceFactory.class == factory)
			return DefaultExecutorServiceFactory.getInstance().create(config, nameFormatArgs);

		if (SystemExecutorServiceFactory.class == factory)
			return SystemExecutorServiceFactory.getInstance().create(null); // config is ignored

		if (AsyncExecutorServiceFactory.class == factory)
			return AsyncExecutorServiceFactory.getInstance().create(null); // config is ignored

		try {
			return factory.newInstance().create(config, nameFormatArgs); // additional factory instance caching can be implemented here
		} catch (final InstantiationException | IllegalAccessException e) {
			throw new OrchestrationException("Couldn't instantiate ExecutorServiceFactory[" + factory + "]", e);
		}
	}

	/**
	 * Terminate {@link ExecutorService} using {@code ExecutorService.shutdownNow()}.
	 * 
	 * @param service
	 *            service
	 */
	public static void terminate(final ExecutorService service) {
		shutdown(service, true, false, 1, TimeUnit.MILLISECONDS);
	}

	/**
	 * Shutdown {@link ExecutorService} using {@code ExecutorService.shutdown()}.
	 * 
	 * @param service
	 *            service
	 * @param wait
	 *            is need to wait
	 * @param timeout
	 *            waiting timeout
	 * @param unit
	 *            waiting timeout unit
	 */
	public static void shutdown(final ExecutorService service, final boolean wait, final long timeout, final TimeUnit unit) {
		shutdown(service, false, wait, timeout, unit);
	}

	/**
	 * Shutdown {@link ExecutorService}.<br>
	 * {@code ExecutorService.shutdownNow()} will be used if:<br>
	 * - {@code terminate} is <code>false</code>;<br>
	 * - {@code wait} is <code>true</code>;<br>
	 * - {@code timeout} is reached.
	 * 
	 * @param service
	 *            service
	 * @param terminate
	 *            {@code ExecutorService.shutdownNow()} will be used instead of {@code ExecutorService.shutdown()} and waiting will be skipped
	 * @param wait
	 *            is need to wait
	 * @param timeout
	 *            waiting timeout
	 * @param unit
	 *            waiting timeout unit
	 */
	public static void shutdown(final ExecutorService service, final boolean terminate, final boolean wait, final long timeout, final TimeUnit unit) {
		checkArgument(service != null, "service argument is null");
		checkArgument(unit != null, "unit argument is null");

		if (terminate) {
			service.shutdownNow();
			return;
		}

		service.shutdown();
		if (service.isTerminated() || !wait)
			return;

		try {
			service.awaitTermination(timeout, unit);
		} catch (final InterruptedException e) {
			LOGGER.warn("service[" + service + "] awaitTermination(" + timeout + ", " + unit + ") is interrupted", e);
		} finally {
			if (!service.isTerminated())
				service.shutdownNow();
		}
	}

}
