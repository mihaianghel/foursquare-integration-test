package com.andigital.foursquare.dao.infinispan;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Infinispan implementation of replicated cache
 */
@Component(value = "infinispanReplicated")
public class FoursquareDAOInfinispanReplicated extends FoursquareDAOInfinispanAbstract {

    @Scheduled(cron = "0 * * * * *")
    @Override
    public void flush() {
        LOGGER.info("Invalidating replicated cache");
        cache.clear();
    }

    @Override
    public void init() throws IOException {
        cache = createCacheManager().getCache();

    }

    private EmbeddedCacheManager createCacheManager() {
        EmbeddedCacheManager cacheManager = new DefaultCacheManager(
                GlobalConfigurationBuilder.defaultClusteredBuilder()
                        .transport()
                        .nodeName(randomNodeName())
                        .addProperty("configurationFile", "jgroups.xml")
                        .build(),
                new ConfigurationBuilder()
                        .clustering()
                        .cacheMode(CacheMode.REPL_SYNC)
                        .build()
        );

        return cacheManager;
    }

    private String randomNodeName() {
        return Double.toString((Math.random() + 31) / 17);
    }

}
