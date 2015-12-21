package net.bolbat.kit.orchestrator.impl.executor;

import java.util.concurrent.ExecutorService;

import net.bolbat.kit.orchestrator.OrchestrationConfig;
import net.bolbat.kit.orchestrator.impl.ExecutionUtils;

/**
 * {@link ExecutorServiceFactory} system implementation.<br>
 * They use system {@link OrchestrationConfig}.
 * 
 * @author Alexandr Bolbat
 */
public class SystemExecutorServiceFactory implements ExecutorServiceFactory {

	/**
	 * System {@link ExecutorService} core pool size.
	 */
	public static final int SYS_POOL_CORE_SIZE = 5;

	/**
	 * System {@link ExecutorService} thread name format.<br>
	 * Format arguments:<br>
	 * - execution identifier;<br>
	 * - execution name;<br>
	 * - thread number.<br>
	 * Example: Orchestrator[system]-thread[1].
	 */
	public static final String SYS_THREAD_NAME_FORMAT = "Orchestrator[system]-thread[%3$d]";

	/**
	 * {@link SystemExecutorServiceFactory} instance.
	 */
	private static final SystemExecutorServiceFactory INSTANCE = new SystemExecutorServiceFactory();

	/**
	 * System {@link OrchestrationConfig}.
	 */
	private static final OrchestrationConfig SYS_CONFIG;

	/**
	 * System {@link ExecutorService}.
	 */
	private volatile ExecutorService sysExecutor;

	/**
	 * Static initialization.
	 */
	static {
		SYS_CONFIG = new OrchestrationConfig();
		SYS_CONFIG.getExecutorConfig().setFactory(SystemExecutorServiceFactory.class);
		SYS_CONFIG.getExecutorConfig().setCoreSize(SYS_POOL_CORE_SIZE);
		SYS_CONFIG.getExecutorConfig().setNameFormat(SYS_THREAD_NAME_FORMAT);
		// other custom settings should be there
	}

	/**
	 * Private constructor.
	 */
	private SystemExecutorServiceFactory() {
	}

	@Override
	public ExecutorService create(final OrchestrationConfig config, final Object... nameFormatArgs) {
		if (sysExecutor == null)
			synchronized (SYS_CONFIG) {
				if (sysExecutor == null)
					sysExecutor = DefaultExecutorServiceFactory.getInstance().create(SYS_CONFIG, nameFormatArgs);
			}

		return sysExecutor;
	}

	/**
	 * Get {@link SystemExecutorServiceFactory} instance.
	 * 
	 * @return {@link SystemExecutorServiceFactory}
	 */
	public static SystemExecutorServiceFactory getInstance() {
		return INSTANCE;
	}

	/**
	 * Tear down system {@link ExecutorService} instance.
	 */
	public static void tearDown() {
		synchronized (SYS_CONFIG) {
			if (getInstance().sysExecutor != null) {
				final ExecutorService toTerminate = getInstance().sysExecutor;
				getInstance().sysExecutor = null;
				ExecutionUtils.terminate(toTerminate);
			}
		}
	}

}
