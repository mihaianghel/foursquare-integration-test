package com.andigital.foursquare.service.impl;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.andigital.foursquare.client.AbstractFoursquareClient;
import com.andigital.foursquare.model.AbstractModel;
import com.andigital.foursquare.model.ExploreResponseModelObject;
import com.andigital.foursquare.model.RequestModelObject;
import com.andigital.foursquare.serialization.impl.JSONDeserializerImpl;
import com.andigital.foursquare.util.Operation;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FoursquareServiceImplTest {
	
	@Mock private AbstractFoursquareClient mockClient;
	@Mock private ConcurrentHashMap<RequestModelObject, String> mockCache;
	@InjectMocks private FoursquareServiceImpl service = new FoursquareServiceImpl();
	
	@Before
	public void setUp() {
		when(mockCache.containsKey(getRequestMockData())).thenReturn(true);
		when(mockCache.get(getRequestMockData())).thenReturn(mockResponseFromFoursquare);
		when(mockClient.execute(getRequestMockData(), Operation.EXPLORE)).thenReturn(mockResponseFromFoursquare);
		
		injectMockCache();
		injectDeserializer();
	}
	
	@Test
	public void requestGoesToFoursquare() {
		//given
		when(mockCache.containsKey(getRequestMockData())).thenReturn(false).thenReturn(false);
		
		//when
		Collection<AbstractModel> result = service.execute("london", 20, 5, Operation.EXPLORE);
	
		assertEquals(2, result.size());
		verify(mockCache, times(1)).put(getRequestMockData(), mockResponseFromFoursquare);
	}
	
	@Test
	public void requestGoesToCache() {
		//when
		Collection<AbstractModel> result = service.execute("london", 20, 5, Operation.EXPLORE);
	
		assertEquals(2, result.size());
		verify(mockCache, times(0)).put(any(RequestModelObject.class), anyString());
	}
	
	@Test
	public void rsponseCannotBeUnmarshalled() {
		//given
		when(mockCache.get(getRequestMockData())).thenReturn("{broken response}");
		
		//when
		Collection<AbstractModel> result = service.execute("london", 20, 5, Operation.EXPLORE);
	
		assertTrue(result.isEmpty());
	}
	
	private void injectMockCache() {
		try {
			final Field field = FoursquareServiceImpl.class.getDeclaredField("CACHE");
			field.setAccessible(true);
			field.set(service, mockCache);
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			fail("Failed to inject mock thread pool executor: " + ex);
		}
	}
	
	private void injectDeserializer() {
		try {
			final Field field = FoursquareServiceImpl.class.getDeclaredField("jsonDeserializer");
			field.setAccessible(true);
			field.set(service, new JSONDeserializerImpl());
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			fail("Failed to inject mock thread pool executor: " + ex);
		}
	}
	
	private RequestModelObject getRequestMockData() {
		return new RequestModelObject("london", 20, 5);
	}
	
	private List<AbstractModel> getResponseMockData() {
		ExploreResponseModelObject o1 = new ExploreResponseModelObject("n1", "c1", null, "123");
		ExploreResponseModelObject o2 = new ExploreResponseModelObject("n2", "c2", null, "1234");
		return Arrays.asList(o1, o2);
	}
	
	private static final String mockResponseFromFoursquare = "{meta:{code:200,requestId:\"564734f8498e71d5658566a3\"},response:{suggestedFilters:{header:\"Tap to show:\",filters:[{name:\"Open now\",key:\"openNow\"}]},geocode:{what:\"\",where:\"london\",center:{lat:51.50853,lng:-0.12574},displayString:\"London, Greater London, United Kingdom\",cc:\"GB\",geometry:{bounds:{ne:{lat:51.691643999655895,lng:0.33418999705203406},sw:{lat:51.28467404417054,lng:-0.5085579279369435}}},slug:\"london\",longId:\"72057594040571679\"},warning:{text:\"There aren't a lot of results near you. Try something more general, reset your filters, or expand the search area.\"},headerLocation:\"London\",headerFullLocation:\"London\",headerLocationGranularity:\"city\",totalResults:12,suggestedBounds:{ne:{lat:51.50999773686964,lng:-0.12165625602933945},sw:{lat:51.507298077084194,lng:-0.13004548168733537}},groups:[{type:\"Recommended Places\",name:\"recommended\",items:[{reasons:{count:0,items:[{summary:\"This spot is popular\",type:\"general\",reasonName:\"globalInteractionReason\"}]},venue:{id:\"4b77eb6ff964a520c4ae2ee3\",name:\"Starbucks\",contact:{phone:\"+442078366850\",formattedPhone:\"+44 20 7836 6850\",twitter:\"starbucksuk\"},location:{address:\"442 Strand\",crossStreet:\"at Charing Cross\",lat:51.508755813953826,lng:-0.1254737377166748,postalCode:\"WC2R 0QU\",cc:\"GB\",city:\"Londra\",state:\"Greater London\",country:\"Marea Britanie\",formattedAddress:[\"442 Strand (at Charing Cross)\",\"Londra\",\"Greater London\",\"WC2R 0QU\",\"Marea Britanie\"]},categories:[{id:\"4bf58dd8d48988d1e0931735\",name:\"Coffee Shop\",pluralName:\"Coffee Shops\",shortName:\"Coffee Shop\",icon:{prefix:\"https://ss3.4sqi.net/img/categories_v2/food/coffeeshop_\",suffix:\".png\"},primary:true}],verified:true,stats:{checkinsCount:3711,usersCount:2405,tipCount:25},url:\"http://starbucks.co.uk\",price:{tier:1,message:\"Cheap\",currency:\"£\"},rating:6.7,ratingSignals:153,allowMenuUrlEdit:true,hours:{status:\"Open until 9:30 PM\",isOpen:true},photos:{count:0,groups:[]},storeId:\"12286\",hereNow:{count:0,summary:\"Nobody here\",groups:[]}},flags:{outsideRadius:true},tips:[{id:\"4b8323fd70c603bba7da92b4\",createdAt:1266885629,text:\"This branch isn't as big as it looks so, if you want a table, make sure there is a free one before you queue.\",type:\"user\",url:\"http://maps.google.co.uk/maps/place?hl=en&rlz=1C1GGLS_en-GBGB351GB351&um=1&ie=UTF-8&q=starbucks+the+strand+london++442+442+Strand+London&fb=1&gl=uk&hq=starbucks&hnear=the+strand+london++442+442+Strand+London&cid=1810657611921279771\",canonicalUrl:\"https://foursquare.com/item/4b8323fd70c603bba7da92b4\",likes:{count:9,groups:[],summary:\"9 likes\"},logView:true,todo:{count:1},user:{id:\"286626\",firstName:\"Jon\",lastName:\"Curnow\",gender:\"male\",photo:{prefix:\"https://irs1.4sqi.net/img/user/\",suffix:\"/DRIAH4X1SC5G5TXL.jpg\"}}}],referralId:\"e-0-4b77eb6ff964a520c4ae2ee3-0\"},{reasons:{count:0,items:[{summary:\"This spot is popular\",type:\"general\",reasonName:\"globalInteractionReason\"}]},venue:{id:\"4c5e79536ebe2d7f24d8d52e\",name:\"Breadline Cafe\",contact:{},location:{address:\"8 Duncannon St.\",lat:51.50854,lng:-0.126228,postalCode:\"WC2 N 4\",cc:\"GB\",city:\"Londra\",state:\"Greater London\",country:\"Marea Britanie\",formattedAddress:[\"8 Duncannon St.\",\"Londra\",\"Greater London\",\"WC2 N 4\",\"Marea Britanie\"]},categories:[{id:\"4bf58dd8d48988d16d941735\",name:\"Café\",pluralName:\"Cafés\",shortName:\"Café\",icon:{prefix:\"https://ss3.4sqi.net/img/categories_v2/food/cafe_\",suffix:\".png\"},primary:true}],verified:false,stats:{checkinsCount:95,usersCount:60,tipCount:3},price:{tier:1,message:\"Cheap\",currency:\"£\"},allowMenuUrlEdit:true,photos:{count:0,groups:[]},hereNow:{count:0,summary:\"Nobody here\",groups:[]}},flags:{outsideRadius:true},tips:[{id:\"4e5bd607091a1023bdff70f8\",createdAt:1314641415,text:\"Great bacon sandwitches\",type:\"user\",canonicalUrl:\"https://foursquare.com/item/4e5bd607091a1023bdff70f8\",likes:{count:2,groups:[],summary:\"2 likes\"},logView:true,todo:{count:4},user:{id:\"12702772\",firstName:\"Charlie\",lastName:\"Nuttall\",gender:\"male\",photo:{prefix:\"https://irs3.4sqi.net/img/user/\",suffix:\"/12702772-IDFT13KOSJMWOKRC.jpg\"}}}],referralId:\"e-0-4c5e79536ebe2d7f24d8d52e-1\"}]}]}}";
}
