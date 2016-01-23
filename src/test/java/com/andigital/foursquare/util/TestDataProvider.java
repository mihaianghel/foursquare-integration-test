package com.andigital.foursquare.util;

import com.andigital.foursquare.domain.AbstractFoursquareResponse;
import com.andigital.foursquare.domain.Explore;
import com.andigital.foursquare.domain.RequestParams;
import com.andigital.foursquare.serialization.JSONDeserializer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class for providing test data
 */
public class TestDataProvider {

	/**
	 * Returns the string representation of the response from Foursquare API.
	 * The response is read from a propertied file.
	 * @throws IOException if something's wrong during file reading
	 */
	public static String getMockResponseFromFoursquareAPI() throws IOException {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("foursqAPIMockResponse");
		return IOUtils.toString(is, "UTF-8");
	}


	/**
	 * Returns the a domain object with the request parameters
	 */
	public static RequestParams getRequestMockData(final Operation operation) {
		return new RequestParams("london", 20, 5, operation);
	}

	public static AbstractFoursquareResponse getFoursquareParsedData() throws IOException {
		return JSONDeserializer.fromString(getMockResponseFromFoursquareAPI(), Explore.class);
	}
}
