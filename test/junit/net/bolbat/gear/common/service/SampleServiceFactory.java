package net.bolbat.gear.common.service;

/**
 * {@link SampleService} factory.
 * 
 * @author Alexandr Bolbat
 */
public class SampleServiceFactory implements ServiceFactory<SampleService> {

	@Override
	public SampleService create(final Configuration configuration) throws ServiceInstantiationException {
		return new SampleServiceImpl();
	}

}
