package net.bolbat.kit.service;

import net.bolbat.kit.service.Service;

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
