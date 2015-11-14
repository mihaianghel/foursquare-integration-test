package com.andigital.foursquare.service;

import java.util.Collection;

import com.andigital.foursquare.model.AbstractModel;
import com.andigital.foursquare.util.Operation;


/**
 * Interface for FoursquareService
 */
public interface FoursquareService {
	
	Collection<AbstractModel> execute(String location, Integer radius, Integer limit, Operation operation);

}
