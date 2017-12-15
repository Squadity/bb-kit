package net.bolbat.kit.scheduler.task.queue;

import net.bolbat.kit.scheduler.SchedulerConfigurationType;
import net.bolbat.kit.scheduler.Task;
import net.bolbat.kit.scheduler.TaskConfiguration;
import net.bolbat.kit.scheduler.TaskParameters;

/**
 * Implementation of the {@link TaskConfiguration} for {@link QueueTask}.
 *
 * @param <T>
 * 		parameter
 * @author ivanbatura
 */
public class QueueTaskConfiguration<T> implements TaskConfiguration {

	/**
	 * Generated serial UID.
	 */
	private static final long serialVersionUID = 8873062486297848831L;

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
	 * {@link TaskParameters}.
	 */
	private TaskParameters parameters;

	/**
	 * Configuration name.
	 */
	private String configurationName;

	/**
	 * {@link net.bolbat.kit.scheduler.SchedulerConfigurationType}.
	 */
	private SchedulerConfigurationType configurationType;

	/**
	 * Default constructor.
	 */
	public QueueTaskConfiguration() {
	}

	/**
	 * Constructor.
	 *
	 * @param loaderClass
	 * 		Class extends the {@link QueueLoader}
	 * @param processorClass
	 * 		Class extends {@link QueueProcessor}
	 * @param processingMode
	 * 		{@link ProcessingMode}
	 * @param parameters
	 * 		{@link TaskParameters}
	 * @param configurationName
	 * 		configuration name
	 * @param configurationType
	 * 		{@link SchedulerConfigurationType }
	 */
	public QueueTaskConfiguration(Class<? extends QueueLoader<T>> loaderClass, Class<? extends QueueProcessor<T>> processorClass, ProcessingMode processingMode, TaskParameters parameters, String configurationName, SchedulerConfigurationType configurationType) {
		this.loaderClass = loaderClass;
		this.processorClass = processorClass;
		this.processingMode = processingMode;
		this.parameters = parameters != null ? parameters : new TaskParameters();
		this.configurationName = configurationName;
		this.configurationType = configurationType;
	}

	public void setLoaderClass(Class<? extends QueueLoader<T>> loaderClass) {
		this.loaderClass = loaderClass;
	}

	public void setProcessorClass(Class<? extends QueueProcessor<T>> processorClass) {
		this.processorClass = processorClass;
	}

	public void setProcessingMode(ProcessingMode processingMode) {
		this.processingMode = processingMode;
	}

	public void setParameters(TaskParameters parameters) {
		this.parameters = parameters;
	}

	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}

	public void setConfigurationType(SchedulerConfigurationType configurationType) {
		this.configurationType = configurationType;
	}

	@Override
	public Class<? extends Task> getJobClass() {
		return QueueTask.class;
	}

	@Override
	public TaskParameters getParameters() {
		return parameters;
	}

	@Override
	public String getConfig() {
		return configurationName;
	}

	@Override
	public SchedulerConfigurationType getConfigType() {
		return configurationType;
	}

	public Class<? extends QueueLoader<T>> getLoaderClass() {
		return loaderClass;
	}

	public Class<? extends QueueProcessor<T>> getProcessorClass() {
		return processorClass;
	}

	public ProcessingMode getProcessingMode() {
		return processingMode;
	}

	public String getConfigurationName() {
		return configurationName;
	}

	public SchedulerConfigurationType getConfigurationType() {
		return configurationType;
	}
}
