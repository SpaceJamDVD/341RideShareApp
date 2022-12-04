package com.example.a341group;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PassengerConfirm extends AppCompatActivity {


    TextView driverText;

    Button confirmBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_confirm);

        driverText = findViewById(R.id.driverText);
        confirmBtn = findViewById(R.id.confirmBtn);

        Intent receivedIntent = getIntent();
        String name = receivedIntent.getStringExtra("name");

        driverText.setText(name + " will pick you up!");

        confirmBtn.setOnClickListener(v -> {
            Intent intent = new Intent(PassengerConfirm.this, PassengerArrive.class);
            intent.putExtra("name", name);
            startActivity(intent);
            finish();
        });

    }

}