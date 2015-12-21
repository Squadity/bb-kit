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
	 */
	Class<? extends ExecutorServiceFactory> factory() default DefaultExecutorServiceFactory.class;

	/**
	 * {@link ExecutorService} core pool size.
	 */
	int coreSize() default OrchestrationConstants.POOL_CORE_SIZE;

	/**
	 * {@link ExecutorService} max pool size.
	 */
	int maxSize() default OrchestrationConstants.POOL_MAX_SIZE;

	/**
	 * {@link ExecutorService} pool queue size.
	 */
	int queueSize() default OrchestrationConstants.POOL_QUEUE_SIZE;

	/**
	 * {@link ExecutorService} pool keep alive time.<br>
	 * When the number of threads is greater than the core, this is the maximum time that excess idle threads will wait for new tasks before terminating.
	 */
	long keepAlive() default OrchestrationConstants.POOL_KEEP_ALIVE;

	/**
	 * {@link ExecutorService} pool keep alive time unit.
	 */
	TimeUnit keepAliveUnit() default TimeUnit.SECONDS; // couldn't use from CallConstants due to annotations restrictions

	/**
	 * {@link ExecutorService} thread name format.
	 */
	String nameFormat() default OrchestrationConstants.THREAD_NAME_FORMAT;

}
