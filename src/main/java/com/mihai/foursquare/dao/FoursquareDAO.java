package com.mihai.foursquare.dao;

import com.mihai.foursquare.domain.AbstractFoursquareResponse;
import com.mihai.foursquare.domain.RequestParams;

/**
 * DAO interface
 */
public interface FoursquareDAO {

    AbstractFoursquareResponse getFoursquareMetadata(RequestParams requestParams);
}
