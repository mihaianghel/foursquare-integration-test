package com.andigital.foursquare.serialization.impl;

import com.andigital.foursquare.domain.*;
import com.andigital.foursquare.serialization.JSONDeserializer;
import org.apache.commons.httpclient.HttpStatus;
import org.junit.Test;

import java.io.IOException;

import static com.andigital.foursquare.util.TestDataProvider.getMockResponseFromFoursquareAPI;
import static org.junit.Assert.*;

/**
 * Test for {@link JSONDeserializer}
 * @author mihaianghel
 */
public class JSONDeserializerTest {

	@Test
	public void deserializeResponseFromFoursquareAPI() throws IOException {
        AbstractFoursquareResponse response = JSONDeserializer.fromString(getMockResponseFromFoursquareAPI(), Explore.class);

        assertTrue(response instanceof Explore);
        assertResponse(response);
	}

    private void assertResponse(final AbstractFoursquareResponse foursquareResponse) {
        assertEquals(HttpStatus.SC_OK, foursquareResponse.getMeta().getCode());
        assertNull(foursquareResponse.getMeta().getErrorDetail());
        final Response response = foursquareResponse.getResponse();
        final Group group = response.getGroups().iterator().next();
        assertEquals(1, response.getGroups().size());
        assertEquals(2, group.getItems().size());
        group.getItems().stream().forEach(item -> {
            final Venue v = item.getVenue();
            final String name = v.getName();
            if ("Millennium Park".equals(name)) {
                assertEquals("(312) 742-1168", v.getFormattedPhone());
                assertEquals(108831, v.getCheckinsCount());
                assertTrue(v.getAddress().contains("201 E Randolph St (btwn Columbus Dr & Michigan Ave)"));
                assertTrue(v.getAddress().contains("Chicago, IL 60601"));
                assertTrue(v.getAddress().contains("Statele Unite"));
            } else if ("Chicago Riverwalk".equals(name)) {
                assertEquals(null, v.getFormattedPhone());
                assertEquals(12827, v.getCheckinsCount());
                assertTrue(v.getAddress().contains("Chicago River (btwn Lake Shore Dr & Canal St)"));
                assertTrue(v.getAddress().contains("Chicago, IL 60601"));
                assertTrue(v.getAddress().contains("Statele Unite"));
            } else {
                fail();
            }
        });
    }

}
