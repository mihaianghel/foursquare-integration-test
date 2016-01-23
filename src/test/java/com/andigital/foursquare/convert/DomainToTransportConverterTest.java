package com.andigital.foursquare.convert;

import com.andigital.foursquare.domain.Response;
import com.andigital.foursquare.dto.ExploreResponseDTO;
import com.andigital.foursquare.dto.ResponseDTO;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;

import static com.andigital.foursquare.util.TestDataProvider.getFoursquareParsedData;
import static org.junit.Assert.*;

public class DomainToTransportConverterTest {

    private final DomainToTransportConverter converter = new DomainToTransportConverter();

    @Test
    public void testSuccessfulConversion() throws IOException {
        final Collection<ResponseDTO> params = converter.convert(getMockResponse());
        params.stream().forEach(
              r -> {
                  assertTrue(r instanceof ExploreResponseDTO);
                  final ExploreResponseDTO dto = (ExploreResponseDTO) r;
                  final String name = dto.getName();
                  if ("Millennium Park".equals(name)) {
                      assertTrue(dto.getAddress().contains("201 E Randolph St (btwn Columbus Dr & Michigan Ave)"));
                      assertTrue(dto.getAddress().contains("Chicago, IL 60601"));
                      assertTrue(dto.getAddress().contains("Statele Unite"));
                      assertEquals("(312) 742-1168", dto.getContactNumber());
                      assertEquals(108831, dto.getCheckins());
                  } else if ("Chicago Riverwalk".equals(name)) {
                      assertTrue(dto.getAddress().contains("Chicago River (btwn Lake Shore Dr & Canal St)"));
                      assertTrue(dto.getAddress().contains("Chicago, IL 60601"));
                      assertTrue(dto.getAddress().contains("Statele Unite"));
                      assertEquals(null, dto.getContactNumber());
                      assertEquals(12827, dto.getCheckins());
                  } else {
                      fail();
                  }

              }
        );
    }

    private Response getMockResponse() throws IOException {
        return getFoursquareParsedData().getResponse();
    }

}