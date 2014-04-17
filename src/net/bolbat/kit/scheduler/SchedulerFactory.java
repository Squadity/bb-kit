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
	 * 		{@link TaskConfiguration }
	 * @return {@link Scheduler}
	 * @throws SchedulerException
	 */
	public static Scheduler create(final TaskConfiguration task) throws SchedulerException {
		return new SchedulerImpl(task);
	}
}
