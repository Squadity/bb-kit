package net.bolbat.gear.common.service;

/**
 * Basic locator interface for retrieving {@link Service} implementation.
 * 
 * @author Alexandr Bolbat
 * @param <T>
 *            service interface
 */
public interface ServiceLocator<T extends Service> extends ServiceFactory<T> {

	/**
	 * Service implementation retrieving method.
	 * 
	 * @param configuration
	 *            location configuration
	 * @return <T> instance
	 * @throws ServiceInstantiationException
	 */
	T locate(ServiceLocatorConfiguration configuration) throws ServiceInstantiationException;

}
