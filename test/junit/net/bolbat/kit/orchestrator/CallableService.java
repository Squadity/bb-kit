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

	void callWithTimeOut(long time, TimeUnit timeUnit);

	void callWithTimeOutOnSmallQueue(long time, TimeUnit timeUnit);

	void callWithTimeOutAndConcurrentLimit(long time, TimeUnit timeUnit);

	void callAsyncVoid(long time, TimeUnit timeUnit);

	String callAsyncNotVoid(long time, TimeUnit timeUnit);

	void callAsyncWithTimeOut(long time, TimeUnit timeUnit);

	void callAsyncWithTimeOutOnSmallQueue(long time, TimeUnit timeUnit);

	void callAsyncWithTimeOutAndConcurrentLimit(long time, TimeUnit timeUnit);

	CallResponce getCallResponce();

	void resetCallResponce();

}
