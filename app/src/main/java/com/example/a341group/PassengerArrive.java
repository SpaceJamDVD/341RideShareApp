package com.example.a341group;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PassengerArrive extends AppCompatActivity {

    Button arriveBtn;
    Button emergencyBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_arrive);

        arriveBtn = findViewById(R.id.arriveBtn);
        emergencyBtn = findViewById(R.id.emergencyBtn);

        Intent receivedIntent = getIntent();
        String name = receivedIntent.getStringExtra("name");

        arriveBtn.setOnClickListener(v -> {
            Intent intent = new Intent(PassengerArrive.this, RatingAfterRideActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
            finish();
        });

        emergencyBtn.setOnClickListener(v -> {

        });

    }

}