package com.andigital.foursquare.service.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.andigital.foursquare.client.AbstractFoursquareClient;
import com.andigital.foursquare.model.AbstractModel;
import com.andigital.foursquare.model.Meta;
import com.andigital.foursquare.model.RequestModelObject;
import com.andigital.foursquare.serialization.JSONDeserializer;
import com.andigital.foursquare.util.Operation;
import com.google.gson.JsonObject;

import static org.mockito.Mockito.*;
import static com.andigital.foursquare.util.TestDataProvider.*;

/**
 * Test for {@link FoursquareServiceImpl}
 * @author mihaianghel
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(JsonObject.class)
public class FoursquareServiceImplTest {
	
	@Mock private AbstractFoursquareClient mockClient;
	@Mock private ConcurrentHashMap<RequestModelObject, String> mockCache;
	@Mock private JSONDeserializer<AbstractModel> mockJsonDeserializer;
	@InjectMocks private FoursquareServiceImpl service = new FoursquareServiceImpl();
	
	@Before
	public void setUp() throws IOException {
		JsonObject mockJsonObject = PowerMockito.mock(JsonObject.class);
		when(mockCache.containsKey(getRequestMockData())).thenReturn(true);
		when(mockCache.get(getRequestMockData())).thenReturn(getMockResponseFromFoursquareAPI());
		when(mockClient.execute(getRequestMockData(), Operation.EXPLORE)).thenReturn(getMockResponseFromFoursquareAPI());
		when(mockJsonDeserializer.deserialise(getMockResponseFromFoursquareAPI())).thenReturn(mockJsonObject);
		when(mockJsonDeserializer.unmarshallMeta(mockJsonObject)).thenReturn(new Meta(200, null));
		when(mockJsonDeserializer.unmarshallResponse(mockJsonObject)).thenReturn(getMockResponseFromDeserializer());
		
		injectMockCache();
	}
	
	@Test
	public void requestGoesToFoursquare() throws IOException {
		//given
		when(mockCache.containsKey(getRequestMockData())).thenReturn(false).thenReturn(false);
		
		//when
		Collection<AbstractModel> result = service.execute("london", 20, 5, Operation.EXPLORE);
	
		//then
		assertEquals(2, result.size());
		verify(mockCache, times(1)).put(getRequestMockData(), getMockResponseFromFoursquareAPI());
	}
	
	@Test
	public void requestGoesToCache() {
		//when
		Collection<AbstractModel> result = service.execute("london", 20, 5, Operation.EXPLORE);
	
		//then
		assertEquals(2, result.size());
		verify(mockCache, times(0)).put(any(RequestModelObject.class), anyString());
	}
	
	@Test
	public void responseCannotBeUnmarshalled() {
		//given
		when(mockCache.get(getRequestMockData())).thenReturn("{broken response}");
		
		//when
		Collection<AbstractModel> result = service.execute("london", 20, 5, Operation.EXPLORE);
	
		//then
		assertTrue(result.isEmpty());
	}
	
	@Test
	public void errorFromFoursquareAPI() {
		//given
		when(mockJsonDeserializer.unmarshallMeta(any(JsonObject.class))).thenReturn(new Meta(404, null));
		
		//when
		Collection<AbstractModel> result = service.execute("london", 20, 5, Operation.EXPLORE);
	
		//then
		assertTrue(result.isEmpty());
	}
	
	private void injectMockCache() {
		try {
			final Field field = FoursquareServiceImpl.class.getDeclaredField("CACHE");
			field.setAccessible(true);
			field.set(service, mockCache);
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			fail("Failed to inject mock cache: " + ex);
		}
	}
	
}
