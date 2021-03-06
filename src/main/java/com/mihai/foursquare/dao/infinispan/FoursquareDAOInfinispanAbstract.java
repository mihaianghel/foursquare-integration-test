package com.mihai.foursquare.dao.infinispan;

import com.mihai.foursquare.client.FoursquareClient;
import com.mihai.foursquare.dao.FoursquareDAO;
import com.mihai.foursquare.domain.AbstractFoursquareResponse;
import com.mihai.foursquare.domain.Explore;
import com.mihai.foursquare.domain.RequestParams;
import com.mihai.foursquare.serialization.JSONDeserializer;
import org.apache.commons.lang.StringUtils;
import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Abstract infinisplan implementation of FoursquareDAO
 */
public abstract class FoursquareDAOInfinispanAbstract implements FoursquareDAO {

    protected static final Logger LOGGER = LoggerFactory.getLogger(FoursquareDAOInfinispanAbstract.class);

    protected Cache<RequestParams, AbstractFoursquareResponse> cache;

    @Autowired
    private FoursquareClient foursquareClient;

    public abstract void flush();

    @PostConstruct
    public abstract void init() throws IOException;

    @Override
    public AbstractFoursquareResponse getFoursquareMetadata(RequestParams requestParams) {
        final AbstractFoursquareResponse abstractFoursquareResponse = cache.get(requestParams);
        if (abstractFoursquareResponse == null) {
            final String response = foursquareClient.execute(requestParams);
            if (StringUtils.isNotBlank(response)) {
                cache.put(requestParams, JSONDeserializer.fromString(response, Explore.class));
                return cache.get(requestParams);
            } else {
                return null;
            }
        } else {
            return cache.get(requestParams);
        }
    }
}
