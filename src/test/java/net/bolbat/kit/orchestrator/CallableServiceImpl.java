package net.bolbat.kit.orchestrator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import net.bolbat.kit.orchestrator.CallResponce.State;
import net.bolbat.kit.orchestrator.annotation.Orchestrate;
import net.bolbat.kit.orchestrator.annotation.OrchestrationExecutor;
import net.bolbat.kit.orchestrator.annotation.OrchestrationLimits;
import net.bolbat.kit.orchestrator.annotation.OrchestrationMode;
import net.bolbat.kit.orchestrator.annotation.OrchestrationMode.Mode;
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
@OrchestrationLimits(concurrent = 100, time = 1, timeUnit = TimeUnit.SECONDS)
public class CallableServiceImpl implements CallableService {

	private final CallResponce defaultCallResponce = new CallResponce();

	private volatile CallResponce callResponce;

	@Override
	@Orchestrate(false)
	public String callDirect() {
		callResponce = new CallResponce().setState(State.EXECUTED);
		return callResponce.getThreadName();
	}

	@Override
	@Orchestrate
	public String callOrchestrated() {
		callResponce = new CallResponce().setState(State.EXECUTED);
		return callResponce.getThreadName();
	}

	@Override
	public String callOrchestratedByClassExecutor() {
		callResponce = new CallResponce().setState(State.EXECUTED);
		return callResponce.getThreadName();
	}

	@Override
	@Orchestrate
	@OrchestrationExecutor(factory = SystemExecutorServiceFactory.class)
	public String callOrchestratedBySystemExecutor() {
		callResponce = new CallResponce().setState(State.EXECUTED);
		return callResponce.getThreadName();
	}

	@Override
	@OrchestrationLimits(concurrent = 1, time = 5, timeUnit = TimeUnit.MILLISECONDS)
	public String callWithMethodLimitsAndClassExecutor(final long time, final TimeUnit timeUnit) {
		callResponce = new CallResponce().setState(State.INITIATED);
		sleep(time, timeUnit);
		callResponce = new CallResponce().setState(State.EXECUTED);
		return callResponce.getThreadName();
	}

	@Override
	@OrchestrationExecutor(maxSize = 1, queueSize = 1)
	public String callWithMethodExecutor(final long time, final TimeUnit timeUnit) {
		callResponce = new CallResponce().setState(State.INITIATED);
		sleep(time, timeUnit);
		callResponce = new CallResponce().setState(State.EXECUTED);
		return callResponce.getThreadName();
	}

	@Override
	public void callOrchestratedByClassWithServiceException() throws ServiceException {
		callResponce = new CallResponce().setState(State.INITIATED);
		throw new ServiceException("custom checked service exception");
	}

	@Override
	public void callOrchestratedByClassWithServiceRuntimeException() {
		callResponce = new CallResponce().setState(State.INITIATED);
		throw new ServiceRuntimeException("custom runtime service exception");
	}

	@Override
	@Orchestrate
	@OrchestrationLimits(time = 5, timeUnit = TimeUnit.MILLISECONDS)
	public void callWithTimeOut(final long time, final TimeUnit timeUnit) {
		callResponce = new CallResponce().setState(State.INITIATED);
		sleep(time, timeUnit);
		callResponce = new CallResponce().setState(State.EXECUTED);
	}

	@Override
	@Orchestrate
	@OrchestrationExecutor(maxSize = 1, queueSize = 1)
	public void callWithTimeOutOnSmallQueue(final long time, final TimeUnit timeUnit) {
		callResponce = new CallResponce().setState(State.INITIATED);
		sleep(time, timeUnit);
		callResponce = new CallResponce().setState(State.EXECUTED);
	}

	@Override
	@Orchestrate
	@OrchestrationLimits(concurrent = 1)
	public void callWithTimeOutAndConcurrentLimit(final long time, final TimeUnit timeUnit) {
		callResponce = new CallResponce().setState(State.INITIATED);
		sleep(time, timeUnit);
		callResponce = new CallResponce().setState(State.EXECUTED);
	}

	@Override
	@OrchestrationMode(Mode.ASYNC)
	@OrchestrationLimits(concurrent = 100, time = 1, timeUnit = TimeUnit.HOURS)
	public void callAsyncVoid(final long time, final TimeUnit timeUnit) {
		callResponce = new CallResponce().setState(State.INITIATED);
		sleep(time, timeUnit);
		callResponce = new CallResponce().setState(State.EXECUTED);
	}

	@Override
	@OrchestrationMode(Mode.ASYNC)
	public String callAsyncNotVoid(final long time, final TimeUnit timeUnit) {
		callResponce = new CallResponce().setState(State.INITIATED);
		sleep(time, timeUnit);
		callResponce = new CallResponce().setState(State.EXECUTED);
		return callResponce.getThreadName();
	}

	@Override
	@Orchestrate
	@OrchestrationMode(Mode.ASYNC)
	@OrchestrationLimits(time = 5, timeUnit = TimeUnit.MILLISECONDS)
	public void callAsyncWithTimeOut(final long time, final TimeUnit timeUnit) {
		callResponce = new CallResponce().setState(State.INITIATED);
		sleep(time, timeUnit);
		callResponce = new CallResponce().setState(State.EXECUTED);
	}

	@Override
	@Orchestrate
	@OrchestrationMode(Mode.ASYNC)
	@OrchestrationExecutor(maxSize = 1, queueSize = 1)
	public void callAsyncWithTimeOutOnSmallQueue(final long time, final TimeUnit timeUnit) {
		callResponce = new CallResponce().setState(State.INITIATED);
		sleep(time, timeUnit);
		callResponce = new CallResponce().setState(State.EXECUTED);
	}

	@Override
	@Orchestrate
	@OrchestrationMode(Mode.ASYNC)
	@OrchestrationLimits(concurrent = 1)
	public void callAsyncWithTimeOutAndConcurrentLimit(final long time, final TimeUnit timeUnit) {
		callResponce = new CallResponce().setState(State.INITIATED);
		sleep(time, timeUnit);
		callResponce = new CallResponce().setState(State.EXECUTED);
	}

	@Override
	@Orchestrate
	@OrchestrationExecutor(coreSize = 2, maxSize = 4)
	public void callOnMaxThreads(final CountDownLatch latch, final AtomicInteger counter) {
		try {
			counter.incrementAndGet();
			latch.await();
		} catch (final InterruptedException e) {
			throw new OrchestrationException("execution is interrupted", e);
		} finally {
			counter.decrementAndGet();
		}
	}

	private static void sleep(final long time, final TimeUnit timeUnit) {
		try {
			Thread.sleep(timeUnit.toMillis(time));
		} catch (final InterruptedException e) {
			throw new OrchestrationException("execution is interrupted", e);
		}
	}

	@Override
	public CallResponce getCallResponce() {
		return callResponce != null ? callResponce : defaultCallResponce;
	}

	@Override
	public void resetCallResponce() {
		callResponce = null;
	}

}
