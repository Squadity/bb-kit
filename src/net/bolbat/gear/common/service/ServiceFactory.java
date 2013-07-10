package net.bolbat.gear.common.service;

/**
 * Basic factory interface for instantiating {@link Service} implementation.
 * 
 * @author Alexandr Bolbat
 * @param <T>
 *            service interface
 */
public interface ServiceFactory<T extends Service> {

	/**
	 * Service implementation instantiation method.
	 * 
	 * @return <T> instance
	 * @throws ServiceInstantiationException
	 */
	T create() throws ServiceInstantiationException;

}
