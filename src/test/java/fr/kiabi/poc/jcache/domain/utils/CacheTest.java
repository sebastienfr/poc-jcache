/*
 * File : CacheSerializerTest.java
 *
 * Goal : Class CacheSerializerTest.
 *
 * History :
 * 2017.02.06 Initial creation (SFR)
 * 
 */
package fr.kiabi.poc.jcache.domain.utils;

import fr.kiabi.poc.jcache.domain.CacheValueDecorator;
import fr.kiabi.poc.jcache.domain.DeliveryPoint;
import fr.kiabi.poc.jcache.domain.EDayPeriod;
import fr.kiabi.poc.jcache.domain.OpeningHours;
import fr.kiabi.poc.jcache.service.CacheService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Class CacheSerializerTest.
 *
 * @author SFR
 * @version 0.1.0
 * @since 2017.02.06
 */
public class CacheTest {

    private final Logger log = LoggerFactory.getLogger(CacheTest.class);

    private DeliveryPoint point;
    private CacheService cacheService;

    private static final int MAX_ENTRIES = 30_000;
    private static final int MODULO_STAT = 5000;
    private static final int POOL_SIZE = 10;


    @Before
    public void buildDeliveryPoint() {
        point = new DeliveryPoint();
        point.setId(1L);
        point.setCode("TEST1");
        point.setName("TEST");
        point.setType("TYPE");
        point.setUrl("localhost/test");
        point.setZoneCode("MY_ZONE");
        point.setCarrierCode("CA_CODE");
        point.setCountryCode("FR");
        point.setClosingDate(null);
        point.setOpeningDate(new Date());
        point.setCreationDate(new Date());
        point.setLastModificationDate(point.getCreationDate());
        point.setDisabled(true);
        point.setDisplayable(true);
        point.setLatitude(44.123);
        point.setLongitude(2.445);

        Set<OpeningHours> hours = new HashSet<>();
        for (EDayPeriod period : EDayPeriod.values()) {
            hours.add(new OpeningHours((int)(20_000_000 * Math.random()), period, "9:00"));
        }
        point.setOpeningHours(hours);


        HashMap<String, String> props = new HashMap<>();
        for (int i = 0; i < 1_000; i++) {
            props.put("sqmdklfjsqmdlfjkqsmdfkjsk"+Math.random(), "smdlkfj qsldkfjqsdjfkmogfiudfkbnvqkdfgjkqdmlkfb");
        }
        point.setProperties(props);

        cacheService = new CacheService();
    }

    @Test
    public void serializeTest() {
        byte[] serialized = CacheSerializer.getSingleton().serialize(point);
        DeliveryPoint ret = CacheSerializer.getSingleton().deSerialize(serialized);
        Assert.assertEquals(point, ret);
    }


    @Test
    public void cacheFullTest() {
        Cache<Long, CacheValueDecorator<DeliveryPoint>> primaryCache = cacheService.getPrimaryCache();
        Cache<Long, CacheValueDecorator<DeliveryPoint>> secondaryCache = cacheService.getSecondaryCache();
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX_ENTRIES; i++) {
            if (i % MODULO_STAT == 0) {
                System.out.println(i+" elements put");
            }
            point.setName("TEST_"+i);
            point.setLatitude(90*Math.random());
            point.setLongitude(180*Math.random());
            CacheValueDecorator<DeliveryPoint> value = new CacheValueDecorator(point);
            try {
                primaryCache.put((long)i, value);
                secondaryCache.put((long)i, value);
            } catch (Exception e) {
                System.err.println("error putting object in cache");
                e.printStackTrace();
            }
        }
        long put = System.currentTimeMillis();
        for (int i = MAX_ENTRIES; i >= 0; i--) {
            try {
                CacheValueDecorator<DeliveryPoint> point1 = primaryCache.get((long)i);
                CacheValueDecorator<DeliveryPoint> point2 = secondaryCache.get((long)i);
            } catch (Exception e) {
                System.err.println("error getting object in cache");
                e.printStackTrace();
            }
            if (i % MODULO_STAT == 0) {
                System.out.println(i+" elements get");
            }
        }
        long get = System.currentTimeMillis();
        double getTime = (get-put)/(double)MAX_ENTRIES;
        double putTime = (put-start)/(double)MAX_ENTRIES;
        System.out.println("Average Put time "+putTime+" ms, average Get time "+getTime+" ms");
    }

    @Test
    public void cacheMultiThreadTest() {
        Cache<Long, CacheValueDecorator<DeliveryPoint>> primaryCache = cacheService.getPrimaryCache();
        Cache<Long, CacheValueDecorator<DeliveryPoint>> secondaryCache = cacheService.getSecondaryCache();

        //limit the number of actual threads
        ExecutorService service = Executors.newFixedThreadPool(POOL_SIZE);
        List<Future<Runnable>> futures = new ArrayList<>();

        Future f = service.submit(new CacheTester(primaryCache, false));
        futures.add(f);
        f = service.submit(new CacheTester(secondaryCache, false));
        futures.add(f);

        for (int i = 0; i < (POOL_SIZE-2)/2; i++) {
            f = service.submit(new CacheTester(primaryCache, true));
            futures.add(f);
            f = service.submit(new CacheTester(secondaryCache, true));
            futures.add(f);
        }

        // wait for all tasks to complete before continuing
        for (Future<Runnable> fut : futures) {
            try {
                fut.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        //shut down the executor service so that this thread can exit
        service.shutdownNow();
    }

    private class CacheTester implements Runnable {

        private Cache<Long, CacheValueDecorator<DeliveryPoint>> cache;
        private boolean read;

        public CacheTester(Cache<Long, CacheValueDecorator<DeliveryPoint>> cache, boolean read) {
            this.cache = cache;
            this.read = read;
        }

        @Override
        public void run() {
            if (read) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long start = System.currentTimeMillis();
                for (int i = 0; i < MAX_ENTRIES; i++) {
                    try {
                        CacheValueDecorator<DeliveryPoint> point1 = cache.get((long)i);
                        DeliveryPoint point = point1.getObject();
                        if (point == null) {
                            System.out.println("unable to find point");
                        }
                    } catch (Exception e) {
                        System.err.println("error getting object in cache");
                        e.printStackTrace();
                    }
                    if (i % MODULO_STAT == 0) {
                        System.out.println(i+" elements get");
                    }
                }

                long get = System.currentTimeMillis();
                double getTime = (get-start)/(double)MAX_ENTRIES;
                System.out.println("Average Get time "+getTime+" ms");
            } else {
                long start = System.currentTimeMillis();
                for (int i = 0; i < MAX_ENTRIES; i++) {
                    if (i % MODULO_STAT == 0) {
                        System.out.println(i+" elements put");
                    }
                    point.setName("TEST_"+i);
                    point.setLatitude(90*Math.random());
                    point.setLongitude(180*Math.random());
                    CacheValueDecorator<DeliveryPoint> value = new CacheValueDecorator<>(point);
                    try {
                        cache.put((long)i, value);
                    } catch (Exception e) {
                        System.err.println("error putting object in cache");
                        e.printStackTrace();
                    }
                }

                long put = System.currentTimeMillis();
                double putTime = (put-start)/(double)MAX_ENTRIES;
                System.out.println("Average Put time "+putTime+" ms");
            }

        }
    }

}
