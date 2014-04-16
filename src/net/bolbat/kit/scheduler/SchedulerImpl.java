package net.bolbat.kit.scheduler;

import java.util.Map;

import net.bolbat.kit.scheduler.task.ProcessingMode;
import net.bolbat.utils.logging.LoggingUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link Scheduler} implementation.
 *
 * @author ivanbatura
 */
public class SchedulerImpl implements Scheduler {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerImpl.class);

	/**
	 * Processing mode.
	 */
	private volatile ProcessingMode mode = ProcessingMode.DEFAULT;

	/**
	 * Configured scheduler.
	 */
	private volatile org.quartz.Scheduler scheduler;

	/**
	 * Configured job.
	 */
	private volatile JobDetail jobDetail;

	/**
	 * Synchronization lock.
	 */
	private final Object lock = new Object();

	/**
	 * Default constructor.
	 *
	 * @param task
	 * 		{@link ScheduledTask}
	 * @throws SchedulerException
	 */
	protected SchedulerImpl(final ScheduledTask task) throws SchedulerException {
		// scheduler initialization
		try {
			scheduler = SchedulerConfigurationFactory.getConfiguration(task);
			scheduler.start();
			jobDetail = JobBuilder.newJob(task.getJobClass()).withIdentity("ScheduledQueueJob", "Scheduler").build();
			if (task.getParameters() == null)
				return;
			for (Map.Entry<String, Object> entry : task.getParameters().entrySet())
				jobDetail.getJobDataMap().put(entry.getKey(), entry.getValue());
		} catch (org.quartz.SchedulerException e) {
			String message = "SchedulerImpl(...) scheduler initialization fail.";
			LOGGER.error(LoggingUtils.FATAL, message, e);
			throw new SchedulerException(message, e);
		}
	}

	@Override
	public void pause() throws SchedulerException {
		synchronized (lock) {
			try {
				if (scheduler.isShutdown())
					throw new IllegalStateException("Scheduler is off");

				if (scheduler.isStarted())
					scheduler.standby();
			} catch (org.quartz.SchedulerException e) {
				String message = "pause() fail";
				LOGGER.error(message, e);
				throw new SchedulerException(message, e);
			}
		}
	}

	@Override
	public void resume() throws SchedulerException {
		synchronized (lock) {
			try {
				if (scheduler.isShutdown())
					throw new IllegalStateException("Scheduler is off");

				if (scheduler.isInStandbyMode())
					scheduler.start();
			} catch (org.quartz.SchedulerException e) {
				String message = "resume() fail";
				LOGGER.error(message, e);
				throw new SchedulerException(message, e);
			}
		}
	}

	@Override
	public boolean isStarted() throws SchedulerException {
		try {
			return !scheduler.isShutdown();
		} catch (org.quartz.SchedulerException e) {
			String message = "isStarted() fail";
			LOGGER.error(message, e);
			throw new SchedulerException(message, e);
		}
	}

	@Override
	public boolean isPaused() throws SchedulerException {
		try {
			return scheduler.isInStandbyMode();
		} catch (org.quartz.SchedulerException e) {
			String message = "isStarted() fail";
			LOGGER.error(message, e);
			throw new SchedulerException(message, e);
		}
	}

	@Override
	public void schedule(final String schedule) throws SchedulerException {
		try {
			if (scheduler.isShutdown())
				throw new IllegalStateException("Scheduler is off");

			if (schedule == null || schedule.trim().isEmpty())
				return;

			TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
			triggerBuilder.withIdentity("LoaderTrigger", "Scheduler").startNow();
			triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(schedule));
			triggerBuilder.forJob(jobDetail.getKey());
			configureTrigger(triggerBuilder.build());
		} catch (org.quartz.SchedulerException e) {
			String message = "schedule(" + schedule + ") fail";
			LOGGER.error(message, e);
			throw new SchedulerException(message, e);
		}
	}

	@Override
	public void schedule(long interval) throws SchedulerException {
		try {
			if (scheduler.isShutdown())
				throw new IllegalStateException("Scheduler is off");

			if (interval < 1)
				throw new IllegalArgumentException("interval argument should be more then 0");

			TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
			triggerBuilder.withIdentity("LoaderTrigger", "Scheduler").startNow();
			triggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(interval).repeatForever());
			configureTrigger(triggerBuilder.build());
		} catch (org.quartz.SchedulerException e) {
			String message = "schedule(" + interval + ") fail";
			LOGGER.error(message, e);
			throw new SchedulerException(message, e);
		}
	}

//	@Override
//	public synchronized void setMode(final ProcessingMode aMode) {
//		if (aMode == null)
//			throw new IllegalArgumentException("aProcessingMode argument is empty");
//
//		mode = aMode;
//		jobDetail.getJobDataMap().put(ScheduledConstants.PROCESSING_MODE, mode);
//	}

	@Override
	public void tearDown() {
		synchronized (lock) {
			try {
				if (scheduler.isShutdown())
					return;

				scheduler.shutdown(true);

				// waiting when all current job's finish it's work
				while (!scheduler.isShutdown())
					Thread.sleep(1L);
			} catch (org.quartz.SchedulerException e) {
				String message = "tearDown() fail";
				LOGGER.error(message, e);
				throw new RuntimeException(message, e);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Trigger configuration.
	 *
	 * @param trigger
	 * 		configured trigger
	 * @throws SchedulerException
	 */
	private synchronized void configureTrigger(final Trigger trigger) throws SchedulerException {
		try {
			// clearing old triggers from scheduler
			pause();
			if (scheduler.checkExists(jobDetail.getKey()))
				scheduler.deleteJob(jobDetail.getKey());

			scheduler.scheduleJob(jobDetail, trigger);
			resume();
		} catch (org.quartz.SchedulerException e) {
			String message = "configureTrigger(" + trigger + ") fail";
			LOGGER.error(message, e);
			throw new SchedulerException(message, e);
		}
	}

}
