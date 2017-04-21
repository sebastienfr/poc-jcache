/*
 * File : CacheValueDecorator.java
 *
 * Goal : Class CacheValueDecorator.
 *
 * History :
 * 2017.04.20 Initial creation (SFR)
 * 
 */
package fr.kiabi.poc.jcache.domain;

import java.io.Serializable;

/**
 * Class CacheValueDecorator.
 *
 * @author SFR
 * @version 0.1.0
 * @since 2017.04.20
 */
public class CacheValueDecorator <T> implements Serializable {

    private static final long serialVersionUID = -5435316800576497330L;

    private T object;

    public CacheValueDecorator() {
    }

    public CacheValueDecorator(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

}