package net.bolbat.kit.service;

import static net.bolbat.utils.lang.StringUtils.isNotEmpty;
import static net.bolbat.utils.lang.Validations.checkArgument;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * {@link SampleService} implementation.
 * 
 * @author Alexandr Bolbat
 */
public class SampleServiceImpl implements SampleService {

	/**
	 * Amount of post-construct executions.
	 */
	private static final AtomicInteger POSTCONSTRUCTED_AMOUNT = new AtomicInteger(0);

	/**
	 * Amount of pre-destroy executions.
	 */
	private static final AtomicInteger PREDESTROYED_AMOUNT = new AtomicInteger(0);

	/**
	 * Service creation method string.
	 */
	protected String creationMethod;

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
	 * Create service instance directly (without factory).
	 * 
	 * @param source
	 *            creation source
	 * @return {@link SampleServiceImpl}
	 */
	public static SampleServiceImpl createBy(final String source) {
		checkArgument(isNotEmpty(source), "source argument is empty");

		final SampleServiceImpl result = new SampleServiceImpl();
		result.creationMethod += " BY: " + source;
		return result;
	}

	/**
	 * Get amount of post-construct method executions.
	 * 
	 * @return <code>int</code>
	 */
	public static final int getPostConstructedAcount() {
		return POSTCONSTRUCTED_AMOUNT.get();
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
	 * Execute post-construct.
	 */
	@PostConstruct
	private void postConstruct() {
		POSTCONSTRUCTED_AMOUNT.incrementAndGet();
	}

	/**
	 * Execute post-construct from method with {@link RuntimeException}.
	 * 
	 * @throws RuntimeException
	 */
	@PostConstruct
	private void postConstructWithRuntimeException() throws RuntimeException {
		POSTCONSTRUCTED_AMOUNT.incrementAndGet();
	}

	/**
	 * Not valid post-construct.
	 * 
	 * @return {@link Object}
	 */
	@PostConstruct
	private Object postConstructNotVoid() {
		POSTCONSTRUCTED_AMOUNT.incrementAndGet();
		return new Object();
	}

	/**
	 * Not valid post-construct.
	 */
	@PostConstruct
	private static void postConstructWithParams(final Object parameter) {
		POSTCONSTRUCTED_AMOUNT.incrementAndGet();
	}

	/**
	 * Not valid post-construct.
	 */
	@PostConstruct
	private static void postConstructStatic() {
		POSTCONSTRUCTED_AMOUNT.incrementAndGet();
	}

	/**
	 * Not valid post-construct.
	 * 
	 * @throws Exception
	 */
	@PostConstruct
	private static void postConstructWithException() throws Exception {
		POSTCONSTRUCTED_AMOUNT.incrementAndGet();
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
	private static void preDestroyWithParams(final Object parameter) {
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
