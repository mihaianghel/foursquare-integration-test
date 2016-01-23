package com.andigital.foursquare.convert;

import com.andigital.foursquare.domain.RequestParams;
import com.andigital.foursquare.dto.RequestParamsDTO;
import com.andigital.foursquare.util.Operation;
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