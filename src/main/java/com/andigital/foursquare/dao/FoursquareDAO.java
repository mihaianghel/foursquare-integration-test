package com.andigital.foursquare.dao;

import com.andigital.foursquare.domain.RequestParams;

/**
 * DAO interface
 */
public interface FoursquareDAO {

    String getFoursquareMetadata(RequestParams requestModelObject);
}
