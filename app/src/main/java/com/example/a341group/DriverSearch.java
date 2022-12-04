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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DriverSearch extends AppCompatActivity {

    DatabaseReference routeDbRef;
    DatabaseReference userDbRef;

    private FirebaseUser user;
    String userUID;

    EditText endLocationInput;
    EditText pickupTimeInput;

    Button searchBtn;
    TextView cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_search);

        routeDbRef = FirebaseDatabase.getInstance().getReference().child("Routes");
        userDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userUID = user.getUid();

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

        String endLocation = endLocationInput.getText().toString();
        String pickupTime = pickupTimeInput.getText().toString();

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

        Intent intent = new Intent(DriverSearch.this, DriverMenu.class);
        intent.putStringArrayListExtra("activePassengers", new ArrayList<>());
        intent.putExtra("endLocation", endLocation);
        intent.putExtra("pickupTime", pickupTime);
        startActivity(intent);
        finish();

//        Route route = new Route(endLocation, pickupTime, userUID);
//
//        routeDbRef.push().setValue(route).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()){
//                    Intent intent = new Intent(DriverSearch.this, DriverMenu.class);
//                    intent.putStringArrayListExtra("activePassengers", new ArrayList<>());
//                    intent.putExtra("endLocation", endLocation);
//                    intent.putExtra("pickupTime", pickupTime);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(DriverSearch.this, "Failed to search!", Toast.LENGTH_LONG).show();
//                }
//            }
//        });

    }

    private void returnToChoice(){
        startActivity(new Intent(DriverSearch.this, RideOrDriveActivity.class));
    }
}
