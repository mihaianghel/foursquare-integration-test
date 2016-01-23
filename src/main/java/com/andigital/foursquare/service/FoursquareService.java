package com.andigital.foursquare.service;

import com.andigital.foursquare.dto.RequestParamsDTO;
import com.andigital.foursquare.dto.ResponseDTO;

import java.util.Collection;


/**
 * Interface for FoursquareService
 */
public interface FoursquareService {
	
	/**
	 * Processes data from the user and makes a call to the http client
	 * @param requestParams parameters of the request
	 * @return collection of {@link ResponseDTO} objects
	 */
	Collection<ResponseDTO> execute(RequestParamsDTO requestParams);

}
