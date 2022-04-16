package com.eussi.basic;

public class Person implements PersonInterface {
    private String name = "Jack";
    private int age = 20;
    private Address address = null;

    public static String tellType() {
        return Person.class.getName();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
