package com.example.a341group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DriverFindRiderActivity extends AppCompatActivity {

    //TODO: add list of possible riders

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_find_rider);
    }

    public void onDriverBack(View view){
        Intent intent = new Intent(this, RideOrDriveActivity.class);
        startActivity(intent);
    }

}