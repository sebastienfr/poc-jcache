/*
 * File : CacheService.java
 *
 * Goal : Class CacheService.
 *
 * History :
 * 2017.03.06 Initial creation (SFR)
 * 
 */
package fr.kiabi.poc.jcache.service;

import fr.kiabi.poc.jcache.domain.CacheValueDecorator;
import fr.kiabi.poc.jcache.domain.DeliveryPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Class CacheService.
 *
 * @author SFR
 * @version 0.1.0
 * @since 2017.03.06
 */
public class CacheService {

    private final Logger log = LoggerFactory.getLogger(CacheService.class);

    CacheManager cacheManager;
    Cache<Long, CacheValueDecorator<DeliveryPoint>> primaryCache;
    Cache<Long, CacheValueDecorator<DeliveryPoint>> secondaryCache;

    private static final String PRIMARY_CACHE = "primary";
    private static final String SECONDARY_CACHE = "secondary";

    public CacheService() {

        // default empty configuration
        MutableConfiguration<Long, CacheValueDecorator<DeliveryPoint>> config = new MutableConfiguration<>();
        //config.setManagementEnabled(true);
        config.setStatisticsEnabled(true);
        // remove from cache not accessed last minute
        //config.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(new Duration(TimeUnit.MINUTES, 1)));
        // remove from cache if created more than 30s
        //config.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 30)));

        // YAML config
        URI redissonConfigUri = null;
        try {
            URL url = this.getClass().getClassLoader().getResource("redisson-jcache.yaml");
            //URL url = this.getClass().getClassLoader().getResource("redisson-jcache.json");
            if (url == null) {
               System.err.println("unable to load configuration from resource");
            }
            redissonConfigUri = url != null ? url.toURI() : null;
        } catch (URISyntaxException e) {
            System.err.println("error loading cache configuration");
            e.printStackTrace();
        }

        // cache manager from config
        cacheManager = Caching.getCachingProvider().getCacheManager(redissonConfigUri, this.getClass().getClassLoader(), null);

        // primary cache
        primaryCache = cacheManager.createCache(PRIMARY_CACHE, config);
        // secondary cache
        secondaryCache = cacheManager.createCache(SECONDARY_CACHE, config);
    }

    public Cache<Long, CacheValueDecorator<DeliveryPoint>> getPrimaryCache() {
        return primaryCache;
    }

    public Cache<Long, CacheValueDecorator<DeliveryPoint>> getSecondaryCache() {
        return secondaryCache;
    }

}
