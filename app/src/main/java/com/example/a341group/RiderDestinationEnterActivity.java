package com.example.a341group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//TODO: add Destination edit text to pass into RiderMapsActivity

public class RiderDestinationEnterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_destination_enter);
    }

    public void onBackDestScreen(View view){
        Intent intent = new Intent(this, RideOrDriveActivity.class);
        startActivity(intent);
    }

    public void onNextDestScreen(View view){
        Intent intent = new Intent(this, RiderMapsActivity.class);
        startActivity(intent);
    }
}