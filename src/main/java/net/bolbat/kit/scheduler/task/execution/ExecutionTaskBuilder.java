package net.bolbat.kit.scheduler.task.execution;

import net.bolbat.kit.scheduler.SchedulerConfigurationType;
import net.bolbat.kit.scheduler.TaskBuilder;
import net.bolbat.kit.scheduler.TaskConfiguration;
import net.bolbat.kit.scheduler.TaskParameters;

/**
 * Builder for {@link ExecutionTask}.
 *
 * @author ivanbatura
 */
public class ExecutionTaskBuilder implements TaskBuilder {

	/**
	 * Class extends {@link ExecutionProcessor}.
	 */
	private Class<? extends ExecutionProcessor> processorClass;

	/**
	 * Config file name.
	 */
	private String configuration;

	/**
	 * {@link SchedulerConfigurationType}.
	 */
	private SchedulerConfigurationType configurationType;

	/**
	 * {@link TaskParameters}.
	 */
	private TaskParameters parameters = new TaskParameters();

	/**
	 * Set {@code processor}.
	 *
	 * @param aProcessorClass
	 * 		Class of {@link ExecutionProcessor}
	 * @return {@link ExecutionTaskBuilder}
	 */
	public ExecutionTaskBuilder processorClass(final Class<? extends ExecutionProcessor> aProcessorClass) {
		this.processorClass = aProcessorClass;
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
	public ExecutionTaskBuilder configuration(final String aConfiguration) {
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
	public ExecutionTaskBuilder configurationType(final SchedulerConfigurationType aConfigurationType) {
		this.configurationType = aConfigurationType;
		return this;
	}

	/**
	 * Set {@code parameters}.
	 *
	 * @param aTaskParameters
	 * 		{@link TaskParameters}
	 * @return {@link ExecutionTaskBuilder}}
	 */
	public ExecutionTaskBuilder parameters(final TaskParameters aTaskParameters) {
		if (aTaskParameters != null)
			this.parameters = aTaskParameters;
		return this;
	}

	@Override
	public TaskConfiguration build() {
		return new ExecutionTaskConfiguration(processorClass, parameters, configuration, configurationType);
	}
}
