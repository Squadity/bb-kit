package net.bolbat.kit.orchestrator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import net.bolbat.kit.orchestrator.OrchestrationConstants;
import net.bolbat.utils.annotation.Audience;
import net.bolbat.utils.annotation.Stability;

/**
 * Orchestration limits configuration.
 * 
 * @author Alexandr Bolbat
 */
@Audience.Public
@Stability.Evolving
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface OrchestrationLimits {

	/**
	 * Execution time limit.
	 * 
	 * @return <code>long</code>
	 */
	long time() default OrchestrationConstants.TIME_LIMIT;

	/**
	 * Execution time limit unit.
	 * 
	 * @return {@link TimeUnit}
	 */
	TimeUnit timeUnit() default TimeUnit.MILLISECONDS; // couldn't use from CallConstants due to annotations restrictions

	/**
	 * Concurrent executions limit.
	 * 
	 * @return <code>int</code>
	 */
	int concurrent() default OrchestrationConstants.CONCURRENT_LIMIT;

}
