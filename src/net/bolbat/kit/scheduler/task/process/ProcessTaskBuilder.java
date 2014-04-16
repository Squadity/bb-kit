package net.bolbat.kit.scheduler.task.process;

import java.util.HashMap;
import java.util.Map;

import net.bolbat.kit.scheduler.ScheduledJob;
import net.bolbat.kit.scheduler.ScheduledTask;
import net.bolbat.kit.scheduler.ScheduledTaskBuilder;
import net.bolbat.kit.scheduler.SchedulerConfigurationType;
import net.bolbat.kit.scheduler.task.Processor;
import net.bolbat.kit.scheduler.task.ScheduledConstants;

/**
 * Builder for {@link net.bolbat.kit.scheduler.task.schedulequeue.ScheduledQueueJob}.
 *
 * @author ivanbatura
 */
public class ProcessTaskBuilder implements ScheduledTaskBuilder {

	/**
	 * {@link Processor}.
	 */
	private Processor processor;

	/**
	 * Config file name.
	 */
	private String configuration;

	/**
	 * {@link net.bolbat.kit.scheduler.SchedulerConfigurationType}.
	 */
	private SchedulerConfigurationType configurationType;

	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public void setConfigurationType(SchedulerConfigurationType configurationType) {
		this.configurationType = configurationType;
	}

	@Override
	public ScheduledTask build() {
		return new ScheduledTask() {
			@Override
			public Class<? extends ScheduledJob> getJobClass() {
				return ProcessJob.class;
			}

			@Override
			public Map<String, Object> getParameters() {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put(ScheduledConstants.PROCESSOR, processor);
				return params;
			}

			@Override
			public String getConfig() {
				return configuration;
			}

			@Override
			public SchedulerConfigurationType getConfigType() {
				return configurationType;
			}
		};
	}
}
