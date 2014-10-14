package net.bolbat.kit.service;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PreDestroy;

/**
 * {@link SampleService} implementation.
 * 
 * @author Alexandr Bolbat
 */
public class SampleServiceImpl implements SampleService {

	/**
	 * Amount of pre-destroy executions.
	 */
	private static final AtomicInteger PREDESTROYED_AMOUNT = new AtomicInteger(0);

	/**
	 * Service creation method string.
	 */
	protected final String creationMethod;

	/**
	 * Default constructor.
	 */
	protected SampleServiceImpl() {
		this.creationMethod = "CREATED";
	}

	/**
	 * Public constructor.
	 * 
	 * @param aSomeParameter
	 *            some parameter
	 */
	protected SampleServiceImpl(final String aSomeParameter) {
		this.creationMethod = "LOCATED. PARAMETER: " + aSomeParameter;
	}

	@Override
	public String getCreationMethod() {
		return creationMethod;
	}

	/**
	 * Get amount of pre-destroy method executions.
	 * 
	 * @return <code>int</code>
	 */
	public static final int getPreDestroyedAcount() {
		return PREDESTROYED_AMOUNT.get();
	}

	/**
	 * Execute pre-destroy.
	 */
	@PreDestroy
	private void preDestroy() {
		PREDESTROYED_AMOUNT.incrementAndGet();
	}

	/**
	 * Execute pre-destroy from method with {@link RuntimeException}.
	 * 
	 * @throws RuntimeException
	 */
	@PreDestroy
	private void preDestroyWithRuntimeException() throws RuntimeException {
		PREDESTROYED_AMOUNT.incrementAndGet();
		throw new RuntimeException("Just for check.");
	}

	/**
	 * Not valid pre-destroy.
	 * 
	 * @return {@link Object}
	 */
	@PreDestroy
	private Object preDestroyNotVoid() {
		PREDESTROYED_AMOUNT.incrementAndGet();
		return new Object();
	}

	/**
	 * Not valid pre-destroy.
	 */
	@PreDestroy
	private static void preDestroyWothParams(final Object parameter) {
		PREDESTROYED_AMOUNT.incrementAndGet();
	}

	/**
	 * Not valid pre-destroy.
	 */
	@PreDestroy
	private static void preDestroyStatic() {
		PREDESTROYED_AMOUNT.incrementAndGet();
	}

	/**
	 * Not valid pre-destroy.
	 * 
	 * @throws Exception
	 */
	@PreDestroy
	private static void preDestroyWithException() throws Exception {
		PREDESTROYED_AMOUNT.incrementAndGet();
	}

}
