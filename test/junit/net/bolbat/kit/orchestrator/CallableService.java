package net.bolbat.kit.orchestrator;

import java.util.concurrent.TimeUnit;

import net.bolbat.kit.service.Service;
import net.bolbat.kit.service.ServiceException;

/**
 * Service for testing purposes.
 * 
 * @author Alexandr Bolbat
 */
public interface CallableService extends Service {

	String callDirect();

	String callOrchestrated();

	String callOrchestratedByClassExecutor();

	String callOrchestratedBySystemExecutor();

	String callWithMethodLimitsAndClassExecutor(long time, TimeUnit timeUnit);

	String callWithMethodExecutor(long time, TimeUnit timeUnit);

	void callOrchestratedByClassWithServiceException() throws ServiceException;

	void callOrchestratedByClassWithServiceRuntimeException();

	void executeWithTimeOut(long time, TimeUnit timeUnit);

	void executeWithTimeOutOnSmallQueue(long time, TimeUnit timeUnit);

	void executeWithTimeOutAndConcurrentLimit(long time, TimeUnit timeUnit);

}
