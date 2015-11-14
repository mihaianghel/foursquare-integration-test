package com.andigital.foursquare.service;

import java.util.Collection;

import com.andigital.foursquare.model.AbstractModel;
import com.andigital.foursquare.util.Operation;


/**
 * Interface for FoursquareService
 */
public interface FoursquareService {
	
	/**
	 * Processes data from the user and makes a call to the http client
	 * @param location set by the user
	 * @param radius set by the user
	 * @param limit set by the user
	 * @param operation - /explore in this case. Available for extension
	 * @return collection of {@link AbstractModel} objects
	 */
	Collection<AbstractModel> execute(String location, Integer radius, Integer limit, Operation operation);

}
