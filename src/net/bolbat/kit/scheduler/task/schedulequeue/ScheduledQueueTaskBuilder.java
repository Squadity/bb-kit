package net.bolbat.kit.scheduler.task.schedulequeue;

import java.util.HashMap;
import java.util.Map;

import net.bolbat.kit.scheduler.ScheduledJob;
import net.bolbat.kit.scheduler.ScheduledTask;
import net.bolbat.kit.scheduler.ScheduledTaskBuilder;
import net.bolbat.kit.scheduler.SchedulerConfigurationType;
import net.bolbat.kit.scheduler.task.Loader;
import net.bolbat.kit.scheduler.task.ProcessingMode;
import net.bolbat.kit.scheduler.task.Processor;
import net.bolbat.kit.scheduler.task.ScheduledConstants;

/**
 * Builder for {@link ScheduledQueueJob}.
 *
 * @author ivanbatura
 */
public class ScheduledQueueTaskBuilder implements ScheduledTaskBuilder {

	/**
	 * {@link Loader}.
	 */
	private Loader loader;

	/**
	 * {@link Processor}.
	 */
	private Processor processor;

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

	public void setLoader(Loader loader) {
		this.loader = loader;
	}

	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

	public void setProcessingMode(ProcessingMode processingMode) {
		this.processingMode = processingMode;
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
				return ScheduledQueueJob.class;
			}

			@Override
			public Map<String, Object> getParameters() {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put(ScheduledConstants.LOADER, loader);
				params.put(ScheduledConstants.PROCESSOR, processor);
				params.put(ScheduledConstants.PROCESSING_MODE, processingMode);
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
