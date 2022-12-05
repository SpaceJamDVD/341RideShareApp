package com.example.a341group;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

public class PassengerSearch extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback{

    DatabaseReference passengerDbRef;
    DatabaseReference userDbRef;

    private FirebaseUser user;
    String userUID;
    String fullName;
    int completedRideshares;

    private GoogleMap mMap;
    MarkerOptions startLoc, endLoc;
    String startStr, endStr;
    Polyline currentPolyline;

    EditText startLocationInput;
    EditText endLocationInput;
    EditText pickupTimeInput;

    Button searchBtn;
    TextView cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_search);

        passengerDbRef = FirebaseDatabase.getInstance().getReference().child("Passengers");
        userDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userUID = user.getUid();
        userDbRef.child(userUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user != null){
                    fullName = user.getFullName();
                    completedRideshares = user.getCompletedRideshares();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        startLocationInput = findViewById(R.id.startLocation);
        endLocationInput = findViewById(R.id.endLocation);
        pickupTimeInput = findViewById(R.id.pickupTime);

        searchBtn = findViewById(R.id.searchBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        searchBtn.setOnClickListener(v -> {
            requestDriver();
        });

        cancelBtn.setOnClickListener(v -> {
            returnToChoice();
        });

        startLocationInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    setMap();
                }
            }
        });
        endLocationInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    setMap();
                }
            }
        });

        startStr = "";
        endStr = "";

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    private void requestDriver(){

        String startLocation = startLocationInput.getText().toString();
        String endLocation = endLocationInput.getText().toString();
        String pickupTime = pickupTimeInput.getText().toString();

        if (startLocation.isEmpty()){
            startLocationInput.setError("Start location is required!");
            startLocationInput.requestFocus();
            return;
        }

        if (endLocation.isEmpty()){
            endLocationInput.setError("End location is required!");
            endLocationInput.requestFocus();
            return;
        }

        if (pickupTime.isEmpty()){
            pickupTimeInput.setError("Pickup time is required!");
            pickupTimeInput.requestFocus();
            return;
        }

        Passenger passenger = new Passenger(fullName, startLocation, endLocation, pickupTime, completedRideshares, fullName.length(), "", userUID, "");
        String documentId = passengerDbRef.push().getKey();
        passengerDbRef.child(documentId).setValue(passenger).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(PassengerSearch.this, PassengerWait.class);
                    intent.putExtra("documentId", documentId);
                    intent.putExtra("startLocation", startLocation);
                    intent.putExtra("endLocation", endLocation);
                    intent.putExtra("pickupTime", pickupTime);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(PassengerSearch.this, "Failed to search!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void returnToChoice(){
        startActivity(new Intent(PassengerSearch.this, RideOrDriveActivity.class));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMap();
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

        startStr = startLocationInput.getText().toString();
        endStr = endLocationInput.getText().toString();

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
        new FetchURL(PassengerSearch.this).execute(getUrl(startLoc.getPosition(), endLoc.getPosition(), "driving"), "driving");

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
