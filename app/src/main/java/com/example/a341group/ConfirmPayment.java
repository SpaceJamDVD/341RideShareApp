package com.example.a341group;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConfirmPayment extends AppCompatActivity {
    DatabaseReference passengerDbRef;
    DatabaseReference userDbRef;

    private FirebaseUser user;
    String userUID;

    ArrayList<String> activePassengers;
    ArrayList<PassengerPayment> payments;
    PassengerPaymentAdapter adapter;
    int totalSaved;

    ListView myListView;
    TextView total;
    Button confirmBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_payment);

        myListView = findViewById(R.id.paymentList);
        total = findViewById(R.id.totalSaved);

        passengerDbRef = FirebaseDatabase.getInstance().getReference("Passengers");
        userDbRef = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userUID = user.getUid();

        Intent intent = getIntent();
        activePassengers = intent.getExtras().getStringArrayList("activePassengers");

        payments = new ArrayList<>();

        for (String documentUID : activePassengers){
            passengerDbRef.child(documentUID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Passenger passenger = snapshot.getValue(Passenger.class);

                    if (passenger != null){
                        PassengerPayment payment = new PassengerPayment(passenger.getName(), passenger.getRideCost());
                        payments.add(payment);
                    }

                    setPayments();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        confirmBtn = findViewById(R.id.confirmPaymentBtn);
        confirmBtn.setOnClickListener(v -> {
            finishRideShare();
        });
        setPayments();
    }

    private void finishRideShare(){
        for (String documentUID : activePassengers){
            passengerDbRef.child(documentUID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Passenger passenger = snapshot.getValue(Passenger.class);
                    snapshot.getRef().removeValue();
                    incrementRideShares(passenger.getPassengerUID());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        incrementRideShares(userUID);
        startActivity(new Intent(ConfirmPayment.this, RideOrDriveActivity.class));
        finish();
    }



    private void setPayments(){
        adapter = new PassengerPaymentAdapter(this, R.layout.passenger_payment, payments);
        myListView.setAdapter(adapter);

        totalSaved = 0;
        for (PassengerPayment payment : payments){
            totalSaved += payment.getPayment();
        }

        total.setText("You've saved " + totalSaved + "$!");
    }

    private void incrementRideShares(String UID){
        userDbRef.child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                user.setCompletedRideshares(user.getCompletedRideshares() + 1);
                userDbRef.child(UID).setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
