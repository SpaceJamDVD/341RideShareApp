package com.example.a341group;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DriverFindRiderActivity extends AppCompatActivity {

    DatabaseReference passengerDbRef;
    DatabaseReference userDbRef;

    PassengerCardAdapter adapter;

    private FirebaseUser user;
    String userUID;

    Button backBtn;
    TextView endLocationInput;
    TextView pickupTimeInput;

    ArrayList<String> activePassengers;
    ArrayList<PassengerCard> passengerCards;
    ArrayList<Passenger> passengers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_find_passenger);

        passengerDbRef = FirebaseDatabase.getInstance().getReference("Passengers");
        userDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userUID = user.getUid();

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
                            PassengerCard passengerCard = new PassengerCard(passenger.getName(), passenger.getStartLocation(), passenger.getEndLocation(), passenger.getCompletedRideshares(), passengerSnapshot.getKey());
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
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String uid = passengerCards.get(i).getDocumentUID();
                Passenger passenger = passengers.get(i);
                //passenger.setDriverUID(userUID);
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

}