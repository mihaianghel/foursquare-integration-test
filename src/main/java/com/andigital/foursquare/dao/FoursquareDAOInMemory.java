package com.andigital.foursquare.dao;

import com.andigital.foursquare.client.AbstractFoursquareClient;
import com.andigital.foursquare.model.AbstractModel;
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

@Component
public class FoursquareDAOInMemory implements FoursquareDAO {

    @Autowired
    private AbstractFoursquareClient foursquareClient;

    private Map<RequestParams, String> CACHE = new ConcurrentHashMap();

    private static final Logger LOGGER = LoggerFactory.getLogger(FoursquareDAOInMemory.class);

    //invalidate the cache
    //set to every minute for testing purposes
    @Scheduled(cron = "0 * * * * *")
    public void flush() {
        LOGGER.info("Invalidating cache");
        CACHE.clear();
    }

    @Override
    public String getFousquareMetadata(RequestParams requestModelObject) {
        if (!CACHE.containsKey(requestModelObject)) {
            synchronized (CACHE) {
                if (!CACHE.containsKey(requestModelObject)) {
                    LOGGER.info("Object not found in cache. Making request to Foursquare Service");
                    final String response = foursquareClient.execute(requestModelObject, Operation.EXPLORE);
                    if (StringUtils.isNotBlank(response)) {
                        CACHE.put(requestModelObject, response);
                    } else {
                        return response;
                    }
                }
            }
        }
        return CACHE.get(requestModelObject);
    }

}
