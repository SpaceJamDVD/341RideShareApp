package com.example.a341group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class PassengerWait extends AppCompatActivity {

    DatabaseReference passengerDbRef;
    DatabaseReference userDbRef;

    TextView startLocationInput;
    TextView endLocationInput;
    TextView pickupTimeInput;

    TextView changeSearchBtn;

    String documentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_waiting);

        passengerDbRef = FirebaseDatabase.getInstance().getReference().child("Passengers");
        userDbRef = FirebaseDatabase.getInstance().getReference().child("Users");

        startLocationInput = findViewById(R.id.startLocation);
        endLocationInput = findViewById(R.id.endLocation);
        pickupTimeInput = findViewById(R.id.pickupTime);

        changeSearchBtn = findViewById(R.id.changeSearchBtn);

        Intent intent = getIntent();
        documentId = intent.getStringExtra("documentId");
        String startLocation = intent.getStringExtra("startLocation");
        String endLocation = intent.getStringExtra("endLocation");
        String pickupTime = intent.getStringExtra("pickupTime");


        passengerDbRef.child(documentId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Passenger passenger = snapshot.getValue(Passenger.class);

                if (!passenger.getDriverUID().equals("") && !passenger.getDriverLocation().equals("")){
                    userDbRef.child(passenger.getDriverUID()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);

                            if (user != null){
                                Intent intent = new Intent(PassengerWait.this, PassengerConfirm.class);
                                intent.putExtra("documentId", documentId);
                                intent.putExtra("name", user.getFullName());
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        startLocationInput.setText(startLocation);
        endLocationInput.setText(endLocation);
        pickupTimeInput.setText(pickupTime);

        changeSearchBtn.setOnClickListener(v -> {
            finish();
        });

    }

}
