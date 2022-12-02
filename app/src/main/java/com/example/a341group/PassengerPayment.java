package com.example.a341group;

public class PassengerPayment {

    private String name;
    private int payment;

    public PassengerPayment(String name, int payment){
        this.name = name;
        this.payment = payment;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
