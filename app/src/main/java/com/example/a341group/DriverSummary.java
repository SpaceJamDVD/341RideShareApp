package com.example.a341group;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DriverSummary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_summary);
        //name, location, destination, time
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void complete(View view){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }


}