package com.mihai.foursquare.service.impl;

import com.mihai.foursquare.dao.FoursquareDAO;
import com.mihai.foursquare.domain.AbstractFoursquareResponse;
import com.mihai.foursquare.domain.RequestParams;
import com.mihai.foursquare.dto.ExploreResponseDTO;
import com.mihai.foursquare.dto.RequestParamsDTO;
import com.mihai.foursquare.dto.ResponseDTO;
import com.mihai.foursquare.util.Operation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


/**
 * Test for {@link FoursquareServiceImpl}
 * @author mihaianghel
 */
@RunWith(MockitoJUnitRunner.class)
public class FoursquareServiceImplTest {

	@Mock private FoursquareDAO foursquareDAO;
	@InjectMocks private FoursquareServiceImpl service = new FoursquareServiceImpl();

    @Test
    public void testEmptyResponseFromDAOLayer() {
        //given
        when(foursquareDAO.getFoursquareMetadata(any(RequestParams.class))).thenReturn(null);

        //when
        Collection<ResponseDTO> result = service.execute(new RequestParamsDTO("london", 20, 5, Operation.EXPLORE));

        assertEquals(0, result.size());
    }

	@Test
	public void testCallIsCorrectlyMade() throws IOException {
		//given
		final AbstractFoursquareResponse response = getFoursquareParsedData();
		when(foursquareDAO.getFoursquareMetadata(any(RequestParams.class))).thenReturn(response);

		//when
		Collection<ResponseDTO> result = service.execute(new RequestParamsDTO("london", 20, 5, Operation.EXPLORE));

		//then
		result.stream().forEach(
			r -> {
				assertTrue(r instanceof ExploreResponseDTO);
				final ExploreResponseDTO dto = (ExploreResponseDTO) r;
				final String name = dto.getName();
				if ("Millennium Park".equals(name)) {
					assertEquals("(312) 742-1168", dto.getContactNumber());
					assertEquals(108831, dto.getCheckins());
					assertTrue(dto.getAddress().contains("201 E Randolph St (btwn Columbus Dr & Michigan Ave)"));
					assertTrue(dto.getAddress().contains("Chicago, IL 60601"));
					assertTrue(dto.getAddress().contains("Statele Unite"));
				} else if ("Chicago Riverwalk".equals(name)) {
					assertEquals(null, dto.getContactNumber());
					assertEquals(12827, dto.getCheckins());
					assertTrue(dto.getAddress().contains("Chicago River (btwn Lake Shore Dr & Canal St)"));
					assertTrue(dto.getAddress().contains("Chicago, IL 60601"));
					assertTrue(dto.getAddress().contains("Statele Unite"));
				} else {
					fail();
				}

			}
		);
	}


}
