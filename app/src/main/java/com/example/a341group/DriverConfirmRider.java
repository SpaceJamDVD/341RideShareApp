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
        String toBeParsed;
        toBeParsed = "geo:0,0?q=" + riderInfos[1];
        Uri gmmIntentUri = Uri.parse(toBeParsed);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
    public void confirmPickup(View view){
        String toBeParsed;
        toBeParsed = "geo:0,0?q=" + riderInfos[1];
        Uri gmmIntentUri = Uri.parse(toBeParsed);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        Intent i = new Intent(this, DriverEnrouteActivity.class);
        startActivity(i);
    }


}