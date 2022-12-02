package com.example.a341group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class DriverFindRiderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_find_rider);

        ListView myListView = findViewById(R.id.passengers);

        ArrayList<PassengerCard> passengerCards = new ArrayList<>();
        passengerCards.add(new PassengerCard("Jason", "Hollywood Road South", 10));
        passengerCards.add(new PassengerCard("Quin", "Hollywood Road West", 20));
        passengerCards.add(new PassengerCard("Kevin", "Hollywood Road North", 30));
        passengerCards.add(new PassengerCard("Gus", "Hollywood Road East", 40));

        PassengerCardAdapter adapter = new PassengerCardAdapter(this, R.layout.passenger_card, passengerCards);
        myListView.setAdapter(adapter);

        Button hereBtn = findViewById(R.id.hereBtn);
        hereBtn.setOnClickListener(v -> {
            startActivity(new Intent(DriverFindRiderActivity.this, ConfirmPayment.class));
        });
    }


}