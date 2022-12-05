package com.example.a341group;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

// make sure to go to SDK Manager-> SDK tools and enable google play services

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;

    TextView moveToRegisterBtn;
    EditText emailInput;
    EditText passwordInput;

    Button signInBtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();

        moveToRegisterBtn = findViewById(R.id.moveToRegisterBtn);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        signInBtn = findViewById(R.id.signInBtn);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        moveToRegisterBtn.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, Register.class));
        });

        signInBtn.setOnClickListener(v -> {
            signInUser();
        });

    }

    private void signInUser(){
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty()){
            emailInput.setError("Email is required!");
            emailInput.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailInput.setError("Please provide a valid email!");
            emailInput.requestFocus();
            return;
        }
        if (password.isEmpty()){
            passwordInput.setError("Password is required!");
            passwordInput.requestFocus();
            return;
        }

        if (password.length() < 6){
            passwordInput.setError("Password should be 6 or more characters!");
            passwordInput.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(Login.this, RideOrDriveActivity.class));
                } else {
                    Toast.makeText(Login.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

}