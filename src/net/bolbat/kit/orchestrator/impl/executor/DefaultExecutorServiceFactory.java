package net.bolbat.kit.orchestrator.impl.executor;

import static net.bolbat.utils.lang.StringUtils.isNotEmpty;
import static net.bolbat.utils.lang.Validations.checkArgument;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

import net.bolbat.kit.orchestrator.OrchestrationConfig.ExecutorConfig;
import net.bolbat.kit.orchestrator.OrchestrationConstants;
import net.bolbat.utils.concurrency.ThreadFactoryBuilder;

/**
 * {@link ExecutorServiceFactory} default implementation.<br>
 * They create new {@link ExecutorService} instance based on given arguments.
 * 
 * @author Alexandr Bolbat
 */
public class DefaultExecutorServiceFactory implements ExecutorServiceFactory {

	/**
	 * {@link DefaultExecutorServiceFactory} instance.
	 */
	private static final DefaultExecutorServiceFactory INSTANCE = new DefaultExecutorServiceFactory();

	/**
	 * Private constructor.
	 */
	private DefaultExecutorServiceFactory() {
	}

	@Override
	public ExecutorService create(final ExecutorConfig config, final Object... nameFormatArgs) {
		checkArgument(config != null, "config argument is null");

		final String nameFormat = isNotEmpty(config.getNameFormat()) ? config.getNameFormat() : OrchestrationConstants.THREAD_NAME_FORMAT;
		final ThreadFactoryBuilder factoryBuilder = new ThreadFactoryBuilder() //
				.setDaemon(true) // move me to configuration if needed
				.setPriority(Thread.NORM_PRIORITY) // move me to configuration if needed
				.setNameFormat(nameFormat) //
				.setNameFormatArgs(nameFormatArgs);

		final BlockingQueue<Runnable> queue = config.getQueueSize() == OrchestrationConstants.POOL_QUEUE_SIZE //
				? new SynchronousQueue<Runnable>() //
				: new ArrayBlockingQueue<Runnable>(config.getQueueSize());

		final int poolMaxSize = config.getMaxSize() == OrchestrationConstants.POOL_MAX_SIZE ? Integer.MAX_VALUE : config.getMaxSize();
		final ExecutorService executor = new ThreadPoolExecutor( //
				config.getCoreSize(), //
				poolMaxSize, //
				config.getKeepAlive(), //
				config.getKeepAliveUnit(), //
				queue, //
				factoryBuilder.build(), //
				new AbortPolicy());

		return executor;
	}

	/**
	 * Get {@link DefaultExecutorServiceFactory} instance.
	 * 
	 * @return {@link DefaultExecutorServiceFactory}
	 */
	public static DefaultExecutorServiceFactory getInstance() {
		return INSTANCE;
	}

}
