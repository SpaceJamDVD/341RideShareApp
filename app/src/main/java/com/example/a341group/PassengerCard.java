package com.example.a341group;

public class PassengerCard {

    private String name;
    private String location;
    private int rideSharesCompleted;

    public PassengerCard(String name, String location, int rideSharesCompleted){
        this.name = name;
        this.location = location;
        this.rideSharesCompleted = rideSharesCompleted;
    }

    public int getRideSharesCompleted() {
        return rideSharesCompleted;
    }

    public void setRideSharesCompleted(int rideSharesCompleted) {
        this.rideSharesCompleted = rideSharesCompleted;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
