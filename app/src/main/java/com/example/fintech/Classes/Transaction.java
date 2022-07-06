package com.example.fintech.Classes;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Date;

public class Transaction {
    private double amount;
    private String toAddress;
    private String fromAddress;
    private String hash;
    private int id;
    private boolean active;
    private String name;
    private Date timestamp;
    static int counter = 0;



    public Transaction(String fromAddress,String toAddress,double amount, boolean active, String name, Date timestamp) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.amount = amount;
        this.hash = calculateHash(this);
        this.id = counter++;
        this.active = active;
        this.name = name;
        this.timestamp = timestamp;
    }

    public static String calculateHash(Transaction transaction) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        // TODO: 7/6/2022
        /***********
         *
         * ADD THE PRIVATE KEY TO SIGN
         *
         *
         * ***********/
        //Creating KeyPair generator object
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");

        //Initializing the key pair generator
        keyPairGen.initialize(2048);

        //Generate the pair of keys
        KeyPair pair = keyPairGen.generateKeyPair();

        //Getting the private key from the key pair
        PrivateKey privKey = pair.getPrivate();

        //Creating a Signature object
        Signature sign = Signature.getInstance("SHA256withDSA");

        //Initialize the signature
        sign.initSign(privKey);



        byte[] bytes = transaction.toString().getBytes();

        //Adding data to the signature
        sign.update(bytes);

        //Calculating the signature
        byte[] signature = sign.sign();
        return bin2hex(signature);
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

    public boolean getActive(){ return active; }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName(){ return name; }

    public Date getTimestamp(){ return timestamp;}


}
