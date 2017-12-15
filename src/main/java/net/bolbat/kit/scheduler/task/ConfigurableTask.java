package net.bolbat.kit.scheduler.task;

import java.io.Serializable;

import net.bolbat.kit.scheduler.TaskParameters;

/**
 * ConfigurableTask.
 *
 * @author ivanbatura
 */
public interface ConfigurableTask extends Serializable {
	/**
	 * Configure.
	 *
	 * @param parameters
	 * 		{@link TaskParameters}
	 */
	void configure(TaskParameters parameters);
}
