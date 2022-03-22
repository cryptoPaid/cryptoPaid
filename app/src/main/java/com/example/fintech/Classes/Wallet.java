package com.example.fintech.Classes;

public class Wallet {

   private String privateKey;
   private String publicKey;
   private double balance;


   public Wallet (){}

    public Wallet ( String privateKey, String publicKey, double balance){
      this.privateKey = privateKey;
      this.publicKey = publicKey;
      this.balance = balance;
    }



}
