package com.andigital.foursquare.dao.infinispan;

import org.infinispan.manager.DefaultCacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Infinispan implementation of local cache
 */
//@Component(value = "infinispanLocal")
public class FoursquareDAOInfinispanLocal extends FoursquareDAOInfinispanAbstract {

    @Scheduled(cron = "0 * * * * *")
    @Override
    public void flush() {
        LOGGER.info("Invalidating local cache");
        cache.clear();
    }

    @Override
    public void init() throws IOException {
        cache = new DefaultCacheManager("infinispan-local.xml").getCache("local-cache");
    }
}
