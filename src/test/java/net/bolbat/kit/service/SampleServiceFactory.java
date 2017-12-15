package net.bolbat.kit.service;

/**
 * {@link SampleService} factory.
 * 
 * @author Alexandr Bolbat
 */
public class SampleServiceFactory implements ServiceFactory<SampleService> {

	@Override
	public SampleService create(final Configuration configuration) {
		return new SampleServiceImpl();
	}

}
