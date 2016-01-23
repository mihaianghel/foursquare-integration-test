package com.andigital.foursquare.client;

import com.andigital.foursquare.domain.RequestParams;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

/**
 * Abstract client for HTTP calls
 * @author mihaianghel
 */
public abstract class AbstractFoursquareClient {
	
	@Value("${4sq.endpoint}")
	protected String endpoint;

	@Value("${4sq.api.version}")
	protected String apiVersion;

	@Value("${4sq.clientid}")
	protected String clientId;

	@Value("${4sq.clientsecret}")
	protected String clientSecret;
	
	/**
	 * Initialise HTTP client
	 */
	public abstract void init();

	/**
	 * Execute HTTP calls
	 * @param model object encapsulating user input
	 * @return String representation of the response from Foursquare service
	 */
	public abstract String execute(RequestParams model);
	
	/**
	 * Destroy HTTP client
	 * @throws IOException
	 */
	public abstract void destroy() throws IOException;

}
