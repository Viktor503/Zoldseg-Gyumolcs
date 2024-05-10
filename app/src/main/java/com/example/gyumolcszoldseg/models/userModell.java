package com.example.gyumolcszoldseg.models;

public class userModell {
    private String email;
    private String password;
    private String address;
    private String phone;

    public userModell(String email, String password, String address, String phone) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
