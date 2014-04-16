package net.bolbat.kit.scheduler;

/**
 * {@link Scheduler} factory.
 *
 * @author ivanbatura
 */
public final class SchedulerFactory {

	/**
	 * Create instance and configure {@link Scheduler}.
	 *
	 * @param task
	 * 		{@link ScheduledTask }
	 * @return {@link Scheduler}
	 * @throws SchedulerException
	 */
	public static Scheduler create(final ScheduledTask task) throws SchedulerException {
		return new SchedulerImpl(task);
	}
}
