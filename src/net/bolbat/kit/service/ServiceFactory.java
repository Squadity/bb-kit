package net.bolbat.kit.service;

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
	 * @param configuration
	 *            factory configuration
	 * @return <T> instance
	 */
	T create(Configuration configuration);

}
