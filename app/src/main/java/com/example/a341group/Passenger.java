package com.example.a341group;

public class Passenger {

    private String name;
    private String startLocation;
    private String endLocation;
    private String pickupTime;
    private int completedRideshares;
    private int rideCost;
    private String driverUID;
    private String passengerUID;
    private String driverLocation;

    Passenger(){

    }

    Passenger(String name, String startLocation, String endLocation, String pickupTime, int completedRideshares, int rideCost, String driverUID, String passengerUID, String driverLocation){
        this.name = name;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.pickupTime = pickupTime;
        this.completedRideshares = completedRideshares;
        this.rideCost = rideCost;
        this.driverUID = driverUID;
        this.passengerUID = passengerUID;
        this.driverLocation = driverLocation;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCompletedRideshares() {
        return completedRideshares;
    }

    public void setCompletedRideshares(int completedRideshares) {
        this.completedRideshares = completedRideshares;
    }

    public int getRideCost() {
        return rideCost;
    }

    public void setRideCost(int rideCost) {
        this.rideCost = rideCost;
    }

    public String getDriverUID() {
        return driverUID;
    }

    public void setDriverUID(String driverUID) {
        this.driverUID = driverUID;
    }

    public String getPassengerUID() {
        return passengerUID;
    }

    public void setPassengerUID(String passengerUID) {
        this.passengerUID = passengerUID;
    }

    public String getDriverLocation() {
        return driverLocation;
    }

    public void setDriverLocation(String driverLocation) {
        this.driverLocation = driverLocation;
    }
}
