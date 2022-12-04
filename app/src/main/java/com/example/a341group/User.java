package com.example.a341group;

public class User {
    private String username;
    private String email;
    private String fullName;
    private int completedRideshares;

    public User(){

    }

    public User(String username, String email, String fullName){
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.completedRideshares = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getCompletedRideshares() {
        return completedRideshares;
    }

    public void setCompletedRideshares(int completedRideshares) {
        this.completedRideshares = completedRideshares;
    }
}
