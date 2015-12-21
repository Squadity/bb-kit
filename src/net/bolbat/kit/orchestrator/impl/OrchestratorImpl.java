package net.bolbat.kit.orchestrator.impl;

import static net.bolbat.utils.lang.Validations.checkArgument;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import net.bolbat.kit.orchestrator.Orchestrator;
import net.bolbat.utils.reflect.ClassUtils;

/**
 * {@link Orchestrator} implementation.
 * 
 * @author Alexandr Bolbat
 */
public class OrchestratorImpl implements Orchestrator {

	@Override
	public <T> T init(final T target) {
		checkArgument(target != null, "target argument is null");

		return init(target, ClassUtils.getAllInterfaces(target.getClass()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T init(final T target, final Class<?>... interfaces) {
		checkArgument(target != null, "target argument is null");
		checkArgument(interfaces != null && interfaces.length > 0, "interfaces argument is empty");

		final ClassLoader classLoader = target.getClass().getClassLoader();
		final InvocationHandler callHandler = new ExecutionHandler(target, interfaces);
		return (T) Proxy.newProxyInstance(classLoader, interfaces, callHandler);
	}

	@Override
	public <T> T invoke(final Callable<T> callable, final int time, final TimeUnit timeUnit, final ExecutorService executor) throws Exception {
		checkArgument(callable != null, "callable argument is null");
		checkArgument(time > 0, "time argument must be more than 0");
		checkArgument(timeUnit != null, "timeUnit argument is null");
		checkArgument(executor != null, "executor argument is null");

		return ExecutionUtils.invoke(callable, time, timeUnit, executor);
	}

}
