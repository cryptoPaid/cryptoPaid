package com.example.fintech.Classes;

public class UserId {
    private String space;
    private String email;

    public UserId() {
    }

    public UserId(String space, String email) {
        this.space = space;
        this.email = email;
    }

    public String getSpace() {
        return space;
    }

    public String getEmail() {
        return email;
    }
}
