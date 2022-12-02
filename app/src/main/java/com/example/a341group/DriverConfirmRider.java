package com.example.a341group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DriverConfirmRider extends AppCompatActivity {
    String[] riderInfos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_confirm_rider);
        Intent intent = getIntent();
        riderInfos = intent.getStringArrayExtra("riderInfo");
        TextView ride = (TextView) findViewById(R.id.confirmRideText);
        ride.setText(riderInfos[0]+"'s ride");
    }
    public void checkRoute(View view){
        Intent i = new Intent(this, MapActivity.class);
        i.putExtra("riderInfo",riderInfos);
        startActivity(i);
    }
    public void confirmPickup(View view){
        Intent i = new Intent(this, MapActivity.class);
        i.putExtra("riderInfo",riderInfos);
        startActivity(i);
    }



}