package net.bolbat.kit.service;

import net.bolbat.kit.service.Configuration;
import net.bolbat.kit.service.ServiceFactory;
import net.bolbat.kit.service.ServiceInstantiationException;

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
