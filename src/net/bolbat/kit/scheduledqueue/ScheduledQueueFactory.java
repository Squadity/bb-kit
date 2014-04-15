package net.bolbat.kit.scheduledqueue;

/**
 * {@link ScheduledQueue} factory.
 *
 * @author ivanbatura
 */
public final class ScheduledQueueFactory {

	/**
	 * Create instance and configure {@link ScheduledQueue}.
	 *
	 * @param configFile
	 * 		name of the 'quartz' configuration file
	 * @param loader
	 * 		{@link Loader}
	 * @param processor
	 * 		{@link Processor}
	 * @return {@link ScheduledQueue}
	 * @throws ScheduledQueueException
	 */
	public static ScheduledQueue create(final String configFile, final Loader loader, final Processor processor) throws ScheduledQueueException {
		return new ScheduledQueueImpl(configFile, loader, processor);
	}

	/**
	 * Create instance and configure {@link ScheduledQueue}.
	 *
	 * @param loader
	 * 		{@link Loader}
	 * @param processor
	 * 		{@link Processor}
	 * @return {@link ScheduledQueue}
	 * @throws ScheduledQueueException
	 */
	public static ScheduledQueue create(final Loader loader, final Processor processor) throws ScheduledQueueException {
		return new ScheduledQueueImpl(null, loader, processor);
	}


	/**
	 * Create instance and configure {@link ScheduledQueue}.
	 *
	 * @param processor
	 * 		{@link Processor}
	 * @return {@link ScheduledQueue}
	 * @throws ScheduledQueueException
	 */
	public static ScheduledQueue create(final String configFile, final Processor processor) throws ScheduledQueueException {
		return new ScheduledQueueImpl(configFile, null, processor);
	}

	/**
	 * Create instance and configure {@link ScheduledQueue}.
	 *
	 * @param processor
	 * 		{@link Processor}
	 * @return {@link ScheduledQueue}
	 * @throws ScheduledQueueException
	 */
	public static ScheduledQueue create(final Processor processor) throws ScheduledQueueException {
		return new ScheduledQueueImpl(null, null, processor);
	}

}
