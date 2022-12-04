package com.example.a341group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PassengerSearch extends AppCompatActivity {

    DatabaseReference passengerDbRef;
    DatabaseReference userDbRef;

    private FirebaseUser user;
    String userUID;
    String fullName;
    int completedRideshares;

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

        Passenger passenger = new Passenger(fullName, startLocation, endLocation, pickupTime, completedRideshares, fullName.length(), "", userUID);

        passengerDbRef.push().setValue(passenger).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(PassengerSearch.this, PassengerWait.class);
                    intent.putExtra("startLocation", startLocation);
                    intent.putExtra("endLocation", endLocation);
                    intent.putExtra("pickupTime", pickupTime);
                    startActivity(intent);
                } else {
                    Toast.makeText(PassengerSearch.this, "Failed to search!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void returnToChoice(){
        startActivity(new Intent(PassengerSearch.this, RideOrDriveActivity.class));
    }
}
