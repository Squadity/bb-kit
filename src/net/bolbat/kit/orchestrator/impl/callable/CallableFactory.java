package net.bolbat.kit.orchestrator.impl.callable;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * Factory for {@link Callable}.
 * 
 * @author Alexandr Bolbat
 */
public interface CallableFactory {

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
	Callable<Object> create(Object impl, Method method, Object[] args);

}
