package net.bolbat.gear.common.service;

/**
 * {@link SampleService} factory.
 * 
 * @author Alexandr Bolbat
 */
public class SampleServiceRemoteFactory implements ServiceFactory<SampleService> {

	/**
	 * Testing parameter name.
	 */
	public static final String PARAM_TEST = "PARAM1";

	@Override
	public SampleService create(final Configuration configuration) throws ServiceInstantiationException {
		return new SampleServiceRemoteImpl(configuration.getString(PARAM_TEST));
	}

}
