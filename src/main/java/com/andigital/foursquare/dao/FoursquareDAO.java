package com.andigital.foursquare.dao;

import com.andigital.foursquare.domain.AbstractFoursquareResponse;
import com.andigital.foursquare.domain.RequestParams;

/**
 * DAO interface
 */
public interface FoursquareDAO {

    AbstractFoursquareResponse getFoursquareMetadata(RequestParams requestModelObject);
}
