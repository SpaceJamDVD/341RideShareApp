package com.example.a341group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RiderGotDriver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_got_driver);
    }

    public void onPickedUpButton(View view){
        Intent intent = new Intent(this,RiderArrivedActivity.class);
        startActivity(intent);
    }
}