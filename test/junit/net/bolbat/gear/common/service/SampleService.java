package net.bolbat.gear.common.service;

/**
 * Sample {@link Service}.
 * 
 * @author Alexandr Bolbat
 */
public interface SampleService extends Service {

	/**
	 * Get creation method string.
	 * 
	 * @return {@link String}
	 * @throws SampleServiceException
	 */
	String getCreationMethod() throws SampleServiceException;

}
