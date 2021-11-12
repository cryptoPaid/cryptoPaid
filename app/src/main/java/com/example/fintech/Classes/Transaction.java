package com.example.fintech.Classes;

public class Transaction {
    private int amount;
    private String toAddress;
    private String fromAddress;


    public Transaction(String fromAddress,String toAddress,int amount) {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getFromAddress() {
        return fromAddress;
    }
}
