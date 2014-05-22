package net.bolbat.kit.scheduler.task.queue;

import net.bolbat.kit.scheduler.SchedulerConfigurationType;
import net.bolbat.kit.scheduler.TaskBuilder;
import net.bolbat.kit.scheduler.TaskConfiguration;
import net.bolbat.kit.scheduler.TaskParameters;

/**
 * Builder for {@link QueueTask}.
 *
 * @param <T>
 * 		type for loaderClass and processorClass
 * @author ivanbatura
 */
public class QueueTaskBuilder<T> implements TaskBuilder {

	/**
	 * {@link QueueLoader}.
	 */
	private Class<? extends QueueLoader<T>> loaderClass;

	/**
	 * Class if {@link QueueProcessor}.
	 */
	private Class<? extends QueueProcessor<T>> processorClass;

	/**
	 * {@link ProcessingMode}.
	 */
	private ProcessingMode processingMode;

	/**
	 * Config file name.
	 */
	private String configuration;

	/**
	 * {@link SchedulerConfigurationType}.
	 */
	private SchedulerConfigurationType configurationType;

	/**
	 * {@link TaskParameters} for loader and processor.
	 */
	private TaskParameters parameters = new TaskParameters();

	/**
	 * Set {@code loaderClass}.
	 *
	 * @param aLoaderClass
	 * 		class extends {@link QueueLoader}
	 * @return {@link QueueTaskBuilder}
	 */
	public QueueTaskBuilder<T> loaderClass(Class<? extends QueueLoader<T>> aLoaderClass) {
		this.loaderClass = aLoaderClass;
		return this;
	}

	/**
	 * Set {@code processorClass}.
	 *
	 * @param aProcessorClass
	 * 		Class of {@link QueueProcessor}
	 * @return {@link QueueTaskBuilder}
	 */
	public QueueTaskBuilder<T> processorClass(Class<? extends QueueProcessor<T>> aProcessorClass) {
		this.processorClass = aProcessorClass;
		return this;
	}

	/**
	 * Set {@code processingMode}.
	 *
	 * @param aProcessingMode
	 * 		{@link ProcessingMode}
	 * @return {@link QueueTaskBuilder}
	 */
	public QueueTaskBuilder<T> processingMode(ProcessingMode aProcessingMode) {
		this.processingMode = aProcessingMode;
		return this;
	}

	/**
	 * Set {@code configuration}.
	 *
	 * @param aConfiguration
	 * 		configuration file name
	 * 		can be NULL - default will be used
	 * @return {@link QueueTaskBuilder}
	 */
	public QueueTaskBuilder<T> configuration(String aConfiguration) {
		this.configuration = aConfiguration;
		return this;
	}

	/**
	 * Set {@code configuration}.
	 *
	 * @param aConfigurationType
	 * 		{@link SchedulerConfigurationType}
	 * 		can be NULL - default will be used
	 * @return {@link QueueTaskBuilder}
	 */
	public QueueTaskBuilder<T> configurationType(SchedulerConfigurationType aConfigurationType) {
		this.configurationType = aConfigurationType;
		return this;
	}

	/**
	 * Set {@code parameters} for processor and loader.
	 *
	 * @param aParameters
	 * 		{@link TaskParameters}
	 * @return {@link QueueTaskBuilder}}
	 */
	public QueueTaskBuilder<T> parameters(final TaskParameters aParameters) {
		if (aParameters != null)
			this.parameters = aParameters;
		return this;
	}


	@Override
	public TaskConfiguration build() {
		return new QueueTaskConfiguration<T>(loaderClass, processorClass, processingMode, parameters, configuration, configurationType);
	}

}
