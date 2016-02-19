package com.mihai.foursquare.convert;

import com.mihai.foursquare.domain.RequestParams;
import com.mihai.foursquare.dto.RequestParamsDTO;
import com.mihai.foursquare.util.Operation;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransportToDomainConverterTest {

    private final TransportToDomainConverter converter = new TransportToDomainConverter();

    @Test
    public void testSuccessfulConversion() {
        final RequestParams params = converter.convert(new RequestParamsDTO("London", 10, 10, Operation.EXPLORE));
        assertEquals("London", params.getLocation());
        assertEquals(new Integer(10), params.getLimit());
        assertEquals(new Integer(10), params.getRadius());
        assertEquals(Operation.EXPLORE, params.getOperation());
    }
}