package com.andigital.foursquare.service.impl;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.andigital.foursquare.client.AbstractFoursquareClient;
import com.andigital.foursquare.model.AbstractModel;
import com.andigital.foursquare.model.Meta;
import com.andigital.foursquare.model.RequestModelObject;
import com.andigital.foursquare.serialization.JSONDeserializer;
import com.andigital.foursquare.service.FoursquareService;
import com.andigital.foursquare.util.Operation;
import com.google.gson.JsonObject;

import static com.andigital.foursquare.util.Constants.*;


/**
 * Service implementation that makes the call to the HTTP client and evaluates the response
 */
@Component
public class FoursquareServiceImpl implements FoursquareService {

	@Autowired
	private AbstractFoursquareClient foursquareHttpClient;
	
	@Autowired
	private JSONDeserializer<AbstractModel> jsonDeserializer;

	protected Logger LOG = LoggerFactory.getLogger(FoursquareServiceImpl.class);
	
	@Override
	public Collection<AbstractModel> execute(String location, Integer radius, Integer limit, Operation operation) {

		final RequestModelObject model = new RequestModelObject(location, radius, limit);
		try {
			final String fourSquareResponseAsString = foursquareHttpClient.execute(model, operation);
			if (StringUtils.isNotBlank(fourSquareResponseAsString)) {
				final JsonObject fourSquareResponseAsJson = jsonDeserializer.deserialise(fourSquareResponseAsString);
				final Meta meta = (Meta) jsonDeserializer.unmarshallMeta(fourSquareResponseAsJson.getAsJsonObject(META));
				if (meta.getStatusCode() == HttpStatus.SC_OK) {
					LOG.info("Successful response from Foursquare API");
					return jsonDeserializer.unmarshallResponse(fourSquareResponseAsJson.getAsJsonObject(RESPONSE));
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
}
