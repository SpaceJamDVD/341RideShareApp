package com.example.a341group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DriverFindRiderActivity extends AppCompatActivity {

    //hard coded
    String[] rider1;
    String[] rider2;
    String[] rider3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_find_rider);
        //name, location, destination, time
        rider1=new String[]{"Gus H", "UBCO", "Rutland", "3:30PM"};
        rider2=new String[]{"Ramos L", "Kelowna Airport", "Rutland", "3:50PM"};
        rider3=new String[]{"Kevin W", "UBCO", "Orchard Park Mall", "2:20PM"};
        Button r1 = (Button)findViewById(R.id.rider1);
        Button r2 = (Button)findViewById(R.id.rider2);
        Button r3 = (Button)findViewById(R.id.rider3);
        r1.setText(rider1[0]+" Wants to go to: "+rider1[1]+" \n from: "+rider1[2]+" at "+rider1[3]);
        r2.setText(rider2[0]+" Wants to go to: "+rider2[1]+" \n from: "+rider2[2]+" at "+rider2[3]);
        r3.setText(rider3[0]+" Wants to go to: "+rider3[1]+" \n from: "+rider3[2]+" at "+rider3[3]);
    }

    public void rider1Click(View view){
        Intent intent = new Intent(this, DriverConfirmRider.class);
        intent.putExtra("riderInfo",rider1);
        startActivity(intent);
    }
    public void rider2Click(View view){
        Intent intent = new Intent(this, DriverConfirmRider.class);
        intent.putExtra("riderInfo",rider2);
        startActivity(intent);
    }
    public void rider3Click(View view){
        Intent intent = new Intent(this, DriverConfirmRider.class);
        intent.putExtra("riderInfo",rider3);
        startActivity(intent);
    }

}