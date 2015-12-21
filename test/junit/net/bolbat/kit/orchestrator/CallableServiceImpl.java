package net.bolbat.kit.orchestrator;

import java.util.concurrent.TimeUnit;

import net.bolbat.kit.orchestrator.annotation.Orchestrate;
import net.bolbat.kit.orchestrator.annotation.OrchestrationExecutor;
import net.bolbat.kit.orchestrator.annotation.OrchestrationLimits;
import net.bolbat.kit.orchestrator.exception.OrchestrationException;
import net.bolbat.kit.orchestrator.impl.executor.SystemExecutorServiceFactory;
import net.bolbat.kit.service.ServiceException;
import net.bolbat.kit.service.ServiceRuntimeException;

/**
 * {@link CallableService} implementation.
 * 
 * @author Alexandr Bolbat
 */
@Orchestrate
public class CallableServiceImpl implements CallableService {

	@Override
	@Orchestrate(false)
	public String callDirect() {
		return Thread.currentThread().getName();
	}

	@Override
	@Orchestrate
	public String callOrchestrated() {
		return Thread.currentThread().getName();
	}

	@Override
	public String callOrchestratedByClassExecutor() {
		return Thread.currentThread().getName();
	}

	@Override
	@Orchestrate
	@OrchestrationExecutor(factory = SystemExecutorServiceFactory.class)
	public String callOrchestratedBySystemExecutor() {
		return Thread.currentThread().getName();
	}

	@Override
	public void callOrchestratedByClassWithServiceException() throws ServiceException {
		throw new ServiceException("custom checked service exception");

	}

	@Override
	public void callOrchestratedByClassWithServiceRuntimeException() {
		throw new ServiceRuntimeException("custom runtime service exception");
	}

	@Override
	@Orchestrate
	@OrchestrationLimits(time = 5, timeUnit = TimeUnit.MILLISECONDS)
	public void executeWithTimeOut(final long time, final TimeUnit timeUnit) {
		sleep(time, timeUnit);
	}

	@Override
	@Orchestrate
	@OrchestrationExecutor(maxSize = 1, queueSize = 1)
	public void executeWithTimeOutOnSmallQueue(final long time, final TimeUnit timeUnit) {
		sleep(time, timeUnit);
	}

	@Override
	@Orchestrate
	@OrchestrationLimits(concurrent = 1)
	public void executeWithTimeOutAndConcurrentLimit(final long time, final TimeUnit timeUnit) {
		sleep(time, timeUnit);
	}

	private static void sleep(final long time, final TimeUnit timeUnit) {
		try {
			Thread.sleep(timeUnit.toMillis(time));
		} catch (final InterruptedException e) {
			throw new OrchestrationException("execution is interrupted", e);
		}
	}

}
