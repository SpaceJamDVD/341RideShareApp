package com.example.a341group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class RatingAfterRideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_after_ride);
    }

    public void onSubmit(View view){
        Intent intent = new Intent(this, RideOrDriveActivity.class);
        startActivity(intent);
    }

}