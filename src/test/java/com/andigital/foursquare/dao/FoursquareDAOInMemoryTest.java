package com.andigital.foursquare.dao;

import com.andigital.foursquare.client.AbstractFoursquareClient;
import com.andigital.foursquare.domain.AbstractFoursquareResponse;
import com.andigital.foursquare.domain.RequestParams;
import com.andigital.foursquare.util.Operation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

import static com.andigital.foursquare.util.TestDataProvider.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FoursquareDAOInMemoryTest {

    private static final RequestParams REQUEST_MOCK_DATA = getRequestMockData(Operation.EXPLORE);

    @Mock private AbstractFoursquareClient foursquareClient;

    @Mock private ConcurrentHashMap<RequestParams, AbstractFoursquareResponse> mockCache;

    @InjectMocks
    private FoursquareDAOInMemory foursquareDAOInMemory = new FoursquareDAOInMemory();

    @Before
    public void setUp() throws IOException {
        when(mockCache.containsKey(REQUEST_MOCK_DATA)).thenReturn(true);
        when(mockCache.get(REQUEST_MOCK_DATA)).thenReturn(getFoursquareParsedData());
        when(foursquareClient.execute(REQUEST_MOCK_DATA)).thenReturn(getMockResponseFromFoursquareAPI());

        injectMockCache();
    }

    @Test
    public void requestGoesToFoursquare() throws IOException {
        //given
        when(mockCache.containsKey(REQUEST_MOCK_DATA)).thenReturn(false).thenReturn(false);

        //when
        AbstractFoursquareResponse result = foursquareDAOInMemory.getFoursquareMetadata(REQUEST_MOCK_DATA);

        //then
        assertNotNull(result);
        verify(mockCache, times(1)).put(eq(REQUEST_MOCK_DATA), any(AbstractFoursquareResponse.class));
    }

    @Test
    public void requestGoesToCache() {
        //when
        AbstractFoursquareResponse result = foursquareDAOInMemory.getFoursquareMetadata(REQUEST_MOCK_DATA);

        //then
        assertNotNull(result);
        verify(mockCache, times(0)).put(any(RequestParams.class), any(AbstractFoursquareResponse.class));
    }

    private void injectMockCache() {
        try {
            final Field field = FoursquareDAOInMemory.class.getDeclaredField("CACHE");
            field.setAccessible(true);
            field.set(foursquareDAOInMemory, mockCache);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            fail("Failed to inject mock cache: " + ex);
        }
    }
}