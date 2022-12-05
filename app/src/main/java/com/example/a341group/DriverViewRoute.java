package com.example.a341group;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

public class DriverViewRoute extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback{

    DatabaseReference passengerDbRef;

    Button backBtn;

    private GoogleMap mMap;
    MarkerOptions startLoc, endLoc;
    String startStr, endStr;
    Polyline currentPolyline;

    String documentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_view_route);

        passengerDbRef = FirebaseDatabase.getInstance().getReference().child("Passengers");

        backBtn = findViewById(R.id.backBtn);

        Intent receivedIntent = getIntent();
        documentId = receivedIntent.getStringExtra("documentId");

        startStr = "";
        endStr = "";

        passengerDbRef.child(documentId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Passenger passenger = snapshot.getValue(Passenger.class);
                startStr = passenger.getStartLocation();
                endStr = passenger.getDriverLocation();
                setMap();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        backBtn.setOnClickListener(v -> {
            finish();
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + directionMode;
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    public void setMap(){

        if (startStr.equals("")){
            return;
        }
        if (endStr.equals("")){
            return;
        }

        List<Address> addressList = null;
        Geocoder geocoder = new Geocoder(this);
        try {
            addressList = geocoder.getFromLocationName(startStr, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address = addressList.get(0);
        LatLng startLL = new LatLng(address.getLatitude(), address.getLongitude());

        startLoc = new MarkerOptions().position(startLL).title(startStr);
        mMap.addMarker(startLoc);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(startLL));
        try {
            addressList = geocoder.getFromLocationName(endStr, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        address = addressList.get(0);
        LatLng endLL = new LatLng(address.getLatitude(), address.getLongitude());
        endLoc = new MarkerOptions().position(endLL).title(endStr);
        mMap.addMarker(endLoc);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(endLL));

        mMap.getUiSettings().setZoomControlsEnabled(true);
        new FetchURL(DriverViewRoute.this).execute(getUrl(startLoc.getPosition(), endLoc.getPosition(), "driving"), "driving");

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(startLoc.getPosition());
                builder.include(endLoc.getPosition());
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 200));
            }
        });
    }
}