package com.mihai.foursquare.service;

import com.mihai.foursquare.dto.RequestParamsDTO;
import com.mihai.foursquare.dto.ResponseDTO;

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
