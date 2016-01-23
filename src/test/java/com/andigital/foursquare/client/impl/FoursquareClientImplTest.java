package com.andigital.foursquare.client.impl;

import com.andigital.foursquare.util.Operation;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.lang.reflect.Field;

import static com.andigital.foursquare.util.TestDataProvider.getRequestMockData;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Test for {@link FoursquareClientImpl}
 * @author mihaianghel
 */
@RunWith(MockitoJUnitRunner.class)
public class FoursquareClientImplTest {

	@Mock HttpClient mockHttpClient;
	private FoursquareClientImpl client = new FoursquareClientImpl();

	@Before
	public void setUp() {
        ReflectionTestUtils.setField(client, "endpoint", "https://api.foursquare");
        ReflectionTestUtils.setField(client, "apiVersion", "v2");
        ReflectionTestUtils.setField(client, "clientId", "1A2B");
        ReflectionTestUtils.setField(client, "clientSecret", "12AB");
		injectHttpClient();
	}

	@Test
	public void testBrokenUri() {
		//given
		ReflectionTestUtils.setField(client, "endpoint", "{}://");

		//when
		String response = client.execute(getRequestMockData(Operation.EXPLORE));

		//then
		assertNull(response);
	}


	@Test
	public void testCallIsExecutedToTheCorrectEndpoint() throws HttpException, IOException {
		//when
		client.execute(getRequestMockData(Operation.EXPLORE));

		//then
		ArgumentCaptor<GetMethod> argument = ArgumentCaptor.forClass(GetMethod.class);
		verify(mockHttpClient).executeMethod(argument.capture());
		assertEquals("https://api.foursquare/explore?client_id=1A2B&client_secret=12AB&near=london&radius=20&limit=5&v=v2",
				argument.getValue().getURI().toString());
	}

	@Test
	public void testUnsupportedOperation() {
		assertNull(client.execute(getRequestMockData(null)));
	}

	private void injectHttpClient() {
		try {
			final Field field = FoursquareClientImpl.class.getDeclaredField("httpClient");
			field.setAccessible(true);
			field.set(client, mockHttpClient);
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			fail("Failed to inject mock http client: " + ex);
		}
	}
}
