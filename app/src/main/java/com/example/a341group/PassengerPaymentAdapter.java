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

public class PassengerPaymentAdapter extends ArrayAdapter<PassengerPayment> {

    private Context myContext;
    int myResources;

    public PassengerPaymentAdapter(Context context, int resource, ArrayList<PassengerPayment> objects){
        super(context, resource, objects);
        myContext = context;
        myResources = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        String name = getItem(position).getName();
        int payment = getItem(position).getPayment();

        LayoutInflater inflater = LayoutInflater.from(myContext);

        convertView = inflater.inflate(myResources, parent, false);

        TextView tvName = convertView.findViewById(R.id.name);
        TextView tvPayment = convertView.findViewById(R.id.payment);

        tvName.setText(" - " + name);
        tvPayment.setText(payment + "$");

        return convertView;
    }
}
