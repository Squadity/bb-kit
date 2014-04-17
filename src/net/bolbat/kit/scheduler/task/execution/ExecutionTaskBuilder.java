package net.bolbat.kit.scheduler.task.execution;

import net.bolbat.kit.scheduler.SchedulerConfigurationType;
import net.bolbat.kit.scheduler.TaskBuilder;
import net.bolbat.kit.scheduler.TaskConfiguration;

/**
 * Builder for {@link ExecutionTask}.
 *
 * @author ivanbatura
 */
public class ExecutionTaskBuilder implements TaskBuilder {

	/**
	 * {@link net.bolbat.kit.scheduler.task.queue.QueueProcessor}.
	 */
	private ExecutionProcessor processor;

	/**
	 * Config file name.
	 */
	private String configuration;

	/**
	 * {@link net.bolbat.kit.scheduler.SchedulerConfigurationType}.
	 */
	private SchedulerConfigurationType configurationType;

	/**
	 * Set {@code processor}.
	 *
	 * @param aProcessor
	 * 		{@link ExecutionProcessor}
	 * @return {@link ExecutionTaskBuilder}
	 */
	public ExecutionTaskBuilder processor(ExecutionProcessor aProcessor) {
		this.processor = aProcessor;
		return this;
	}

	/**
	 * Set {@code configuration}.
	 *
	 * @param aConfiguration
	 * 		configuration file name
	 * 		can be NULL - default will be used
	 * @return {@link ExecutionTaskBuilder}
	 */
	public ExecutionTaskBuilder configuration(String aConfiguration) {
		this.configuration = aConfiguration;
		return this;
	}

	/**
	 * Set {@code configuration}.
	 *
	 * @param aConfigurationType
	 * 		{@link SchedulerConfigurationType}
	 * 		can be NULL - default will be used
	 * @return {@link ExecutionTaskBuilder}
	 */
	public ExecutionTaskBuilder configurationType(SchedulerConfigurationType aConfigurationType) {
		this.configurationType = aConfigurationType;
		return this;
	}

	@Override
	public TaskConfiguration build() {
		ExecutionTaskConfiguration taskConfiguration = new ExecutionTaskConfiguration();
		taskConfiguration.getParameters().put(ExecutionConstants.PROCESSOR, processor);
		taskConfiguration.setConfigurationName(configuration);
		taskConfiguration.setConfigurationType(configurationType);
		return taskConfiguration;
	}
}
