package com.andigital.foursquare.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.IOUtils;

import com.andigital.foursquare.model.AbstractModel;
import com.andigital.foursquare.model.ExploreResponseModelObject;
import com.andigital.foursquare.domain.RequestParams;

/**
 * Utility class for providing test data
 * @author mihaianghel
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
	 * Returns a {@link Collection} of {@link ExploreResponseModelObject} objects
	 * representing the mock response from the deserializer
	 */
	public static Collection<AbstractModel> getMockResponseFromDeserializer() {
		ExploreResponseModelObject o1 = new ExploreResponseModelObject("Millennium Park", "(312) 742-1168", null, "108831");
		ExploreResponseModelObject o2 = new ExploreResponseModelObject("Chicago Riverwalk", "-", null, "12827");
		return Arrays.asList(o1, o2);
	}
	
	/**
	 * Returns the a model object with the request parameters
	 */
	public static RequestParams getRequestMockData() {
		return new RequestParams("london", 20, 5);
	}
}
