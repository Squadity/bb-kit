package net.bolbat.kit.orchestrator.impl.callable;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import net.bolbat.kit.orchestrator.impl.ExecutionCaches;

/**
 * {@link CallableFactory} default implementation.<br>
 * They create new {@link Callable} instance based on given arguments.
 * 
 * @author Alexandr Bolbat
 */
public class DefaultCallableFactory implements CallableFactory {

	/**
	 * {@link DefaultCallableFactory} instance.
	 */
	private static final DefaultCallableFactory INSTANCE = new DefaultCallableFactory();

	/**
	 * Private constructor.
	 */
	private DefaultCallableFactory() {
	}

	@Override
	public Callable<Object> create(final Object impl, final Method method, final Object[] args) {
		return ExecutionCaches.getCallable(impl, method, args);
	}

	/**
	 * Get {@link DefaultCallableFactory} instance.
	 * 
	 * @return {@link DefaultCallableFactory}
	 */
	public static DefaultCallableFactory getInstance() {
		return INSTANCE;
	}

}
