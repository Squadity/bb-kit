package net.bolbat.kit.service;

/**
 * Sample {@link Service}.
 * 
 * @author Alexandr Bolbat
 */
public interface SampleService extends Service {

	int getPostConstructedAmount();

	int getPreDestroyedAmount();

	String getCreationMethod() throws SampleServiceException;

}
