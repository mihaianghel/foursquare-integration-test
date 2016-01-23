package com.andigital.foursquare.service.impl;

import java.util.Collection;
import java.util.Collections;

import com.andigital.foursquare.dao.FoursquareDAO;
import com.andigital.foursquare.dto.RequestParamsDTO;
import com.andigital.foursquare.serialization.JSONDeserializer;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.andigital.foursquare.model.AbstractModel;
import com.andigital.foursquare.model.Meta;
import com.andigital.foursquare.domain.RequestParams;
import com.andigital.foursquare.service.FoursquareService;
import com.andigital.foursquare.util.Operation;
import com.google.gson.JsonObject;

/**
 * Implementation for the foursquare service
 */
@Component
public class FoursquareServiceImpl implements FoursquareService {

	@Autowired
	private FoursquareDAO foursquareDAO;

    @Autowired
    private JSONDeserializer<AbstractModel> jsonDeserializer;

	protected Logger LOG = LoggerFactory.getLogger(FoursquareServiceImpl.class);

	@Override
	public Collection<AbstractModel> execute(final RequestParamsDTO requestParamsDTO) {

		final RequestParams requestParams = convertTransferObjectToDomainObject(requestParamsDTO);
		try {
			final String fourSquareResponseAsString = foursquareDAO.getFousquareMetadata(requestParams);
			if (StringUtils.isNotBlank(fourSquareResponseAsString)) {
				final JsonObject fourSquareResponseAsJson = jsonDeserializer.deserialise(fourSquareResponseAsString);
				final Meta meta = (Meta) jsonDeserializer.unmarshallMeta(fourSquareResponseAsJson);
				if (meta.getStatusCode() == HttpStatus.SC_OK) {
					LOG.info("Successful response from Foursquare API");
					return jsonDeserializer.unmarshallResponse(fourSquareResponseAsJson);
				} else {
					LOG.error(
							String.format("Request to Foursquare API had incorrect parameters. StatusCode = %s Error = %s",
									meta.getStatusCode(), meta.getError()));
				}
			} else {
				LOG.error("Request to Foursquare API was unsuccessful");
			}
			return Collections.emptyList();
		} catch (Exception e) {
			LOG.error("Unexpected error ocurred during processing", e);
			return Collections.emptyList();
		}
	}

    //TODO: use spring converter annotation
    private RequestParams convertTransferObjectToDomainObject(final RequestParamsDTO requestParamsDTO) {
        return new RequestParams(requestParamsDTO.getLocation(),
                requestParamsDTO.getRadius(),
                requestParamsDTO.getLimit());
    }

}
