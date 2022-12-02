package com.example.a341group;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ConfirmPayment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_payment);

        ListView myListView = findViewById(R.id.paymentList);
        TextView total = findViewById(R.id.totalSaved);

        ArrayList<PassengerPayment> payments = new ArrayList<>();
        payments.add(new PassengerPayment("Jason", 2));
        payments.add(new PassengerPayment("Quin", 222));
        payments.add(new PassengerPayment("Kevin", 120));
        payments.add(new PassengerPayment("Gus", 12));

        PassengerPaymentAdapter adapter = new PassengerPaymentAdapter(this, R.layout.passenger_payment, payments);
        myListView.setAdapter(adapter);

        int totalSaved = 0;
        for (PassengerPayment payment : payments){
            totalSaved += payment.getPayment();
        }

        total.setText("You've saved " + totalSaved + "$!");
    }
}
