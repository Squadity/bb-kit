package net.bolbat.kit.orchestrator.impl;

import static net.bolbat.utils.lang.Validations.checkArgument;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import net.bolbat.kit.orchestrator.OrchestrationConfig;

/**
 * Caches required for orchestration.
 * 
 * @author Alexandr Bolbat
 */
public final class ExecutionCaches {

	/**
	 * {@link ExecutionInfo} cache.
	 */
	private static final Map<String, ExecutionInfo> INFOS = new ConcurrentHashMap<>();

	/**
	 * {@link ExecutorService} cache.
	 */
	private static final Map<String, ExecutorService> EXECUTORS = new ConcurrentHashMap<>();

	/**
	 * {@link Callable} cache.
	 */
	private static final Map<Method, Callable<Object>> CALLABLES = new ConcurrentHashMap<>();

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private ExecutionCaches() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Get cached {@link ExecutionInfo}.
	 * 
	 * @param id
	 *            execution identifier
	 * @return {@link OrchestrationConfig} instance or <code>null</code>
	 */
	public static ExecutionInfo getInfo(final String id) {
		checkArgument(id != null, "id argument is null");

		return INFOS.get(id);
	}

	/**
	 * Cache {@link ExecutionInfo}.
	 * 
	 * @param id
	 *            execution identifier
	 * @param info
	 *            {@link ExecutionInfo} instance
	 */
	public static void cacheInfo(final String id, final ExecutionInfo info) {
		checkArgument(id != null, "id argument is null");
		checkArgument(info != null, "info argument is empty");

		INFOS.put(id, info);
	}

	/**
	 * Get cached {@link ExecutorService}.
	 * 
	 * @param id
	 *            execution identifier
	 * @return {@link ExecutorService} instance or <code>null</code>
	 */
	public static ExecutorService getExecutor(final String id) {
		checkArgument(id != null, "id argument is null");

		return EXECUTORS.get(id);
	}

	/**
	 * Cache {@link ExecutorService}.<br>
	 * Previously associated {@link ExecutorService} would be shut down.
	 * 
	 * @param id
	 *            execution identifier
	 * @param service
	 *            {@link ExecutorService} instance
	 */
	public static void cacheExecutor(final String id, final ExecutorService service) {
		checkArgument(id != null, "id argument is null");
		checkArgument(service != null, "service argument is empty");

		final ExecutorService toShutdown = EXECUTORS.put(id, service);
		shutdownExecutor(toShutdown);
	}

	/**
	 * Shut down {@link ExecutorService}.
	 * 
	 * @param id
	 *            execution identifier
	 */
	public static void shutdownExecutor(final String id) {
		final ExecutorService toShutdown = EXECUTORS.remove(id);
		shutdownExecutor(toShutdown);
	}

	/**
	 * Shut down {@link ExecutorService}.
	 * 
	 * @param toShutdown
	 *            {@link ExecutorService} instance
	 */
	public static void shutdownExecutor(final ExecutorService toShutdown) {
		if (toShutdown != null)
			ExecutionUtils.shutdown(toShutdown, false, 0, TimeUnit.MILLISECONDS);
	}

	/**
	 * Get {@link Callable}, callables for methods without arguments would be cached.
	 * 
	 * @param impl
	 *            implementation
	 * @param method
	 *            method
	 * @param args
	 *            method arguments
	 * @return {@link Callable}
	 */
	public static Callable<Object> getCallable(final Object impl, final Method method, final Object[] args) {
		checkArgument(impl != null, "impl argument is null");
		checkArgument(method != null, "method argument is null");

		if (method.getParameterTypes().length > 0)
			return ExecutionUtils.createCallable(impl, method, args);

		Callable<Object> result = CALLABLES.get(method);
		if (result == null) {
			result = ExecutionUtils.createCallable(impl, method, args);
			CALLABLES.put(method, result);
		}

		return result;
	}

	/**
	 * Tear down {@link ExecutionCaches}.
	 */
	public static synchronized void tearDown() {
		// executions info's cache
		for (final String id : INFOS.keySet()) {
			final ExecutionInfo removed = INFOS.remove(id);
			removed.unregisterFromConfigurationChanges();
		}

		// executors cache
		for (final String id : EXECUTORS.keySet()) {
			final ExecutorService removed = EXECUTORS.remove(id);
			ExecutionUtils.terminate(removed);
		}

		// callables cache
		CALLABLES.clear();
	}

}
