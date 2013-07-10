package net.bolbat.gear.common.ioc;

import java.util.Arrays;

import net.bolbat.gear.common.service.Service;
import net.bolbat.gear.common.service.ServiceFactory;
import net.bolbat.gear.common.service.ServiceLocatorConfiguration;

/**
 * Service scope configuration.
 * 
 * @author Alexandr Bolbat
 * 
 * @param <S>
 *            service
 * @param <SF>
 *            service factory
 */
public final class ServiceScopeConfiguration<S extends Service, SF extends ServiceFactory<S>> {

	/**
	 * Service interface.
	 */
	private final Class<S> service;

	/**
	 * Service factory.
	 */
	private final SF serviceFactory;

	/**
	 * Service locator configuration, used if service factory instance of service locator.
	 */
	private final ServiceLocatorConfiguration configuration;

	/**
	 * Service scopes.
	 */
	private final Scope[] scopes;

	/**
	 * Service instance.
	 */
	private S instance;

	/**
	 * Default constructor.
	 * 
	 * @param aService
	 *            service interface
	 * @param aServiceFactory
	 *            service factory
	 * @param aConfiguration
	 *            service locator configuration, used if service factory instance of service locator
	 * @param aScopes
	 *            service scopes
	 */
	protected ServiceScopeConfiguration(final Class<S> aService, final SF aServiceFactory, final ServiceLocatorConfiguration aConfiguration,
			final Scope[] aScopes) {
		this.service = aService;
		this.serviceFactory = aServiceFactory;
		this.configuration = aConfiguration;
		this.scopes = aScopes;
	}

	public S getInstance() {
		return instance;
	}

	public void setInstance(S aInstance) {
		this.instance = aInstance;
	}

	public Class<S> getService() {
		return service;
	}

	public SF getServiceFactory() {
		return serviceFactory;
	}

	public ServiceLocatorConfiguration getConfiguration() {
		return configuration;
	}

	public Scope[] getScopes() {
		return scopes.clone();
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [service=").append(service);
		builder.append(", serviceFactory=").append(serviceFactory);
		builder.append(", configuration=").append(configuration);
		builder.append(", scopes=").append(scopes != null ? Arrays.asList(scopes).subList(0, Math.min(scopes.length, maxLen)) : null);
		builder.append(", instance=").append(instance != null ? instance.getClass().getName() : null);
		builder.append("]");
		return builder.toString();
	}

}
