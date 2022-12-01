package com.example.a341group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DriverEnrouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_enroute);
        //name, location, destination, time
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void confirmDropoff(View view){
        Intent i = new Intent(this, DriverSummary.class);
        startActivity(i);
    }


}