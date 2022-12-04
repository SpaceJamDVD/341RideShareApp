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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class PassengerWait extends AppCompatActivity {

    DatabaseReference passengerDbRef;

    TextView startLocationInput;
    TextView endLocationInput;
    TextView pickupTimeInput;

    TextView changeSearchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_waiting);

        passengerDbRef = FirebaseDatabase.getInstance().getReference().child("Passengers");

        startLocationInput = findViewById(R.id.startLocation);
        endLocationInput = findViewById(R.id.endLocation);
        pickupTimeInput = findViewById(R.id.pickupTime);

        changeSearchBtn = findViewById(R.id.changeSearchBtn);

        Intent intent = getIntent();
        String startLocation = intent.getStringExtra("startLocation");
        String endLocation = intent.getStringExtra("endLocation");
        String pickupTime = intent.getStringExtra("pickupTime");

        startLocationInput.setText(startLocation);
        endLocationInput.setText(endLocation);
        pickupTimeInput.setText(pickupTime);

        changeSearchBtn.setOnClickListener(v -> {
            backToChoice();
        });

    }

    private void backToChoice(){
        startActivity(new Intent(PassengerWait.this, PassengerSearch.class));
    }
}
