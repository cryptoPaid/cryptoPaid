package com.example.fintech.Classes;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Wallet {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private double balance;



    public Wallet (){}

    public Wallet ( PrivateKey privateKey, PublicKey publicKey, double balance){
      this.privateKey = privateKey;
      this.publicKey = publicKey;
      this.balance = balance;

    }


    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "privateKey=" + privateKey +
                ", publicKey=" + publicKey +
                ", balance=" + balance;
    }
}

