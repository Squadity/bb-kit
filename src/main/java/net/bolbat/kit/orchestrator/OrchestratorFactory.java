package net.bolbat.kit.orchestrator;

import net.bolbat.kit.orchestrator.impl.ExecutionCaches;
import net.bolbat.kit.orchestrator.impl.OrchestratorImpl;
import net.bolbat.kit.orchestrator.impl.executor.SystemExecutorServiceFactory;
import net.bolbat.utils.annotation.Audience;
import net.bolbat.utils.annotation.Stability;

/**
 * {@link Orchestrator} factory.
 * 
 * @author Alexandr Bolbat
 */
@Audience.Public
@Stability.Evolving
public final class OrchestratorFactory {

	/**
	 * Instance creation lock for default {@link Orchestrator}.
	 */
	private static final Object LOCK = new Object();

	/**
	 * Default {@link Orchestrator} instance.
	 */
	private static volatile Orchestrator defaultInstance;

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private OrchestratorFactory() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Get default {@link Orchestrator}.
	 * 
	 * @return {@link Orchestrator}
	 */
	public static Orchestrator getDefault() {
		if (defaultInstance == null)
			synchronized (LOCK) {
				if (defaultInstance == null)
					defaultInstance = new OrchestratorImpl();
			}

		return defaultInstance;
	}

	/**
	 * Tear down {@link Orchestrator} internals.
	 */
	public static void tearDown() {
		synchronized (LOCK) {
			defaultInstance = null;
			ExecutionCaches.tearDown();
			SystemExecutorServiceFactory.tearDown();
		}
	}

}
