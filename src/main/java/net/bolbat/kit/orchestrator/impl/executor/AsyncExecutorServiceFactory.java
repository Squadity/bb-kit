package net.bolbat.kit.orchestrator.impl.executor;

import java.util.concurrent.ExecutorService;

import net.bolbat.kit.orchestrator.OrchestrationConfig;
import net.bolbat.kit.orchestrator.OrchestrationConfig.ExecutorConfig;
import net.bolbat.kit.orchestrator.impl.ExecutionUtils;

/**
 * {@link ExecutorServiceFactory} implementation for 'ASYNC' executions.<br>
 * They use system {@link OrchestrationConfig}.
 * 
 * @author Alexandr Bolbat
 */
public class AsyncExecutorServiceFactory implements ExecutorServiceFactory {

	/**
	 * System {@link ExecutorService} core pool size.
	 */
	public static final int POOL_CORE_SIZE = 5;

	/**
	 * System {@link ExecutorService} thread name format.<br>
	 * Format arguments:<br>
	 * - execution identifier;<br>
	 * - execution name;<br>
	 * - thread number.<br>
	 * Example: Orchestrator[system]-thread[1].
	 */
	public static final String THREAD_NAME_FORMAT = "Orchestrator[async]-thread[%1$d]";

	/**
	 * {@link AsyncExecutorServiceFactory} instance.
	 */
	private static final AsyncExecutorServiceFactory INSTANCE = new AsyncExecutorServiceFactory();

	/**
	 * System {@link ExecutorConfig}.
	 */
	private static final ExecutorConfig CONFIG;

	/**
	 * System {@link ExecutorService}.
	 */
	private volatile ExecutorService executor;

	/**
	 * Static initialization.
	 */
	static {
		CONFIG = new ExecutorConfig();
		CONFIG.setFactory(AsyncExecutorServiceFactory.class);
		CONFIG.setCoreSize(POOL_CORE_SIZE);
		CONFIG.setNameFormat(THREAD_NAME_FORMAT);
		// other custom settings should be there
	}

	/**
	 * Private constructor.
	 */
	private AsyncExecutorServiceFactory() {
	}

	@Override
	public ExecutorService create(final ExecutorConfig config, final Object... nameFormatArgs) {
		if (executor == null)
			synchronized (CONFIG) {
				if (executor == null)
					executor = DefaultExecutorServiceFactory.getInstance().create(CONFIG, nameFormatArgs);
			}

		return executor;
	}

	/**
	 * Get {@link AsyncExecutorServiceFactory} instance.
	 * 
	 * @return {@link AsyncExecutorServiceFactory}
	 */
	public static AsyncExecutorServiceFactory getInstance() {
		return INSTANCE;
	}

	/**
	 * Get {@link ExecutorService}.
	 * 
	 * @return {@link ExecutorService}
	 */
	public static ExecutorService getExecutorService() {
		return getInstance().executor;
	}

	/**
	 * Tear down system {@link ExecutorService} instance.
	 */
	public static void tearDown() {
		synchronized (CONFIG) {
			if (getInstance().executor != null) {
				final ExecutorService toTerminate = getInstance().executor;
				getInstance().executor = null;
				ExecutionUtils.terminate(toTerminate);
			}
		}
	}

}
