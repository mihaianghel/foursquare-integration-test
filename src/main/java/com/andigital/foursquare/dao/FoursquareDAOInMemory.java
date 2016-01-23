package com.andigital.foursquare.dao;

import com.andigital.foursquare.client.FoursquareClient;
import com.andigital.foursquare.domain.AbstractFoursquareResponse;
import com.andigital.foursquare.domain.Explore;
import com.andigital.foursquare.domain.RequestParams;
import com.andigital.foursquare.serialization.JSONDeserializer;
import com.andigital.foursquare.util.Operation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-Memory implementation of FoursquareDAO
 */
@Component
public class FoursquareDAOInMemory implements FoursquareDAO {

    @Autowired
    private FoursquareClient foursquareClient;

    private Map<RequestParams, AbstractFoursquareResponse> CACHE = new ConcurrentHashMap();

    private static final Logger LOGGER = LoggerFactory.getLogger(FoursquareDAOInMemory.class);

    //invalidate the cache
    //set to every minute for testing purposes
    @Scheduled(cron = "0 * * * * *")
    public void flush() {
        LOGGER.info("Invalidating cache");
        CACHE.clear();
    }

    @Override
    public AbstractFoursquareResponse getFoursquareMetadata(RequestParams requestParams) {
        if (!CACHE.containsKey(requestParams)) {
            synchronized (CACHE) {
                if (!CACHE.containsKey(requestParams)) {
                    LOGGER.info("Object not found in cache. Making request to Foursquare Service");
                    final String response = foursquareClient.execute(requestParams);
                    if (StringUtils.isNotBlank(response)) {
                        AbstractFoursquareResponse abstractFoursquareResponse;
                        if (Operation.EXPLORE.equals(requestParams.getOperation())) {
                            abstractFoursquareResponse = JSONDeserializer.fromString(response, Explore.class);
                        } else {
                            abstractFoursquareResponse = null;
                        }
                        CACHE.put(requestParams, abstractFoursquareResponse);
                    } else {
                        return null;
                    }
                }
            }
        }
        return CACHE.get(requestParams);
    }

}
