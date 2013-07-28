package net.bolbat.kit.service;

import net.bolbat.kit.service.Configuration;
import net.bolbat.kit.service.ServiceFactory;
import net.bolbat.kit.service.ServiceInstantiationException;

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
