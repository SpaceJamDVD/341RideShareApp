package com.example.a341group;

public class Route {

    private String endLocation;
    private String pickupTime;
    private String driverUID;

    Route(String endLocation, String pickupTime, String driverUID){
        this.endLocation = endLocation;
        this.pickupTime = pickupTime;
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

}
