package com.example.a341group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//TODO: add Destination edit text to pass into RiderMapsActivity

public class RiderDestinationEnterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_destination_enter);

        //SearchView locSearchView = (SearchView) findViewById(R.id.locationSearch); // inititate a search view
        //CharSequence locquery = locSearchView.getQuery(); // get the query string currently in the text field

        //SearchView destSearchView = (SearchView) findViewById(R.id.destinationSearch); // inititate a search view
       // CharSequence destquery = destSearchView.getQuery(); // get the query string currently in the text field
    }

    public void onBackDestScreen(View view){
        Intent intent = new Intent(this, RideOrDriveActivity.class);
        startActivity(intent);
    }

    public void onNextDestScreen(View view){
        Intent intent = new Intent(this, RiderMapsActivity.class);
        startActivity(intent);
    }
}