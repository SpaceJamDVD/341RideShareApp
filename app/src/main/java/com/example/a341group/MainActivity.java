package com.example.a341group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//TODO: add internet and locations permissions
//TODO: add google maps API , key not generated yet


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Click on register button: open new activity called RegistrationActivity
    public void onRegister(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    //Click on LogIn button: open new activity called xxxx if credentials are good
    public void onLogIn(View view) {

        //TODO: validate username and password

        Intent intent = new Intent(this, RideOrDriveActivity.class);
        startActivity(intent);
    }


}