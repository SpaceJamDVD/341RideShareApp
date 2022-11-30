package com.example.a341group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RiderFindDriverActivity extends AppCompatActivity {

    //TODO: add list of Drivers from firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_find_driver);
    }

    public void onRiderBack(View view){
        Intent intent = new Intent(this, RideOrDriveActivity.class);
        startActivity(intent);
    }

}