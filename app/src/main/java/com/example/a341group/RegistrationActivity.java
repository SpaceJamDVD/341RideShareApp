package com.example.a341group;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    EditText username;
    EditText firstname;
    EditText lastname;
    RadioButton radiomale;
    RadioButton radiofemale;
    EditText phonenumber;
    EditText email;
    EditText password1;
    EditText password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = (EditText)findViewById(R.id.userTag);
        firstname = (EditText)findViewById(R.id.firstName);
        lastname = (EditText)findViewById(R.id.lastName);
        radiomale = (RadioButton)findViewById(R.id.maleButton);
        radiofemale = (RadioButton)findViewById(R.id.femaleButton);
        phonenumber = (EditText)findViewById(R.id.phNumber);
        email = (EditText)findViewById(R.id.eMail);
        password1 = (EditText)findViewById(R.id.pass1);
        password2 = (EditText)findViewById(R.id.pass2);

    }

    // On back button: return to MainActivity
    public void onBack(View view){ finish(); }

    // On Register button : check parameters are correct then save user profile
    public void onRegisterSave(View view) {
        if (username.getText().toString().length() == 0) {
            Toast.makeText(RegistrationActivity.this, "please enter user name", Toast.LENGTH_LONG).show();
        } else {
            if (firstname.getText().toString().length() == 0) {
                Toast.makeText(RegistrationActivity.this, "please enter first name", Toast.LENGTH_LONG).show();
            } else {
                if (lastname.getText().toString().length() == 0) {
                    Toast.makeText(RegistrationActivity.this, "please enter last name", Toast.LENGTH_LONG).show();
                } else {
                    if (radiomale.isChecked() == false && radiofemale.isChecked() == false) {
                        Toast.makeText(RegistrationActivity.this, "please select gender", Toast.LENGTH_LONG).show();
                    } else {
                        if (phonenumber.getText().toString().length() != 10) {
                            Toast.makeText(RegistrationActivity.this, "please enter a valid phone number", Toast.LENGTH_LONG).show();
                        } else {
                            if (email.getText().toString().length() == 0) {
                                Toast.makeText(RegistrationActivity.this, "please enter e-mail address", Toast.LENGTH_LONG).show();
                            } else {
                                if (password1.getText().toString().length() == 0) {
                                    Toast.makeText(RegistrationActivity.this, "enter passwords", Toast.LENGTH_LONG).show();
                                } else {
                                    if (password1.getText().toString().equals(password2.getText().toString()) == false) {
                                        Toast.makeText(RegistrationActivity.this, "passwords don't match", Toast.LENGTH_LONG).show();
                                    } else {

                                        //TODO: save all the data to user profile

                                        finish();
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}