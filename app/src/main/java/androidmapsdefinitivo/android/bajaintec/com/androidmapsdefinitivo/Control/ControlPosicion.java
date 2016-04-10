package androidmapsdefinitivo.android.bajaintec.com.androidmapsdefinitivo.Control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by angel on 4/4/2016.
 */
public class ControlPosicion {

    public static final double PI = 3.14159265359;
    public static final double RADIO = .5;
    public static final double RADIOTIERRA = 16000;

    public ArrayList<Double> calculoPosicion(double centerLat,double centerLng){
        double d = RADIO/RADIOTIERRA; // 6378800 is Earth radius in meters
        double lat1 = (PI/180)* centerLat;
        double lng1 = (PI/180)* centerLng;
        //comparacion del los mayores y menores

        double latMayor = 0;
        double latMenor = RADIOTIERRA;
        double lonMayor = -7764;
        double lonMenor = RADIOTIERRA;

        ArrayList<Double> listLanLon = new ArrayList<Double>();
        System.out.println("El chilo"+centerLat+" "+centerLng);
        // Go around a circle from 0 to 360 degrees, every 10 degrees or set a to your desired bearing, in degrees.
        for (int a = 1 ; a < 361 ; a++) {
            double tc = (PI/180)*a;
            /*
            double tc = (PI/180)*a;
            double y = Math.asin(Math.sin(lat1) * Math.cos(d) + Math.cos(lat1) * Math.sin(d) * Math.cos(tc));
            double dlng = Math.atan2(Math.sin(tc) * Math.sin(d) * Math.cos(lat1), Math.cos(d) - Math.sin(lat1) * Math.sin(y));
            double x = ((lng1-dlng+PI) % (2*PI)) - PI ;
            double lat = y*(180/PI);
            double lon = x*(180/PI);*/

            //double lat1 = centerLat*(180/PI);
            //double lon1 = centerLon*(180/PI);

            double lat2 = Math.asin(Math.sin(lat1) * Math.cos(d) +
                    Math.cos(lat1) * Math.sin(d) * Math.cos(tc));

            double lon2 = lng1 + Math.atan2(Math.sin(tc) * Math.sin(d) *
                            Math.cos(lat1),Math.cos(d) - Math.sin(lat1) *
                            Math.sin(lat2));

            lat2 = lat2*(180/PI);
            lon2 = lon2*(180/PI);

            if(lat2>latMayor){
                latMayor = lat2;
            }else if(lat2<latMenor){
                latMenor = lat2;
            }

            if(lon2>lonMayor){
                lonMayor = lon2;
            }else if(lon2<lonMenor){
                lonMenor = lon2;
            }

            System.out.println(lat2+" "+lon2);
            // Convert the lat and lon to pixel, if needed. (x,y)
        }
        listLanLon.add(latMayor);
        listLanLon.add(latMenor);
        listLanLon.add(lonMayor);
        listLanLon.add(lonMenor);
        System.out.println(latMayor+" "+latMenor);
        System.out.println(lonMayor+" "+lonMenor);
        return listLanLon;
    }


}
