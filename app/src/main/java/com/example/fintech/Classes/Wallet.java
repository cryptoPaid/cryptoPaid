package com.example.fintech.Classes;

public class Wallet {

   private String credit_card;
   private String cvv;
   private String date;
   private double balance;


   public Wallet (){}

    public Wallet (String credit_card, String cvv, String date, double balance){
       this.credit_card = credit_card;
       this.cvv = cvv;
       this.date = date;
       this.balance = balance;
    }


}
