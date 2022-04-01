package com.example.fintech.Classes;

public class User {

    private String username;
    private String password;
    private String email;
    private String role;
    private Wallet wallet;
    private String firstName;
    private String lastName;



    public User(){
    }

    public User(String username, String password, String email, String role, Wallet wallet, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.wallet = wallet;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String username, String password, String email, Wallet wallet, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.wallet = wallet;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getRole() {
        return role;
    }

    public User setRole(String role) {
        this.role = role;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String username) {
        this.password = password;
        return this;
    }



    @Override
    public String toString() {
        return "Account{" +
                "email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", username='" + username +
                '}';
    }


}
