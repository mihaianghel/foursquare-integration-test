package com.andigital.foursquare.service;

import java.util.Collection;

import com.andigital.foursquare.dto.RequestParamsDTO;
import com.andigital.foursquare.model.AbstractModel;
import com.andigital.foursquare.util.Operation;


/**
 * Interface for FoursquareService
 */
public interface FoursquareService {
	
	/**
	 * Processes data from the user and makes a call to the http client
	 * @param requestParams parameters of the request
	 * @return collection of {@link AbstractModel} objects
	 */
	Collection<AbstractModel> execute(RequestParamsDTO requestParams);

}
