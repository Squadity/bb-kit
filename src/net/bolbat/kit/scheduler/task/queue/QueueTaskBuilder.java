package net.bolbat.kit.scheduler.task.queue;

import net.bolbat.kit.scheduler.SchedulerConfigurationType;
import net.bolbat.kit.scheduler.TaskBuilder;
import net.bolbat.kit.scheduler.TaskConfiguration;

/**
 * Builder for {@link QueueTask}.
 *
 * @author ivanbatura
 */
public class QueueTaskBuilder implements TaskBuilder {

	/**
	 * {@link QueueLoader}.
	 */
	private QueueLoader loader;

	/**
	 * {@link QueueProcessor}.
	 */
	private QueueProcessor processor;

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
	 * Set {@code loader}.
	 *
	 * @param aLoader
	 * 		{@link QueueLoader}
	 * @return {@link QueueTaskBuilder}
	 */
	public QueueTaskBuilder loader(QueueLoader aLoader) {
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
	public QueueTaskBuilder processor(QueueProcessor aProcessor) {
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
	public QueueTaskBuilder processingMode(ProcessingMode aProcessingMode) {
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
	public QueueTaskBuilder configuration(String aConfiguration) {
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
	public QueueTaskBuilder configurationType(SchedulerConfigurationType aConfigurationType) {
		this.configurationType = aConfigurationType;
		return this;
	}

	@Override
	public TaskConfiguration build() {
		QueueTaskConfiguration taskConfiguration = new QueueTaskConfiguration();
		taskConfiguration.setConfigurationName(configuration);
		taskConfiguration.setConfigurationType(configurationType);
		taskConfiguration.getParameters().put(QueueConstants.LOADER, loader);
		taskConfiguration.getParameters().put(QueueConstants.PROCESSOR, processor);
		taskConfiguration.getParameters().put(QueueConstants.PROCESSING_MODE, processingMode);
		return taskConfiguration;
	}
}
