package com.example.fintech.Classes;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

    private String username;
    private String password;
    private String email;
    private String role;
    private Wallet wallet;
    private String firstName;
    private String lastName;
    private BlockChain johnstaCoin;
    private ArrayList<Transaction> pendingTransaction;



    public User(){
    }

    public User(String username, String password, String email, String role, Wallet wallet, String firstName, String lastName, BlockChain johnstaCoin, ArrayList<Transaction> pendingTransaction) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.wallet = wallet;
        this.firstName = firstName;
        this.lastName = lastName;
        this.johnstaCoin = johnstaCoin;
        this.pendingTransaction = pendingTransaction;
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


    public Wallet getWallet() {
        return wallet;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BlockChain getJohnstaCoin() {
        return johnstaCoin;
    }

    public ArrayList<Transaction> getPendingTransaction() {
        return pendingTransaction;
    }

    public User setWallet(Wallet wallet) {
        this.wallet = wallet;
        return this;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public User setJohnstaCoin(BlockChain johnstaCoin) {
        this.johnstaCoin = johnstaCoin;
        return this;
    }

    public User setPendingTransaction(ArrayList<Transaction> pendingTransaction) {
        this.pendingTransaction = pendingTransaction;
        return this;
    }

    public static String calculateHash(String password) {
        MessageDigest digest=null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        digest.reset();

        byte[] dig = digest.digest(password.getBytes());
        return bin2hex(dig);
    }
    static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data));
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
