package com.andigital.foursquare.service;

import java.util.Collection;

import com.andigital.foursquare.model.AbstractModel;


/**
 * Interface for FoursquareService
 */
public interface FoursquareService {
	
	Collection<AbstractModel> execute(String location, Integer radius, Integer limit);

}
