package com.eussi.basic;

public class Address {
    public Address(){}
    public Address(String city) {
        this.city = city;
    }

    public String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
