package net.bolbat.kit.service;

/**
 * {@link SampleService} implementation.
 * 
 * @author Alexandr Bolbat
 */
public class SampleServiceImpl implements SampleService {

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

}
