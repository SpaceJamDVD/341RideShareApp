package com.example.a341group;

public class PassengerCard {

    private String name;
    private String location;
    private int completedRideshares;
    private String destination;
    private String documentUID;
    private String pickupTime;

    public PassengerCard(String name, String location, String destination, int completedRideshares, String documentUID, String pickupTime){
        this.name = name;
        this.location = location;
        this.completedRideshares = completedRideshares;
        this.destination = destination;
        this.documentUID = documentUID;
        this.pickupTime = pickupTime;
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

    public int getCompletedRideshares() {
        return completedRideshares;
    }

    public void setCompletedRideshares(int completedRideshares) {
        this.completedRideshares = completedRideshares;
    }

    public String getDocumentUID() {
        return documentUID;
    }

    public void setDocumentUID(String documentUID) {
        this.documentUID = documentUID;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }
}
