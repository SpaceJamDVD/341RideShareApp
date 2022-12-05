package com.example.a341group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class RatingAfterRideActivity extends AppCompatActivity {

    TextView driverText;
    TextView reportText;
    Button confirmBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_rate_driver);

        driverText = findViewById(R.id.driverText);
        confirmBtn = findViewById(R.id.confirmBtn);
        reportText = findViewById(R.id.reportText);

        Intent receivedIntent = getIntent();
        String name = receivedIntent.getStringExtra("name");

        driverText.setText(name);
        reportText.setText("Report " + name);

        confirmBtn.setOnClickListener(v -> {
            startActivity(new Intent(RatingAfterRideActivity.this, RideOrDriveActivity.class));
            finish();
        });
        reportText.setOnClickListener(v -> {

        });
    }
}