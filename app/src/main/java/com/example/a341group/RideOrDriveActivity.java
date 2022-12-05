package com.example.a341group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RideOrDriveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_or_drive);
    }

    public void onRiderButton(View view){
        Intent intent = new Intent(this, PassengerSearch.class);
        startActivity(intent);
    }

    public void onDriverButton(View view){
        Intent intent = new Intent(this, DriverSearch.class);
        startActivity(intent);
    }
}