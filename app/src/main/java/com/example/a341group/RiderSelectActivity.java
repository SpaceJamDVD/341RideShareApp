package com.example.a341group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RiderSelectActivity extends AppCompatActivity {

    TextView riderName;
    TextView riderDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_select);

        riderName = (TextView) findViewById(R.id.riderName);
        riderDistance = (TextView) findViewById(R.id.riderDistance);

        //TODO: set rider parameters from database
        //riderName.setText();
        //riderDistance.setText();
    }

    public void onGetDirectionsToRider(View view){
        String toBeParsed;
        //TODO plumb riders location into 'location' for driver to receive directions
        //toBeParsed = "geo:0,0?q=" + location.getText().toString();
        //Uri gmmIntentUri = Uri.parse(toBeParsed);
        //Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        //mapIntent.setPackage("com.google.android.apps.maps");
        //startActivity(mapIntent);
    }

    public void getRiderInfo(){
        //TODO: get rider data from database (?? parameters needed)
    }


}