package net.bolbat.kit.scheduler.task.queue;

import net.bolbat.kit.scheduler.SchedulerConfigurationType;
import net.bolbat.kit.scheduler.Task;
import net.bolbat.kit.scheduler.TaskConfiguration;
import net.bolbat.kit.scheduler.TaskParameters;

/**
 * Implementation of the {@link TaskConfiguration} for {@link QueueTask}.
 *
 * @author ivanbatura
 */
public class QueueTaskConfiguration implements TaskConfiguration {

	/**
	 * Generated serial UID.
	 */
	private static final long serialVersionUID = 8873062486297848831L;

	/**
	 * {@link net.bolbat.kit.scheduler.TaskParameters}.
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
		if (parameters == null)
			parameters = new TaskParameters();
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
}
