package com.example.a341group;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DriverMenu extends AppCompatActivity {

    DatabaseReference passengerDbRef;
    TextView endLocationInput;
    TextView pickupTimeInput;

    ArrayList<String> activePassengers;
    PassengerCardAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_menu);

        ListView myListView = findViewById(R.id.passengers);

        passengerDbRef = FirebaseDatabase.getInstance().getReference("Passengers");
        endLocationInput = findViewById(R.id.endLocation);
        pickupTimeInput = findViewById(R.id.pickupTime);

        Intent receivedIntent = getIntent();
        activePassengers = receivedIntent.getExtras().getStringArrayList("activePassengers");
        String endLocation = receivedIntent.getStringExtra("endLocation");
        String pickupTime = receivedIntent.getStringExtra("pickupTime");

        endLocationInput.setText(endLocation);
        pickupTimeInput.setText(pickupTime);

        ArrayList<PassengerCard> passengerCards = new ArrayList<>();
        for (String documentUID : activePassengers){
            passengerDbRef.child(documentUID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Passenger passenger = snapshot.getValue(Passenger.class);

                    if (passenger != null){
                        PassengerCard passengerCard = new PassengerCard(passenger.getName(), passenger.getStartLocation(), passenger.getEndLocation(), passenger.getCompletedRideshares(), documentUID, passenger.getPickupTime());
                        passengerCards.add(passengerCard);
                    }

                    adapter = new PassengerCardAdapter(DriverMenu.this, R.layout.passenger_card, passengerCards);
                    myListView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        adapter = new PassengerCardAdapter(this, R.layout.passenger_card, passengerCards);
        myListView.setAdapter(adapter);

        Button hereBtn = findViewById(R.id.hereBtn);
        hereBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DriverMenu.this, ConfirmPayment.class);
            intent.putStringArrayListExtra("activePassengers", activePassengers);
            startActivity(intent);
            finish();
        });

        Button lookBtn = findViewById(R.id.lookBtn);
        lookBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DriverMenu.this, DriverFindRiderActivity.class);
            intent.putStringArrayListExtra("activePassengers", activePassengers);
            intent.putExtra("endLocation", endLocation);
            intent.putExtra("pickupTime", pickupTime);
            startActivity(intent);
            finish();//Remove for ESC
        });
    }
}