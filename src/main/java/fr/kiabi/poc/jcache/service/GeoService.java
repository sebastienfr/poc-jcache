/*
 * File : GeoService.java
 *
 * Goal : Class GeoService.
 *
 * History :
 * 2017.05.17 Initial creation (SFR)
 * 
 */
package fr.kiabi.poc.jcache.service;

import com.vividsolutions.jts.geom.Coordinate;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.GeodeticCalculator;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.referencing.datum.DefaultEllipsoid;

import java.awt.geom.Point2D;

/**
 * Class GeoService.
 *
 * @author SFR
 * @version 0.1.0
 * @since 2017.05.17
 */
public class GeoService {

    // private final Logger log = LoggerFactory.getLogger(GeoService.class);
    public static void main(String[] args) {

        /*

         pom.xml
         </dependencies>
             <dependency>
                <groupId>org.geotools</groupId>
                <artifactId>gt-main</artifactId>
                <version>17.0</version>
            </dependency>

        </dependencies>

        <repositories>
            <repository>
                <id>osgeo</id>
                <name>Open Source Geospatial Foundation Repository</name>
                <url>http://download.osgeo.org/webdav/geotools/</url>
            </repository>
            <repository>
                <snapshots>
                    <enabled>true</enabled>
                </snapshots>
                <id>boundless</id>
                <name>Boundless Maven Repository</name>
                <url>http://repo.boundlessgeo.com/main</url>
            </repository>
        </repositories>


        */
        // the following code is based on JTS.orthodromicDistance( start, end, crs )

        // assertEquals(1004560, distance, 10);

        try {
            // start end points
            Coordinate start = new Coordinate(-19.949465, 3.570224);
            Coordinate end = new Coordinate(-25.752408, -3.356814);

            // égal à tout ce qui est au-dessus
            double distance = JTS.orthodromicDistance(start, end, DefaultGeographicCRS.WGS84);
            System.out.println("Distance = " + distance+ "m");

            // idem supra
            GeodeticCalculator gc = new GeodeticCalculator(DefaultEllipsoid.WGS84);
            // longitude / latitude
            gc.setStartingPosition( JTS.toDirectPosition(start, DefaultGeographicCRS.WGS84));
            gc.setDestinationPosition( JTS.toDirectPosition(end, DefaultGeographicCRS.WGS84));

            distance = gc.getOrthodromicDistance();

            int totalmeters = (int) distance;
            int km = totalmeters / 1000;
            int meters = totalmeters - (km * 1000);
            float remaining_cm = (float) (distance - totalmeters) * 10000;
            remaining_cm = Math.round(remaining_cm);
            float cm = remaining_cm / 100;

            System.out.println("Distance = " + km + "km " + meters + "m " + cm + "cm");
            System.out.println("Distance = " + distance+ "m");

            System.out.println();

            GeodeticCalculator calc = new GeodeticCalculator();
            // mind, this is lon/lat
            calc.setStartingGeographicPoint(3.019906, 50.641544);

            for (int angle=0; angle <=360; angle+=45) {
                calc.setDirection(angle /* azimuth */, 300_000/* distance */);
                Point2D dest = calc.getDestinationGeographicPoint();
                System.out.printf("Longitude: %3.6f  Latitude: %3.6f \n", dest.getX(), dest.getY());
            }

            /*
                GeoPoint center = createPoint(50.641544, 3.019906);
                int radius = 300;


                assertEquals(53.339425, latitudeNorth, 0.0001);
                assertEquals(47.943581, latitudeSouth, 0.0001);
                assertEquals(7.276561, longitudeEast, 0.0001);
                assertEquals(-1.236763, longitudeWest, 0.0001);
            */

        } catch (Exception e) {

        }

    }

}
