package net.bolbat.kit.orchestrator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import net.bolbat.kit.orchestrator.OrchestrationConstants;
import net.bolbat.kit.orchestrator.impl.executor.DefaultExecutorServiceFactory;
import net.bolbat.kit.orchestrator.impl.executor.ExecutorServiceFactory;
import net.bolbat.utils.annotation.Audience;
import net.bolbat.utils.annotation.Stability;

/**
 * Orchestration {@link ExecutorService} configuration.
 * 
 * @author Alexandr Bolbat
 */
@Audience.Public
@Stability.Evolving
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface OrchestrationExecutor {

	/**
	 * {@link ExecutorService} factory class.
	 * 
	 * @return {@link ExecutorServiceFactory} implementation
	 */
	Class<? extends ExecutorServiceFactory> factory() default DefaultExecutorServiceFactory.class;

	/**
	 * {@link ExecutorService} core pool size.
	 * 
	 * @return <code>int</code>
	 */
	int coreSize() default OrchestrationConstants.POOL_CORE_SIZE;

	/**
	 * {@link ExecutorService} max pool size.
	 * 
	 * @return <code>int</code>
	 */
	int maxSize() default OrchestrationConstants.POOL_MAX_SIZE;

	/**
	 * {@link ExecutorService} pool queue size.
	 * 
	 * @return <code>int</code>
	 */
	int queueSize() default OrchestrationConstants.POOL_QUEUE_SIZE;

	/**
	 * {@link ExecutorService} pool keep alive time.<br>
	 * When the number of threads is greater than the core, this is the maximum time that excess idle threads will wait for new tasks before terminating.
	 * 
	 * @return <code>long</code>
	 */
	long keepAlive() default OrchestrationConstants.POOL_KEEP_ALIVE;

	/**
	 * {@link ExecutorService} pool keep alive time unit.
	 * 
	 * @return {@link TimeUnit}
	 */
	TimeUnit keepAliveUnit() default TimeUnit.SECONDS; // couldn't use from CallConstants due to annotations restrictions

	/**
	 * {@link ExecutorService} thread name format.
	 * 
	 * @return {@link String}
	 */
	String nameFormat() default OrchestrationConstants.THREAD_NAME_FORMAT;

}
