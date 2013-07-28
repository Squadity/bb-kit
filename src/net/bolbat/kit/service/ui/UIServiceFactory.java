package net.bolbat.kit.service.ui;

import net.bolbat.kit.service.Configuration;
import net.bolbat.kit.service.ServiceFactory;

/**
 * Basic factory interface for instantiating {@link UIService} implementation.
 * 
 * @author Alexandr Bolbat
 * @param <T>
 *            UI service interface
 */
public interface UIServiceFactory<T extends UIService> extends ServiceFactory<T> {

	/**
	 * UI service implementation instantiation method.
	 * 
	 * @param configuration
	 *            factory configuration
	 * @return <T> instance
	 * @throws UIServiceInstantiationException
	 */
	T create(Configuration configuration) throws UIServiceInstantiationException;

}
