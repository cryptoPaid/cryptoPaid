package com.example.fintech.Classes;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Transaction {
    private double amount;
    private String toAddress;
    private String fromAddress;
    private String hash;
    private int id = 0;


    public Transaction(String fromAddress,String toAddress,double amount) {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.amount = amount;
        this.hash = calculateHash(this);
        this.id = id++;
    }

    public static String calculateHash(Transaction transaction) {
        MessageDigest digest=null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        digest.reset();

        byte[] dig = digest.digest(transaction.toString().getBytes());
        return bin2hex(dig);
    }

    static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data));
    }


    public String getHash() {
        return hash;
    }

    public double getAmount() {
        return amount;
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public int getId(){ return id; }
}
