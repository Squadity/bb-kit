package net.bolbat.kit.orchestrator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.bolbat.utils.annotation.Audience;
import net.bolbat.utils.annotation.Stability;

/**
 * Orchestration mode.<br>
 * Currently supported for 'void' methods only.
 * 
 * @author Alexandr Bolbat
 */
@Audience.Public
@Stability.Evolving
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface OrchestrationMode {

	/**
	 * Execution mode.
	 * 
	 * @return {@link Mode}
	 */
	Mode value() default Mode.SYNC; // couldn't use from Mode.DEFAULT due to annotations restrictions

	/**
	 * Execution modes.
	 * 
	 * @author Alexandr Bolbat
	 */
	enum Mode {

		/**
		 * Synchronous.
		 */
		SYNC,

		/**
		 * Asynchronous.
		 */
		ASYNC;

		/**
		 * Default {@link Mode}.
		 */
		public static final Mode DEFAULT = Mode.SYNC;
	}

}
