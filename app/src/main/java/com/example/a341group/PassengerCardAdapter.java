package com.example.a341group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PassengerCardAdapter extends ArrayAdapter<PassengerCard> {
    private Context myContext;
    int myResources;

    public PassengerCardAdapter(Context context, int resource, ArrayList<PassengerCard> objects){
        super(context, resource, objects);
        myContext = context;
        myResources = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        String name = getItem(position).getName();
        String location = getItem(position).getLocation();
        int rideSharesCompleted = getItem(position).getCompletedRideshares();

        LayoutInflater inflater = LayoutInflater.from(myContext);

        convertView = inflater.inflate(myResources, parent, false);

        TextView tvName = convertView.findViewById(R.id.passengerName);
        TextView tvLocation = convertView.findViewById(R.id.passengerLocation);
        TextView tvRideSharesCompleted = convertView.findViewById(R.id.rideSharesCompleted);

        tvName.setText(name);
        tvLocation.setText(location);
        tvRideSharesCompleted.setText(rideSharesCompleted + " Rideshares Completed!");

        return convertView;
    }
}
