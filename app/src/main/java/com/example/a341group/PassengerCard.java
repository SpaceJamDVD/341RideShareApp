package com.example.a341group;

public class PassengerCard {

    private String name;
    private String location;
    private int rideSharesCompleted;
    private String destination;

    public PassengerCard(String name, String location, String destination, int rideSharesCompleted){
        this.name = name;
        this.location = location;
        this.rideSharesCompleted = rideSharesCompleted;
        this.destination=destination;
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
    public String getDestination(){
        return this.destination;
    }
    public void setName(String name) {
        this.name = name;
    }
}
