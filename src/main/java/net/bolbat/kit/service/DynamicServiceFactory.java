package net.bolbat.kit.service;

import static net.bolbat.utils.lang.Validations.checkArgument;

import net.bolbat.utils.reflect.Instantiator;

/**
 * {@link ServiceFactory} implementation with dynamic implementation instantiation.
 * 
 * @author Alexandr Bolbat
 * @param <T>
 *            service interface
 */
public class DynamicServiceFactory<T extends Service> implements ServiceFactory<T> {

	/**
	 * Service implementation class.
	 */
	private final Class<T> implClass;

	/**
	 * Default constructor.
	 * 
	 * @param aImplClass
	 *            service implementation class
	 */
	public DynamicServiceFactory(final Class<T> aImplClass) {
		checkArgument(aImplClass != null, "aImplClass class argument is null");

		this.implClass = aImplClass;
	}

	@Override
	public T create(final Configuration configuration) {
		return Instantiator.instantiate(implClass);
	}

}
