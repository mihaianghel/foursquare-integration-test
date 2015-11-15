package com.andigital.foursquare.serialization.impl;

import java.io.IOException;
import java.util.Collection;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import com.andigital.foursquare.model.AbstractModel;
import com.andigital.foursquare.model.Meta;
import com.google.gson.JsonObject;

import static org.junit.Assert.*;

import static com.andigital.foursquare.util.TestDataProvider.*;

/**
 * Test for {@link JSONDeserializerImpl}
 * @author mihaianghel
 */
public class JSONDeserializerImplTest {
	
	private JSONDeserializerImpl deserializer;
	private JsonObject jsonObject;
	
	@Before
	public void setUp() throws IOException {
		deserializer = new JSONDeserializerImpl();
		jsonObject = deserializer.deserialise(getMockResponseFromFoursquareAPI());
	}
	
	@Test
	public void deserializeResponseFromFoursquareAPI() {
		assertNotNull(jsonObject);
	}
	
	@Test
	public void deserializeMeta() {
		//when
		Meta meta = (Meta) deserializer.unmarshallMeta(jsonObject);
		
		//then
		assertEquals(HttpStatus.SC_OK, meta.getStatusCode());
	}
	
	@Test
	public void deserializeResponse() {
		//when
		Collection<AbstractModel> result = deserializer.unmarshallResponse(jsonObject);
		
		//then
		assertEquals(2, result.size());
	}
}
