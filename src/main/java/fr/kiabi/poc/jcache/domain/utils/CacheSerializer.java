/*
 * File : CacheSerializer.java
 *
 * Goal : Class CacheSerializer.
 *
 * History :
 * 2017.02.03 Initial creation (SFR)
 * 
 */
package fr.kiabi.poc.jcache.domain.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.kiabi.poc.jcache.domain.DeliveryPoint;
import java.io.IOException;

/**
 * Class CacheSerializer.
 *
 * @author SFR
 * @version 0.1.0
 * @since 2017.02.03
 */
public class CacheSerializer {

    private ObjectMapper mapper;

    private CacheSerializer() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    public static CacheSerializer getSingleton() {
       return CacheSerializerHolder.instance;
    }

    public byte[] serialize(DeliveryPoint point) {
        byte[] retBytes = null;
        try {
            retBytes = mapper.writeValueAsBytes(point);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return retBytes;
    }

    public DeliveryPoint deSerialize(byte[] bytes) {
        DeliveryPoint point = null;
        try {
            point = mapper.readValue(bytes, DeliveryPoint.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return point;
    }

    private static class CacheSerializerHolder {
        private final static CacheSerializer instance = new CacheSerializer();
    }

}

