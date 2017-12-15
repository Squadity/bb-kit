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
	private final AtomicInteger postConstructedAmount = new AtomicInteger(0);

	/**
	 * Amount of pre-destroy executions.
	 */
	private final AtomicInteger preDestroyedAmount = new AtomicInteger(0);

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

	/**
	 * Get amount of post-construct method executions.
	 * 
	 * @return <code>int</code>
	 */
	@Override
	public int getPostConstructedAmount() {
		return postConstructedAmount.get();
	}

	/**
	 * Get amount of pre-destroy method executions.
	 * 
	 * @return <code>int</code>
	 */
	@Override
	public int getPreDestroyedAmount() {
		return preDestroyedAmount.get();
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
	 * Execute post-construct.
	 */
	@PostConstruct
	private void postConstruct() {
		postConstructedAmount.incrementAndGet();
	}

	/**
	 * Execute post-construct from method with {@link RuntimeException}.
	 * 
	 * @throws RuntimeException
	 */
	@PostConstruct
	private void postConstructWithRuntimeException() throws RuntimeException {
		postConstructedAmount.incrementAndGet();
	}

	/**
	 * Not valid post-construct.
	 * 
	 * @return {@link Object}
	 */
	@PostConstruct
	private Object postConstructNotVoid() {
		postConstructedAmount.incrementAndGet();
		return new Object();
	}

	/**
	 * Execute pre-destroy.
	 */
	@PreDestroy
	private void preDestroy() {
		preDestroyedAmount.incrementAndGet();
	}

	/**
	 * Execute pre-destroy from method with {@link RuntimeException}.
	 * 
	 * @throws RuntimeException
	 */
	@PreDestroy
	private void preDestroyWithRuntimeException() throws RuntimeException {
		preDestroyedAmount.incrementAndGet();
		throw new RuntimeException("Just for check.");
	}

	/**
	 * Not valid pre-destroy.
	 * 
	 * @return {@link Object}
	 */
	@PreDestroy
	private Object preDestroyNotVoid() {
		preDestroyedAmount.incrementAndGet();
		return new Object();
	}

}
