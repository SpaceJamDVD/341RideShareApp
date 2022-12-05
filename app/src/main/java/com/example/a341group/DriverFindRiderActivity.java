package com.example.a341group;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DriverFindRiderActivity extends AppCompatActivity {

    DatabaseReference passengerDbRef;
    DatabaseReference userDbRef;
    PassengerCardAdapter adapter;

    FusedLocationProviderClient fusedLocationProviderClient;
    String currentLocation;
    private FirebaseUser user;
    String userUID;

    Button backBtn;
    TextView endLocationInput;
    TextView pickupTimeInput;

    ArrayList<String> activePassengers;
    ArrayList<PassengerCard> passengerCards;
    ArrayList<Passenger> passengers;

    private final static int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_find_passenger);

        passengerDbRef = FirebaseDatabase.getInstance().getReference("Passengers");
        userDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userUID = user.getUid();
        currentLocation = "";

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        ListView myListView = findViewById(R.id.passengers);

        endLocationInput = findViewById(R.id.endLocation);
        pickupTimeInput = findViewById(R.id.pickupTime);
        backBtn = findViewById(R.id.backBtn);

        Intent receivedIntent = getIntent();
        activePassengers = receivedIntent.getExtras().getStringArrayList("activePassengers");
        String endLocation = receivedIntent.getStringExtra("endLocation");
        String pickupTime = receivedIntent.getStringExtra("pickupTime");

        endLocationInput.setText(endLocation);
        pickupTimeInput.setText(pickupTime);

        passengerCards = new ArrayList<>();
        passengers = new ArrayList<>();

        passengerDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                passengerCards.clear();

                for(DataSnapshot passengerSnapshot : snapshot.getChildren()){
                    Passenger passenger = passengerSnapshot.getValue(Passenger.class);
                    if (passenger != null){
                        if (passenger.getDriverUID().equals("")){
                            passengers.add(passenger);
                            PassengerCard passengerCard = new PassengerCard(passenger.getName(), passenger.getStartLocation(), passenger.getEndLocation(), passenger.getCompletedRideshares(), passengerSnapshot.getKey(), passenger.getPickupTime());
                            passengerCards.add(passengerCard);
                        }
                    }
                }

                adapter = new PassengerCardAdapter(DriverFindRiderActivity.this, R.layout.passenger_card, passengerCards);
                myListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new PassengerCardAdapter(this, R.layout.passenger_card, passengerCards);
        myListView.setAdapter(adapter);
        if (myListView.getAdapter().getCount()==0){

            Toast.makeText(getApplicationContext(),"Currently there are no passengers",Toast.LENGTH_LONG).show();
        }
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String uid = passengerCards.get(i).getDocumentUID();
                Passenger passenger = passengers.get(i);
                getLastLocation();
                passenger.setDriverUID(userUID);
                if (currentLocation.equals("")){
                    passenger.setDriverLocation("UBCO Exchange, Kelowna"); // Testing
                } else {
                    passenger.setDriverLocation(currentLocation);
                }
                passengerDbRef.child(uid).setValue(passenger).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            activePassengers.add(uid);
                            Intent intent = new Intent(DriverFindRiderActivity.this, DriverMenu.class);
                            intent.putStringArrayListExtra("activePassengers", activePassengers);
                            intent.putExtra("endLocation", endLocation);
                            intent.putExtra("pickupTime", pickupTime);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DriverFindRiderActivity.this, DriverMenu.class);
            intent.putStringArrayListExtra("activePassengers", activePassengers);
            intent.putExtra("endLocation", endLocation);
            intent.putExtra("pickupTime", pickupTime);
            startActivity(intent);
            finish();
        });

    }

    private void getLastLocation(){
        System.out.println("Here");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null){
                        Geocoder geocoder = new Geocoder(DriverFindRiderActivity.this, Locale.getDefault());
                        List<Address> addresses = null;
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        currentLocation = addresses.get(0).getAddressLine(0);
                        Toast.makeText(DriverFindRiderActivity.this, currentLocation, Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            getPermission();
        }
    }

    private void getPermission(){
        ActivityCompat.requestPermissions(DriverFindRiderActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            } else {
                Toast.makeText(DriverFindRiderActivity.this, "Requires Permissions", Toast.LENGTH_LONG).show();
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}