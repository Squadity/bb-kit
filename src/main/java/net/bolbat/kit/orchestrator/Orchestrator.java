package net.bolbat.kit.orchestrator;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import net.bolbat.utils.annotation.Audience;
import net.bolbat.utils.annotation.Stability;

/**
 * API for controlled concurrent execution.
 * 
 * @author Alexandr Bolbat
 */
@Audience.Public
@Stability.Evolving
public interface Orchestrator {

	/**
	 * Initialize, all interfaces would be supported.
	 * 
	 * @param target
	 *            object
	 * @return orchestrable proxy
	 */
	<T> T init(T target);

	/**
	 * Initialize.
	 * 
	 * @param target
	 *            object
	 * @param interfaces
	 *            supported interfaces
	 * @return orchestrable proxy
	 */
	<T> T init(T target, Class<?>... interfaces);

	/**
	 * Invoke callable.
	 * 
	 * @param callable
	 *            execution call
	 * @param time
	 *            execution time limit
	 * @param timeUnit
	 *            execution time limit unit
	 * @param executor
	 *            {@link ExecutorService}
	 * @return T
	 */
	<T> T invoke(Callable<T> callable, int time, TimeUnit timeUnit, ExecutorService executor) throws Exception;

}
