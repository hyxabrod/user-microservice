package com.example.user.dto;

public class UserResponse {

    private final String firstName;
    private final String lastName;
    private final Integer age;
    private final String address;

    public UserResponse(String firstName, String lastName, Integer age, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }
}
