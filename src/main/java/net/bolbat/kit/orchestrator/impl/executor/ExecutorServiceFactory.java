package net.bolbat.kit.orchestrator.impl.executor;

import java.util.concurrent.ExecutorService;

import net.bolbat.kit.orchestrator.OrchestrationConfig.ExecutorConfig;

/**
 * Factory for {@link ExecutorService}.
 * 
 * @author Alexandr Bolbat
 */
public interface ExecutorServiceFactory {

	/**
	 * Create {@link ExecutorService} instance.
	 * 
	 * @param config
	 *            {@link ExecutorConfig}
	 * @param nameFormatArgs
	 *            thread name format arguments
	 * @return {@link ExecutorService}
	 */
	ExecutorService create(ExecutorConfig config, Object... nameFormatArgs);

}
