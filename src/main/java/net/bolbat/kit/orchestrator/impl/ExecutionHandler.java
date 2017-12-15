package net.bolbat.kit.orchestrator.impl;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.bolbat.kit.orchestrator.OrchestrationConfig;
import net.bolbat.kit.orchestrator.annotation.Orchestrate;
import net.bolbat.kit.orchestrator.annotation.OrchestrationExecutor;
import net.bolbat.kit.orchestrator.annotation.OrchestrationLimits;
import net.bolbat.kit.orchestrator.annotation.OrchestrationMode;
import net.bolbat.kit.orchestrator.exception.OrchestrationException;
import net.bolbat.utils.concurrency.lock.IdBasedLock;
import net.bolbat.utils.concurrency.lock.IdBasedLockManager;
import net.bolbat.utils.concurrency.lock.SafeIdBasedLockManager;
import net.bolbat.utils.lang.ToStringUtils;
import net.bolbat.utils.reflect.proxy.AdvisedHandler;

/**
 * Orchestration execution handler implementation.
 * 
 * @author Alexandr Bolbat
 */
public class ExecutionHandler extends AdvisedHandler {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionHandler.class);

	/**
	 * {@link IdBasedLockManager} instance.
	 */
	private static final IdBasedLockManager<String> LOCK_MANAGER = new SafeIdBasedLockManager<>();

	/**
	 * Cached method execution identifiers.
	 */
	private final ConcurrentMap<Method, String> methodIds = new ConcurrentHashMap<>();

	/**
	 * Execution identifier for implementation instance.
	 */
	private final String instanceId;

	/**
	 * Default constructor.
	 * 
	 * @param aTarget
	 *            proxied target
	 * @param aInterfaces
	 *            proxied interfaces
	 */
	public ExecutionHandler(final Object aTarget, final Class<?>[] aInterfaces) {
		super(aTarget, aInterfaces);
		this.instanceId = ExecutionUtils.objectId(getProxiedTarget());
	}

	@Override
	// CHECKSTYLE:OFF
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		// CHECKSTYLE:ON
		final String executionId = resolveId(getProxiedTarget(), method);
		final ExecutionInfo info = resolveInstanceMethodInfo(instanceId, getProxiedTarget(), executionId, method);
		if (info.isOrchestrated())
			return ExecutionUtils.invoke(getProxiedTarget(), method, args, info);

		return method.invoke(getProxiedTarget(), args);
	}

	/**
	 * Resolve method unique identifier (use cache).
	 * 
	 * @param aInstance
	 *            instance
	 * @param aMethod
	 *            instance method
	 * @return {@link String}
	 */
	public String resolveId(final Object aInstance, final Method aMethod) {
		String id = methodIds.get(aMethod);
		if (id == null) {
			id = ExecutionUtils.methodId(aInstance, aMethod);
			methodIds.put(aMethod, id);
		}

		return id;
	}

	/**
	 * Resolve (with lazy initialization) {@link ExecutionInfo} for instance.
	 * 
	 * @param instanceId
	 *            instance execution identifier
	 * @param instance
	 *            instance
	 * @return {@link ExecutionInfo}
	 */
	protected static ExecutionInfo resolveInstanceInfo(final String instanceId, final Object instance) {
		ExecutionInfo info = ExecutionCaches.getInfo(instanceId);
		if (info != null)
			return info;

		final IdBasedLock<String> lock = LOCK_MANAGER.obtainLock(instanceId);
		lock.lock();
		try {
			info = ExecutionCaches.getInfo(instanceId); // double check
			if (info != null)
				return info;

			final Class<?> implType = instance.getClass();

			info = new ExecutionInfo();
			info.setId(instanceId);
			info.setName(implType.getSimpleName());

			final Orchestrate orchestrate = implType.getAnnotation(Orchestrate.class);
			final OrchestrationLimits limits = implType.getAnnotation(OrchestrationLimits.class);
			final OrchestrationExecutor executor = implType.getAnnotation(OrchestrationExecutor.class);

			info.setDisabled(orchestrate != null && !orchestrate.value());
			info.setOwnScope(orchestrate != null);
			info.setOwnLimits(limits != null);
			info.setOwnExecutor(executor != null);

			info.setConfig(OrchestrationConfig.configure(orchestrate, limits, executor));

			info.initActualConfiguration();
			ExecutionCaches.cacheInfo(instanceId, info);
			return info;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Resolve (with lazy initialization) {@link ExecutionInfo} for method.
	 * 
	 * @param instanceId
	 *            instance execution identifier
	 * @param instance
	 *            instance
	 * @param instanceMethodId
	 *            method execution identifier
	 * @param method
	 *            method
	 * @return {@link ExecutionInfo}
	 */
	protected static ExecutionInfo resolveInstanceMethodInfo(final String instanceId, final Object instance, final String instanceMethodId,
			final Method method) {
		ExecutionInfo info = ExecutionCaches.getInfo(instanceMethodId);
		if (info != null)
			return info;

		final IdBasedLock<String> lock = LOCK_MANAGER.obtainLock(instanceMethodId);
		lock.lock();
		try {
			info = ExecutionCaches.getInfo(instanceMethodId); // double check
			if (info != null)
				return info;

			final ExecutionInfo classInfo = resolveInstanceInfo(instanceId, instance);
			final Class<?> implType = instance.getClass();
			Method implMethod = null;
			try {
				implMethod = implType.getMethod(method.getName(), method.getParameterTypes());
			} catch (final NoSuchMethodException | SecurityException e) {
				final String message = "Unable to get method[" + method + "] from impl type[" + implType + "]";
				LOGGER.warn(message, e);
				throw new OrchestrationException(message, e);
			}

			info = new ExecutionInfo();
			info.setId(instanceMethodId);
			info.setName(implType.getSimpleName() + "." + ToStringUtils.toMethodName(implMethod));
			info.setClassInfo(classInfo);

			final Orchestrate orchestrate = implMethod.getAnnotation(Orchestrate.class);
			final OrchestrationLimits limits = implMethod.getAnnotation(OrchestrationLimits.class);
			final OrchestrationExecutor executor = implMethod.getAnnotation(OrchestrationExecutor.class);
			final OrchestrationMode mode = implMethod.getAnnotation(OrchestrationMode.class);

			info.setDisabled(orchestrate != null && !orchestrate.value());
			info.setOwnScope(orchestrate != null);
			info.setOwnLimits(limits != null);
			info.setOwnExecutor(executor != null);

			info.setConfig(OrchestrationConfig.configure(orchestrate, mode, limits, executor));

			info.initActualConfiguration();
			ExecutionCaches.cacheInfo(instanceMethodId, info);
			return info;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Tear down {@link ExecutionHandler} internals.
	 */
	public synchronized void tearDown() {
		methodIds.clear();
	}

}
