package com.example.a341group;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

// make sure to go to SDK Manager-> SDK tools and enable google play services

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    Button getDirection;
    private Polyline currentPolyline;
    String[] riderInfos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getDirection=(Button)findViewById(R.id.mapConfirmDropoff);
        Intent intent = getIntent();
        riderInfos = intent.getStringArrayExtra("riderInfo");
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync( this);
//        new FetchURL(MapActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, DriverConfirmRider.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        String start= riderInfos[1]+", Kelowna";
        String end = riderInfos[2]+", Kelonwa";
        List<Address> addressList = null;
        Geocoder geocoder = new Geocoder(this);
        try {
            addressList = geocoder.getFromLocationName(start, 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address = addressList.get(0);
        LatLng startLL = new LatLng(address.getLatitude(), address.getLongitude());
        mMap.addMarker(new MarkerOptions().position(startLL).title(start));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(startLL));
        try {
            addressList = geocoder.getFromLocationName(end, 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        address = addressList.get(0);
        LatLng endLL = new LatLng(address.getLatitude(), address.getLongitude());
        mMap.addMarker(new MarkerOptions().position(endLL).title(end));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(endLL));


        mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(startLL, endLL)
                .color(Color.BLUE)
                .geodesic(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLL, 11));
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
    public void mapConfirmDropoff(View view){
        Intent i = new Intent(this, DriverSummary.class);
        i.putExtra("riderInfo",riderInfos);
        startActivity(i);
    }
}