package com.example.a341group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

//TODO: add Destination edit text to pass into RiderMapsActivity

public class RiderDestinationEnterActivity extends AppCompatActivity {

    EditText location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_destination_enter);

        location = (EditText)findViewById(R.id.destlocation);

    }

    public void onBackDestScreen(View view){
        Intent intent = new Intent(this, RideOrDriveActivity.class);
        startActivity(intent);
    }

    public void onNextDestScreen(View view){
        String toBeParsed;
        toBeParsed = "geo:0,0?q=" + location.getText().toString();
        Uri gmmIntentUri = Uri.parse(toBeParsed);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}