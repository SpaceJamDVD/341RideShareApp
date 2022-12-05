package com.example.a341group;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class RideOrDriveActivity extends AppCompatActivity {

    TextView logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_or_drive);

        logout = findViewById(R.id.logOut);
        logout.setOnClickListener(v -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        });
    }

    public void onRiderButton(View view){
        Intent intent = new Intent(this, PassengerSearch.class);
        startActivity(intent);
        finish();
    }

    public void onDriverButton(View view){
        Intent intent = new Intent(this, DriverSearch.class);
        startActivity(intent);
        finish();
    }
    public void needHelp(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=YQHsXMglC9A"));
        startActivity(intent);
    }
}