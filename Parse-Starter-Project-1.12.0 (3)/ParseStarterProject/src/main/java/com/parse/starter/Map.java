package com.parse.starter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;
import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.google.android.gms.maps.*;



import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaimie on 1/23/2016.
 */

public class Map extends FragmentActivity implements OnMapReadyCallback, LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    GoogleMap map;
    GoogleApiClient client;
    Location currentLocation;
    LocationRequest request;
    boolean requestingLocationUpdates = true;
    Double zoomLat;
    Double zoomLong;

    /**
     * zooms in on given latitude and longitude
     * @param lat latitude of position
     * @param lng longitude of position
     */
    void zoomToPosition(double lat, double lng) {
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 18));
        LatLng coordinate = new LatLng(lat, lng);
        if (coordinate != null) {
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 18);
            if (yourLocation != null && map != null) {
                map.animateCamera(yourLocation);
            }
        }
    }

    /**
     * creates new markers given a title string and latitude and longitude
     * @param s the title for the marker
     * @param lat the latitude of the position
     * @param lng the longitude of the position
     * @return an array of newly created markers; marker[0] is banner, marker[1] is flat
     */
    Marker[] createNewMarkers(String s, double lat, double lng) {
        Marker[] markers = new Marker[2];
        markers[0] = map.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title(s)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.scroll)));
        markers[1] = map.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title(s)
                .flat(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.foot)));
        return markers;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapp);
        mapFragment.getMapAsync(this);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        findViewById(R.id.logout).setOnClickListener(this);

        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        request = new LocationRequest();
        request.setInterval(250)
                .setFastestInterval(100)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //This should be the latitude and longitude from zooming in on client
        //I'm not sure what it'll do if you just open the map
        Intent x = getIntent();
       if (x != null) {
            zoomLat = (Double)(x.getDoubleExtra("lat", 0.0));
            zoomLong = (Double)(x.getDoubleExtra("long", 0.0));
            if (zoomLat != null && zoomLong != null) zoomToPosition(zoomLat, zoomLong);
        }
    }

    @Override
    public void onMapReady(GoogleMap gmap) {
        map = gmap;
        // change dimensions as necessary
        // uncomment and replace "url here" with image url
        /*TileProvider tileProvider = new UrlTileProvider(256, 256) {
            @Override
            public URL getTileUrl(int x, int y, int zoom) {
                try {
                    return new URL("url here");
                } catch (MalformedURLException e) {
                    return null;
                }
            }
        };

        TileOverlay tileOverlay = map.addTileOverlay(new TileOverlayOptions()
                .tileProvider(tileProvider));*/

        // replace with map center
        LatLng place = new LatLng(39.95, -75.12);

        map.getUiSettings().setTiltGesturesEnabled(false);
        map.getUiSettings().setRotateGesturesEnabled(false);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.95, -75.19), 18));
        //zoomToPosition(39.95, -75.19);

        final ParseUser p = ParseUser.getCurrentUser();

        List<ParseUser> arrayList1 = (ArrayList<ParseUser>) p.get("friends");

        if (arrayList1 == null) {
            arrayList1 = new ArrayList<ParseUser>();
        }

        final List<ParseUser> arrayList = arrayList1;
        final List<String> list = new ArrayList<String>();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> t, ParseException e) {
                for (ParseUser u : t) {
                    if (arrayList.contains(u)) {
                        if (u != null) {
                            if (u.get("Latitude") != null && u.get("Longitude") != null) {
                                createNewMarkers((String) u.getUsername(), (double) u.get("Latitude"), (double) u.get("Longitude"));
                            }
                        }
                    }
                }
            }
        });
        if (p.get("Latitude") != null && p.get("Longitude") != null) {
            createNewMarkers(p.getUsername(), (double) p.get("Latitude"), (double) p.get("Longitude"));
            System.out.println("self marker made");
        }

        // uncomment and replace filename with map image
        /*GroundOverlayOptions groundOverlay = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.[filename]));
                .position(place, 200f) // float is width in meters
                .zIndex(1);
        map.addGroundOverlay(groundOverlay);*/
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if(requestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    protected void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);
        } else {
            // request permission
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        //
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        //
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        ParseUser p = ParseUser.getCurrentUser();
        p.put("Latitude", location.getLatitude());
        p.put("Longitude", location.getLongitude());
        updateMarker();
    }

    public void updateMarker() {
        LatLng position = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        //temporary code (there are no markers right now)
        /*if (mark1 == null && mark2 == null) {
            mark2 = mMap.addMarker(new MarkerOptions()
                    .position(position)
                    .flat(true)
                    .title("yo")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.smallbananas)));
            mark1 = mMap.addMarker(new MarkerOptions()
                    .position(position)
                    .title("yo")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.scroll)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 20));
        } else {
            mark1.setPosition(position);
            mark2.setPosition(position);
            double lat = position.latitude - mark2.getPosition().latitude;
            double lng = position.longitude - mark2.getPosition().longitude;
            mark2.setRotation(mark2.getRotation() + (float) Math.atan2(lat, lng));
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (client.isConnected() && !requestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        client.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void go(View view) {
        Intent intent = new Intent (this, AddorDeleteFriends.class);
        startActivity(intent);
    }

    public void findFriends(View view) {
        Intent intent = new Intent(this, LocateFriend.class);
        startActivity(intent);
    }

    ///////////////

    protected static final int REQUEST_OK = 1;


    @Override
    public void onClick(View v) {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        try {
            startActivityForResult(i, REQUEST_OK);
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (thingsYouSaid.contains("Mischief managed.")) {
                System.out.println("hello");
                ParseUser p = ParseUser.getCurrentUser();
                p.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        System.out.println("got here");
                        Intent intent = new Intent(Map.this, MainActivity.class);
                        System.out.println("working");
                        startActivity(intent);
                    }
                });
            } else {
                //FIX THIS DREADFUL HACKERY
                ParseUser p = ParseUser.getCurrentUser();
                p.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        System.out.println("got here");
                        Intent intent = new Intent(Map.this, MainActivity.class);
                        System.out.println("working");
                        startActivity(intent);
                    }
                });
            }
        }
    }

}
