package com.andigital.foursquare.client;

/**
 * Abstract client for HTTP calls
 * @author mihaianghel
 */
public abstract class AbstractFoursquareClient {
	
	/**
	 * Initialise HTTP client
	 */
	public abstract void init();
	
	/**
	 * Execute HTTP calls
	 */
	public abstract String execute();
	
	/**
	 * Destroy HTTP client
	 */
	public abstract void destroy();

}
