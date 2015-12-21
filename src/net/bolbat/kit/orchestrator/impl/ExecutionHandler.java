package net.bolbat.kit.orchestrator.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.bolbat.kit.orchestrator.OrchestrationConfig;
import net.bolbat.kit.orchestrator.OrchestrationConfig.LimitsConfig;
import net.bolbat.kit.orchestrator.OrchestrationConstants;
import net.bolbat.kit.orchestrator.annotation.Orchestrate;
import net.bolbat.kit.orchestrator.annotation.OrchestrationExecutor;
import net.bolbat.kit.orchestrator.annotation.OrchestrationLimits;
import net.bolbat.kit.orchestrator.exception.ConcurrentOverflowException;
import net.bolbat.kit.orchestrator.exception.ExecutionTimeoutException;
import net.bolbat.kit.orchestrator.exception.ExecutorOverflowException;
import net.bolbat.utils.concurrency.lock.IdBasedLock;
import net.bolbat.utils.concurrency.lock.IdBasedLockManager;
import net.bolbat.utils.concurrency.lock.SafeIdBasedLockManager;
import net.bolbat.utils.lang.ToStringUtils;

/**
 * Orchestration execution handler implementation.
 * 
 * @author Alexandr Bolbat
 */
public class ExecutionHandler implements InvocationHandler {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionHandler.class);

	/**
	 * {@link IdBasedLockManager} instance.
	 */
	private final IdBasedLockManager<String> lockManager = new SafeIdBasedLockManager<>();

	/**
	 * Cached method execution identifiers.
	 */
	private final ConcurrentMap<Method, String> methodIds = new ConcurrentHashMap<>();

	/**
	 * Cached execution info's states.
	 */
	private final ConcurrentMap<String, InfoState> infos = new ConcurrentHashMap<>();

	/**
	 * Implementation instance.
	 */
	private final Object impl;

	/**
	 * Supported interfaces (reserved for the future).
	 */
	@SuppressWarnings("unused")
	private final Class<?>[] interfaces;

	/**
	 * Execution identifier for configuration based on implementation type data.
	 */
	private final String implExecutionId;

	/**
	 * Default constructor.
	 * 
	 * @param aImpl
	 *            implementation instance
	 * @param aInterfaces
	 *            supported interfaces
	 */
	public ExecutionHandler(final Object aImpl, final Class<?>[] aInterfaces) {
		this.impl = aImpl;
		this.interfaces = aInterfaces;
		this.implExecutionId = ExecutionUtils.objectId(impl);
	}

	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		final String executionId = resolveId(method);

		// PHASE-1 - optimized orchestration resolving (from local cache)

		final InfoState state = infos.get(executionId);
		if (state != null)
			switch (state) {
				case ORCHESTRATED_BY_METHOD:
					final ExecutionInfo methodInfo = resolveInfo(executionId, impl, method).getInfo();
					return invokeOnExecutor(method, args, methodInfo);
				case ORCHESTRATED_BY_CLASS:
					final ExecutionInfo classInfo = resolveInfo(implExecutionId, impl).getInfo();
					return invokeOnExecutor(method, args, classInfo);
				default:
					return method.invoke(impl, args);
			}

		// PHASE-2 - finding right way to orchestrate

		// try to orchestrate by method configuration
		ResolvedInfo resolved = resolveInfo(executionId, impl, method);
		if (resolved.getState() == ResolvedState.OK) {
			infos.put(executionId, InfoState.ORCHESTRATED_BY_METHOD);
			return invokeOnExecutor(method, args, resolved.getInfo());
		} else if (resolved.getState() == ResolvedState.DISABLED) {
			infos.put(executionId, InfoState.NOT_ORCHESTRATED);
			return method.invoke(impl, args);
		}

		// try to orchestrate by implementation configuration
		resolved = resolveInfo(implExecutionId, impl);
		if (resolved.getState() == ResolvedState.OK) {
			infos.put(executionId, InfoState.ORCHESTRATED_BY_CLASS);
			return invokeOnExecutor(method, args, resolved.getInfo());
		} else if (resolved.getState() == ResolvedState.DISABLED) {
			infos.put(executionId, InfoState.NOT_ORCHESTRATED);
			return method.invoke(impl, args);
		}

		// method is not orchestrated
		infos.put(executionId, InfoState.NOT_ORCHESTRATED);
		return method.invoke(impl, args);
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
	 * @throws Throwable
	 */
	private Object invokeOnExecutor(final Method method, final Object[] args, final ExecutionInfo info) throws Throwable {
		final Callable<Object> callable = ExecutionCaches.getCallable(impl, method, args);
		final LimitsConfig limitsConf = info.getConfig().getLimitsConfig();

		final boolean controlConcurrency = limitsConf.getConcurrent() != OrchestrationConstants.CONCURRENT_LIMIT;
		try {
			if (controlConcurrency && limitsConf.getConcurrent() < info.getCurrentExecutions().incrementAndGet())
				throw new ConcurrentOverflowException(info);

			return ExecutionUtils.invoke(callable, limitsConf.getTime(), limitsConf.getTimeUnit(), info.getExecutor());
		} catch (final RejectedExecutionException e) {
			throw new ExecutorOverflowException(info);
		} catch (final TimeoutException e) {
			throw new ExecutionTimeoutException(info);
		} finally {
			if (controlConcurrency)
				info.getCurrentExecutions().decrementAndGet();
		}
	}

	/**
	 * Resolve method unique identifier (use cache).
	 * 
	 * @param method
	 *            {@link Method}
	 * @return {@link String}
	 */
	public String resolveId(final Method method) {
		String id = methodIds.get(method);
		if (id == null) {
			id = ExecutionUtils.methodId(impl, method);
			methodIds.put(method, id);
		}

		return id;
	}

	/**
	 * Resolve execution configuration based on implementation type data.
	 * 
	 * @param id
	 *            execution identifier
	 * @param impl
	 *            implementation instance
	 * @return {@link ResolvedInfo}
	 */
	public ResolvedInfo resolveInfo(final String id, final Object impl) {
		ExecutionInfo info = ExecutionCaches.getExecutionInfo(id);
		if (info != null)
			return ResolvedInfo.ok(info);

		final IdBasedLock<String> lock = lockManager.obtainLock(id);
		lock.lock();
		try {
			info = ExecutionCaches.getExecutionInfo(id); // double check
			if (info != null)
				return ResolvedInfo.ok(info);

			// configuration resolving logic for implementation
			final Class<?> implType = impl.getClass();
			final Orchestrate orchestrate = implType.getAnnotation(Orchestrate.class);
			if (orchestrate == null)
				return ResolvedInfo.noConfiguration();
			if (!orchestrate.value())
				return ResolvedInfo.disabled();

			// try to resolve from interface???

			final OrchestrationConfig config = OrchestrationConfig.configure(orchestrate, //
					implType.getAnnotation(OrchestrationLimits.class), //
					implType.getAnnotation(OrchestrationExecutor.class));

			final String executionName = implType.getSimpleName();
			info = new ExecutionInfo(id, executionName, config);
			ExecutionCaches.cacheExecutionInfo(id, info);
		} finally {
			lock.unlock();
		}

		return ResolvedInfo.ok(info);
	}

	/**
	 * Resolve execution configuration based on implementation type and method data.
	 * 
	 * @param id
	 *            execution identifier
	 * @param impl
	 *            implementation instance
	 * @param method
	 *            execution method
	 * @return {@link ResolvedInfo}
	 */
	public ResolvedInfo resolveInfo(final String id, final Object impl, final Method method) {
		ExecutionInfo info = ExecutionCaches.getExecutionInfo(id);
		if (info != null)
			return ResolvedInfo.ok(info);

		final IdBasedLock<String> lock = lockManager.obtainLock(id);
		lock.lock();
		try {
			info = ExecutionCaches.getExecutionInfo(id); // double check
			if (info != null)
				return ResolvedInfo.ok(info);

			// configuration resolving logic for method
			final Class<?> implType = impl.getClass();
			Method implMethod = null;
			try {
				implMethod = implType.getMethod(method.getName(), method.getParameterTypes());
			} catch (final NoSuchMethodException | SecurityException e) {
				LOGGER.warn("Unable to get method[" + method + "] from impl type[" + implType + "], skipping", e);
				return ResolvedInfo.error();
			}

			final Orchestrate orchestrate = implMethod.getAnnotation(Orchestrate.class);
			if (orchestrate == null)
				return ResolvedInfo.noConfiguration();
			if (!orchestrate.value())
				return ResolvedInfo.disabled();

			// try to resolve from interface???

			final OrchestrationConfig config = OrchestrationConfig.configure(orchestrate, //
					implMethod.getAnnotation(OrchestrationLimits.class), //
					implMethod.getAnnotation(OrchestrationExecutor.class));

			final String executionName = implType.getSimpleName() + "." + ToStringUtils.toMethodName(implMethod);
			info = new ExecutionInfo(id, executionName, config);
			ExecutionCaches.cacheExecutionInfo(id, info);
		} finally {
			lock.unlock();
		}

		return ResolvedInfo.ok(info);
	}

	/**
	 * Tear down {@link ExecutionHandler} internals.
	 */
	public void tearDown() {
		synchronized (this) {
			methodIds.clear();
			infos.clear();
		}
	}

	/**
	 * Cached {@link ExecutionInfo} state.
	 * 
	 * @author Alexandr Bolbat
	 */
	public enum InfoState {

		/**
		 * Execution orchestrated by method configuration.
		 */
		ORCHESTRATED_BY_METHOD,

		/**
		 * Execution orchestrated by class configuration.
		 */
		ORCHESTRATED_BY_CLASS,

		/**
		 * Execution is not orchestrated.
		 */
		NOT_ORCHESTRATED;

	}

	/**
	 * Resolved information.
	 * 
	 * @author Alexandr Bolbat
	 */
	private static class ResolvedInfo {

		/**
		 * Cached instance for cases when state is 'NO_CONFIGURATION'.
		 */
		private static final ResolvedInfo NO_CONFIGURATION = new ResolvedInfo(ResolvedState.NO_CONFIGURATION, null);

		/**
		 * Cached instance for cases when state is 'DISABLED'.
		 */
		private static final ResolvedInfo DISABLED = new ResolvedInfo(ResolvedState.DISABLED, null);

		/**
		 * Cached instance for cases when state is 'ERROR'.
		 */
		private static final ResolvedInfo ERROR = new ResolvedInfo(ResolvedState.ERROR, null);

		/**
		 * Resolved state.
		 */
		private final ResolvedState state;

		/**
		 * Resolved {@link ExecutionInfo}.
		 */
		private final ExecutionInfo info;

		/**
		 * Default constructor.
		 * 
		 * @param aState
		 *            resolved state
		 * @param aInfo
		 *            resolved {@link ExecutionInfo}
		 */
		private ResolvedInfo(final ResolvedState aState, final ExecutionInfo aInfo) {
			this.state = aState;
			this.info = aInfo;
		}

		public ResolvedState getState() {
			return state;
		}

		public ExecutionInfo getInfo() {
			return info;
		}

		/**
		 * Resolved with state 'OK'.
		 * 
		 * @param aInfo
		 *            resolved {@link ExecutionInfo}
		 * @return {@link ResolvedInfo}
		 */
		public static ResolvedInfo ok(final ExecutionInfo aInfo) {
			return new ResolvedInfo(ResolvedState.OK, aInfo);
		}

		/**
		 * Resolved with state 'NO_CONFIGURATION'.
		 * 
		 * @return {@link ResolvedInfo}
		 */
		public static ResolvedInfo noConfiguration() {
			return NO_CONFIGURATION;
		}

		/**
		 * Resolved with state 'DISABLED'.
		 * 
		 * @return {@link ResolvedInfo}
		 */
		public static ResolvedInfo disabled() {
			return DISABLED;
		}

		/**
		 * Resolved with state 'ERROR'.
		 * 
		 * @return {@link ResolvedInfo}
		 */
		public static ResolvedInfo error() {
			return ERROR;
		}

	}

	/**
	 * Resolving states.
	 * 
	 * @author Alexandr Bolbat
	 */
	private enum ResolvedState {

		/**
		 * Execution configuration resolved.
		 */
		OK,

		/**
		 * Execution configuration not found.
		 */
		NO_CONFIGURATION,

		/**
		 * Orchestration is disabled.
		 */
		DISABLED,

		/**
		 * Resolving error.
		 */
		ERROR;

	}

}
