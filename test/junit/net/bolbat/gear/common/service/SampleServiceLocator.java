package net.bolbat.gear.common.service;

/**
 * {@link SampleService} locator.
 * 
 * @author Alexandr Bolbat
 */
public class SampleServiceLocator implements ServiceLocator<SampleService> {

	@Override
	public SampleService create() throws ServiceInstantiationException {
		throw new IllegalAccessError();
	}

	@Override
	public SampleService locate(final ServiceLocatorConfiguration configuration) throws ServiceInstantiationException {
		return new SampleServiceRemoteImpl(String.valueOf(configuration.getParameterValue("PARAM1")));
	}

}
