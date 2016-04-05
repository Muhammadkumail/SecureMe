package com.secureme.secureme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapActivity extends AppCompatActivity {

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        get_location();

    }

    private void get_location() {
        float lat, lng;


        lat = (float) 24.9324;
        lng = (float) 67.1127;

        try {
            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.google_map_fragment)).getMap();
            MarkerOptions k = new MarkerOptions().position(new LatLng(lat, lng)).title("Ned University").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_google_marker)).draggable(true).snippet("");
            map.addMarker(k);
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.setMyLocationEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setAllGesturesEnabled(true);
            map.getUiSettings().setCompassEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
            map.getUiSettings().setRotateGesturesEnabled(true);
            map.getUiSettings().setTiltGesturesEnabled(true);

            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lat, lng)).zoom(12).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onClick_Google_map(View view) {

        Toast.makeText(GoogleMapActivity.this, "Searching...", Toast.LENGTH_LONG).show();
        get_location();

    }


}
