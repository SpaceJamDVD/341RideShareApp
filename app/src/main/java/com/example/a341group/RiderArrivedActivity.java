package com.example.a341group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RiderArrivedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_arrived);

    }

    public void onArrivedButton(View view){
        Intent intent = new Intent(this,RatingAfterRideActivity.class);
        startActivity(intent);
    }

    public void onSafetyButtonRA(View view){
        Intent intent = new Intent(this,safetyActivity.class);
        startActivity(intent);
    }
}