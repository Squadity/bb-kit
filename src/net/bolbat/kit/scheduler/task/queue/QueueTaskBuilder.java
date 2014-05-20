package net.bolbat.kit.scheduler.task.queue;

import net.bolbat.kit.scheduler.SchedulerConfigurationType;
import net.bolbat.kit.scheduler.TaskBuilder;
import net.bolbat.kit.scheduler.TaskConfiguration;
import net.bolbat.kit.scheduler.TaskParameters;

/**
 * Builder for {@link QueueTask}.
 *
 * @param <T>
 * 		type for loader and processor
 * @author ivanbatura
 */
public class QueueTaskBuilder<T> implements TaskBuilder {

	/**
	 * {@link QueueLoader}.
	 */
	private QueueLoader<T> loader;

	/**
	 * {@link QueueProcessor}.
	 */
	private QueueProcessor<T> processor;

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
	 * {@link TaskParameters} for.
	 */
	private TaskParameters loaderParameters = new TaskParameters();

	/**
	 * {@link TaskParameters}.
	 */
	private TaskParameters processorParameters = new TaskParameters();

	/**
	 * Set {@code loader}.
	 *
	 * @param aLoader
	 * 		{@link QueueLoader}
	 * @return {@link QueueTaskBuilder}
	 */
	public QueueTaskBuilder<T> loader(QueueLoader<T> aLoader) {
		this.loader = aLoader;
		return this;
	}

	/**
	 * Set {@code processor}.
	 *
	 * @param aProcessor
	 * 		{@link QueueProcessor}
	 * @return {@link QueueTaskBuilder}
	 */
	public QueueTaskBuilder<T> processor(QueueProcessor<T> aProcessor) {
		this.processor = aProcessor;
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
	 * Set {@code parameters} for loader.
	 *
	 * @param aTaskParameters
	 * 		{@link TaskParameters}
	 * @return {@link QueueTaskBuilder}}
	 */
	public QueueTaskBuilder<T> loaderParameters(final TaskParameters aTaskParameters) {
		if (aTaskParameters != null)
			this.loaderParameters = aTaskParameters;
		return this;
	}

	/**
	 * Set {@code parameters} for processor.
	 *
	 * @param aTaskParameters
	 * 		{@link TaskParameters}
	 * @return {@link QueueTaskBuilder}}
	 */
	public QueueTaskBuilder<T> processorParameters(final TaskParameters aTaskParameters) {
		if (aTaskParameters != null)
			this.processorParameters = aTaskParameters;
		return this;
	}


	@Override
	public TaskConfiguration build() {
		QueueTaskConfiguration taskConfiguration = new QueueTaskConfiguration();
		taskConfiguration.setConfigurationName(configuration);
		taskConfiguration.setConfigurationType(configurationType);
		taskConfiguration.getParameters().put(QueueConstants.LOADER, loader);
		taskConfiguration.getParameters().put(QueueConstants.PROCESSOR, processor);
		taskConfiguration.getParameters().put(QueueConstants.LOADER_PARAMETERS, loaderParameters);
		taskConfiguration.getParameters().put(QueueConstants.PROCESSOR_PARAMETERS, processorParameters);
		taskConfiguration.getParameters().put(QueueConstants.PROCESSING_MODE, processingMode);
		return taskConfiguration;
	}

}
