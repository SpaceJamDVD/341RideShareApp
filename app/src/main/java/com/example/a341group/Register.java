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
import com.google.firebase.database.FirebaseDatabase;

// make sure to go to SDK Manager-> SDK tools and enable google play services

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;

    TextView moveToLoginBtn;
    EditText usernameInput;
    EditText emailInput;
    EditText passwordInput;
    EditText fullNameInput;

    Button registerBtn;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mAuth = FirebaseAuth.getInstance();

        moveToLoginBtn = findViewById(R.id.moveToLoginBtn);
        fullNameInput = findViewById(R.id.fullNameInput);
        usernameInput = findViewById(R.id.usernameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        registerBtn = findViewById(R.id.registerBtn);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        moveToLoginBtn.setOnClickListener(v -> {
            startActivity(new Intent(Register.this, Login.class));
        });

        registerBtn.setOnClickListener(v -> {
            registerUser();
        });
    }

    private void registerUser(){
        String fullName = fullNameInput.getText().toString().trim();
        String username = usernameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (username.isEmpty()){
            usernameInput.setError("Username is required!");
            usernameInput.requestFocus();
            return;
        }
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
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(username, email, fullName);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                startActivity(new Intent(Register.this, Login.class));
                                            } else {
                                                Toast.makeText(Register.this, "Failed to register, Try again!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(Register.this, "Failed to register, Try again!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }

}