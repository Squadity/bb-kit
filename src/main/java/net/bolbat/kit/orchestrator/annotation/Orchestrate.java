package net.bolbat.kit.orchestrator.annotation;

import static net.bolbat.utils.lang.StringUtils.EMPTY;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.bolbat.kit.orchestrator.Orchestrator;
import net.bolbat.utils.annotation.Audience;
import net.bolbat.utils.annotation.Stability;

/**
 * Enable/Disable orchestration.
 * 
 * @author Alexandr Bolbat
 */
@Audience.Public
@Stability.Evolving
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface Orchestrate {

	/**
	 * Is orchestration enabled.
	 * 
	 * @return <code>boolean</code>
	 */
	boolean value() default true;

	/**
	 * Configuration file name, overrides {@link Orchestrator} annotations values.
	 * 
	 * @return {@link String}
	 */
	String configName() default EMPTY;

}
