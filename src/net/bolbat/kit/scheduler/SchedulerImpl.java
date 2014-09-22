package net.bolbat.kit.scheduler;

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
	 * 		{@link TaskConfiguration}
	 * @throws SchedulerException
	 */
	protected SchedulerImpl(final TaskConfiguration task) throws SchedulerException {
		// scheduler initialization
		try {
			scheduler = SchedulerConfigurationFactory.getConfiguration(task);
			scheduler.start();
			jobDetail = JobBuilder.newJob(task.getJobClass()).withIdentity("QueueTask", "Scheduler").build();
			if (task.getParameters() == null)
				return;
			//TODO: make it in some constant class and use it in all classes that extends Task  class
			jobDetail.getJobDataMap().put(SchedulerConstants.PARAM_NAME_TASK_CONFIGURATION, task);
		} catch (final org.quartz.SchedulerException e) {
			final String message = "SchedulerImpl(...) scheduler initialization fail.";
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
			} catch (final org.quartz.SchedulerException e) {
				final String message = "pause() fail";
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
			} catch (final org.quartz.SchedulerException e) {
				final String message = "resume() fail";
				LOGGER.error(message, e);
				throw new SchedulerException(message, e);
			}
		}
	}

	@Override
	public boolean isStarted() throws SchedulerException {
		try {
			return !scheduler.isShutdown();
		} catch (final org.quartz.SchedulerException e) {
			final String message = "isStarted() fail";
			LOGGER.error(message, e);
			throw new SchedulerException(message, e);
		}
	}

	@Override
	public boolean isPaused() throws SchedulerException {
		try {
			return scheduler.isInStandbyMode();
		} catch (final org.quartz.SchedulerException e) {
			final String message = "isStarted() fail";
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
			final TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
			triggerBuilder.withIdentity("LoaderTrigger", "Scheduler").startNow();
			triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(schedule));
			triggerBuilder.forJob(jobDetail.getKey());
			configureTrigger(triggerBuilder.build());
		} catch (final org.quartz.SchedulerException e) {
			final String message = "schedule(" + schedule + ") fail";
			LOGGER.error(message, e);
			throw new SchedulerException(message, e);
		}
	}

	@Override
	public void schedule(final long interval) throws SchedulerException {
		try {
			if (scheduler.isShutdown())
				throw new IllegalStateException("Scheduler is off");

			if (interval < 1)
				throw new IllegalArgumentException("interval argument should be more then 0");

			final TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
			triggerBuilder.withIdentity("LoaderTrigger", "Scheduler").startNow();
			triggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(interval).repeatForever());
			configureTrigger(triggerBuilder.build());
		} catch (final org.quartz.SchedulerException e) {
			final String message = "schedule(" + interval + ") fail";
			LOGGER.error(message, e);
			throw new SchedulerException(message, e);
		}
	}

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
		} catch (final org.quartz.SchedulerException e) {
			final String message = "configureTrigger(" + trigger + ") fail";
			LOGGER.error(message, e);
			throw new SchedulerException(message, e);
		}
	}

}
