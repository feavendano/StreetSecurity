package androidmapsdefinitivo.android.bajaintec.com.androidmapsdefinitivo;

/**
 * Copyright(C) <2016> <BajaInTec>
 * This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **/

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidmapsdefinitivo.android.bajaintec.com.androidmapsdefinitivo.Control.ControlPosicion;
import androidmapsdefinitivo.android.bajaintec.com.androidmapsdefinitivo.Control.DirectionsJSONParser;
import androidmapsdefinitivo.android.bajaintec.com.androidmapsdefinitivo.Control.WebServiceTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String LANMAYOR = "LatitudMayor";
    private static final String LANMENOR = "LatitudMenor";
    private static final String LONMAYOR = "LongitudMayor";
    private static final String LONMENOR = "LongitudMenor";
    private GoogleMap mMap;
    private String calleOrigen;
    private String calleDestino;
    ArrayList<LatLng> markerPoints;
    List<PolylineOptions> lista = new ArrayList<PolylineOptions>();
    private LocationManager locationManager;
    public List<Integer> idColor = new ArrayList<Integer>();
    private LatLng point;
    private Button mBtnGuardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //Initializing array List
        calleOrigen="1";
        calleDestino="1";
        mBtnGuardar = (Button) findViewById(R.id.btnGuardarP);
        mBtnGuardar.setEnabled(false);
        markerPoints = new ArrayList<LatLng>();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location==null){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
                location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location ==null){
                    Log.d("Falla de gps: ","valio");
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }

            }
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),
                    location.getLongitude()));
            /*
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(32.595085,
                    -115.482046));
            */
            //CameraUpdate zoom = CameraUpdateFactory.zoomTo(20);
            mMap.moveCamera(center);
            //mMap.animateCamera(zoom);
            point = new LatLng(location.getAltitude(), location.getLongitude());
            getLines(location.getAltitude(),location.getLongitude());
            //getLines();
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(false);
        } catch (SecurityException e) {
            System.exit(0);
        } catch (NullPointerException e){
            System.exit(0);
        }

        final Snackbar snackStart = Snackbar
                .make(findViewById(R.id.map), "Selecciona una ruta de una calle", Snackbar
                        .LENGTH_LONG);

        snackStart.show();

        final Snackbar snackErr = Snackbar
                .make(findViewById(R.id.map), "Selecciona una sola calle", Snackbar
                        .LENGTH_LONG);

        final Snackbar snackbar = Snackbar
                .make(findViewById(R.id.map), R.string.guardar_ruta, Snackbar
                        .LENGTH_LONG);

        mBtnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, SeguridadActivity.class);
                intent.putExtra("calle", calleDestino);
                intent.putExtra("origen_n", String.valueOf(markerPoints.get(0).latitude));
                intent.putExtra("origen_w", String.valueOf(markerPoints.get(0).longitude));
                intent.putExtra("destino_n", String.valueOf(markerPoints.get(1).latitude));
                intent.putExtra("destino_w", String.valueOf(markerPoints.get(1).longitude));
                intent.putExtra("idUsuario", getIntent().getIntExtra("idUsuario", 0));
                startActivity(intent);
            }
        });

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                LatLng li = cameraPosition.target;
                LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;

                if (!bounds.contains(point)) {
                    try {
                        point = null;
                        point = new LatLng(li.latitude, li.longitude);
                        System.out.println("Sali");
                        getLines(point.latitude, point.longitude);
                        //getLines();
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                if (markerPoints.size() > 1) {
                    markerPoints.clear();
                    mMap.clear();
                    mBtnGuardar.setEnabled(false);
                    //snackbar.dismiss();
                    getLines();
                    //getLines(point.latitude,point.longitude);
                }

                markerPoints.add(point);
                /*
                try {
                    Geocoder gc = new Geocoder(getBaseContext(), Locale.getDefault());
                    List<Address> addresses = gc.getFromLocation(point.latitude, point.longitude, 1);
                    //Obtener nombres de las calles.
                    if (markerPoints.size() == 1) {
                        calleOrigen = addresses.get(0).getAddressLine(0);
                    } else if (markerPoints.size() == 2) {
                        calleDestino = addresses.get(0).getAddressLine(0);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                MarkerOptions options = new MarkerOptions();

                options.position(point);

                if (markerPoints.size() == 1) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                } else if (markerPoints.size() == 2) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    if (!calleOrigen.equalsIgnoreCase(calleDestino)) {
                        Log.d("Calles: ", calleOrigen + "//" + calleDestino);
                        Boolean msj = !calleOrigen.equalsIgnoreCase(calleDestino);
                        Log.d("Calles: ", msj.toString());
                        markerPoints.clear();
                        mMap.clear();
                        //getLines(point.latitude,point.longitude);
                        getLines();
                        snackErr.show();
                        return;
                    }
                }

                mMap.addMarker(options);

                if (markerPoints.size() >= 2) {
                    LatLng origin = markerPoints.get(0);
                    LatLng dest = markerPoints.get(1);

                    String url = getDirectionsUrl(origin, dest);

                    DownloadTask downloadTask = new DownloadTask();

                    downloadTask.execute(url);
                    snackbar.show();
                    mBtnGuardar.setEnabled(true);
                }
            }

        });
        //getLines();
        getLines(point.latitude,point.longitude);
    }

    public void getLines(){
        WebServiceTask obj = new WebServiceTask();
        String res = null;
        try {
            obj.execute("get", "3");
            res = obj.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (res != null) {
            JSONArray array;
            List<List<HashMap<String, String>>> result = null;

            try {
                array = new JSONArray(res.split("---")[0]);
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject jOrigen = array.getJSONObject(i).getJSONObject("origen");
                        JSONObject jDestino = array.getJSONObject(i).getJSONObject("destino");
                        idColor.add(array.getJSONObject(i).getInt("idColor"));

                        LatLng origenL = new LatLng(jOrigen.getDouble("lat"), jOrigen.getDouble
                                ("lon"));
                        LatLng destinoL = new LatLng(jDestino.getDouble("lat"), jDestino
                                .getDouble("lon"));

//                        LatLng origenL = new LatLng(32.516339, -116.959958 );
//                        LatLng destinoL = new LatLng(32.515211, -116.960466);

                        String url = getDirectionsUrl(origenL, destinoL);

                        DownloadTask downloadTask = new DownloadTask();

                        downloadTask.execute(url);

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            } catch (JSONException e) {

            }
        }
    }

    public void getLines(double lat,double lon){
        WebServiceTask obj = new WebServiceTask();
        ControlPosicion controlPosicion = new ControlPosicion();
        ArrayList<Double> listLanLon;
        listLanLon = controlPosicion.calculoPosicion(lat,lon);
        String res = null;
        try {
            obj.execute("POST", "5",
                    LANMAYOR,listLanLon.get(0)+"",
                    LANMENOR,listLanLon.get(1)+"",
                    LONMAYOR,listLanLon.get(2)+"",
                    LONMENOR,listLanLon.get(3)+"");
            res = obj.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (res != null) {
            JSONArray array;
            List<List<HashMap<String, String>>> result = null;

            try {
                array = new JSONArray(res.split("---")[0]);
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject jOrigen = array.getJSONObject(i).getJSONObject("origen");
                        JSONObject jDestino = array.getJSONObject(i).getJSONObject("destino");
                        idColor.add(array.getJSONObject(i).getInt("idColor"));

                        LatLng origenL = new LatLng(jOrigen.getDouble("lat"), jOrigen.getDouble
                                ("lon"));
                        LatLng destinoL = new LatLng(jDestino.getDouble("lat"), jDestino
                                .getDouble("lon"));

//                        LatLng origenL = new LatLng(32.516339, -116.959958 );
//                        LatLng destinoL = new LatLng(32.515211, -116.960466);

                        String url = getDirectionsUrl(origenL, destinoL);

                        DownloadTask downloadTask = new DownloadTask();

                        downloadTask.execute(url);

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            } catch (JSONException e) {

            }
        }
    }


    public void drawAll() {
        for (PolylineOptions lineOptions: lista) {
            mMap.addPolyline(lineOptions);
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Error downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,
            String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        public void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            //MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(20);
                lineOptions.color(Color.argb(150, 44, 192, 67));
            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions!=null){
                mMap.addPolyline(lineOptions);
                lista.add(lineOptions);
            }
        }

    }
}
