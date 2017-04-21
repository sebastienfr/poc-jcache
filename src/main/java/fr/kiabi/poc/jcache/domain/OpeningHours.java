/*
 * File : OpeningHours.java
 *
 * Goal : Class OpeningHours.
 *
 * History :
 * 2017.04.20 Initial creation (SFR)
 * 
 */
package fr.kiabi.poc.jcache.domain;

/**
 * Class OpeningHours.
 *
 * @author SFR
 * @version 0.1.0
 * @since 2017.04.20
 */
public class OpeningHours {

    // private final Logger log = LoggerFactory.getLogger(OpeningHours.class);

    private int id;

    private EDayPeriod period;

    private String hour;

    public OpeningHours() {
    }

    public OpeningHours(int id, EDayPeriod period, String hour) {
        this.id = id;
        this.period = period;
        this.hour = hour;
    }
}
