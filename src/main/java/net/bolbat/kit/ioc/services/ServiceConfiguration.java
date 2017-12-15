package net.bolbat.kit.ioc.services;

import net.bolbat.kit.ioc.ManagerUtils;
import net.bolbat.kit.ioc.scope.Scope;
import net.bolbat.kit.service.Configuration;
import net.bolbat.kit.service.Service;
import net.bolbat.kit.service.ServiceFactory;
import net.bolbat.utils.lang.ToStringUtils;

/**
 * Service configuration.
 * 
 * @author Alexandr Bolbat
 * 
 * @param <S>
 *            service type
 */
public class ServiceConfiguration<S extends Service> {

	/**
	 * Service interface.
	 */
	private final Class<S> service;

	/**
	 * Service factory.
	 */
	private final ServiceFactory<S> factory;

	/**
	 * Service factory configuration.
	 */
	private final Configuration factoryConf;

	/**
	 * Service scopes.
	 */
	private final Scope[] scopes;

	/**
	 * Service instance.
	 */
	private S instance;

	/**
	 * Package protected constructor.
	 * 
	 * @param aService
	 *            service interface
	 * @param aFactory
	 *            service factory
	 * @param aFactoryConf
	 *            service factory configuration, can be <code>null</code>
	 * @param aScopes
	 *            service scopes
	 */
	ServiceConfiguration(final Class<S> aService, final ServiceFactory<S> aFactory, final Configuration aFactoryConf, final Scope[] aScopes) {
		this.service = aService;
		this.factory = aFactory;
		this.factoryConf = aFactoryConf != null ? aFactoryConf : Configuration.EMPTY;
		this.scopes = aScopes;
	}

	/**
	 * Package protected constructor.
	 * 
	 * @param aService
	 *            service interface
	 * @param aInstance
	 *            service instance
	 * @param aScopes
	 *            service scopes
	 */
	ServiceConfiguration(final Class<S> aService, final S aInstance, final Scope[] aScopes) {
		this.service = aService;
		this.factory = null;
		this.factoryConf = Configuration.EMPTY;
		this.scopes = aScopes;
		this.instance = aInstance;
	}

	public Class<S> getService() {
		return service;
	}

	public ServiceFactory<S> getFactory() {
		return factory;
	}

	public Configuration getFactoryConf() {
		return factoryConf;
	}

	public S getInstance() {
		return instance;
	}

	public void setInstance(final S aInstance) {
		this.instance = aInstance;
	}

	/**
	 * Get configuration key.
	 * 
	 * @return {@link String}
	 */
	public String toKey() {
		return ManagerUtils.resolveKey(service, scopes);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [service=").append(service);
		builder.append(", factory=").append(factory);
		builder.append(", factoryConf=").append(factoryConf);
		builder.append(", scopes=").append(ToStringUtils.toString(scopes));
		builder.append(", instance=").append(instance != null ? instance.getClass().getName() : null);
		builder.append("]");
		return builder.toString();
	}

}
