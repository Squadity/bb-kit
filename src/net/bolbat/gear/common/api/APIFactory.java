package net.bolbat.gear.common.api;

import net.bolbat.gear.common.service.Configuration;
import net.bolbat.gear.common.service.ServiceFactory;

/**
 * Basic factory interface for instantiating {@link API} implementation.
 * 
 * @author Alexandr Bolbat
 * @param <T>
 *            API interface
 */
public interface APIFactory<T extends API> extends ServiceFactory<T> {

	/**
	 * API implementation instantiation method.
	 * 
	 * @param configuration
	 *            factory configuration
	 * @return <T> instance
	 * @throws APIInstantiationException
	 */
	T create(Configuration configuration) throws APIInstantiationException;

}
