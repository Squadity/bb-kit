package net.bolbat.kit.orchestrator.impl;

import static net.bolbat.utils.lang.Validations.checkArgument;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * Caches required for orchestration.
 * 
 * @author Alexandr Bolbat
 */
public final class ExecutionCaches {

	/**
	 * {@link ExecutionInfo} cache.
	 */
	private static final Map<String, ExecutionInfo> EXECUTION_INFO = new ConcurrentHashMap<>();

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
	 * @return {@link ExecutionInfo} instance or <code>null</code>
	 */
	public static ExecutionInfo getExecutionInfo(final String id) {
		checkArgument(id != null, "id argument is null");

		return EXECUTION_INFO.get(id);
	}

	/**
	 * Cache {@link ExecutionInfo}.
	 * 
	 * @param id
	 *            execution identifier
	 * @param info
	 *            {@link ExecutionInfo} instance
	 */
	public static void cacheExecutionInfo(final String id, final ExecutionInfo info) {
		checkArgument(id != null, "id argument is null");
		checkArgument(info != null, "info argument is empty");

		EXECUTION_INFO.put(id, info);
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
	 * Cache {@link ExecutorService}.
	 * 
	 * @param id
	 *            execution identifier
	 * @param service
	 *            {@link ExecutorService} instance
	 * @return old {@link ExecutorService} instance or <code>null</code>
	 */
	public static ExecutorService cacheExecutor(final String id, final ExecutorService service) {
		checkArgument(id != null, "id argument is null");
		checkArgument(service != null, "service argument is empty");

		return EXECUTORS.put(id, service);
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
		// execution info cache
		for (final String id : EXECUTION_INFO.keySet())
			EXECUTION_INFO.remove(id).unregisterFromConfiguration();

		// executors cache
		for (final String id : EXECUTORS.keySet())
			ExecutionUtils.terminate(EXECUTORS.remove(id));

		// callables cache
		CALLABLES.clear();
	}

}
