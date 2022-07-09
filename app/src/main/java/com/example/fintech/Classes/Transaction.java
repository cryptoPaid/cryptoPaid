package com.example.fintech.Classes;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
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
    boolean approve;



    public Transaction(String fromAddress,String toAddress,double amount, boolean active, String name, Date timestamp, byte[] privateKey, boolean approve) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.amount = amount;
        this.hash = calculateHash(this,privateKey);
        this.id = counter++;
        this.active = active;
        this.name = name;
        this.timestamp = timestamp;
        this.approve = approve;
    }

    public Transaction(int id, String fromAddress,String toAddress,double amount, boolean active, String name, Date timestamp, byte[] privateKey,  boolean approve) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.amount = amount;
        this.hash = calculateHash(this, privateKey);
        this.active = active;
        this.name = name;
        this.timestamp = timestamp;
        this.id = id;
        this.approve = approve;
    }

    public static String calculateHash(Transaction transaction, byte[] privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKey);
        PrivateKey privatek = keyFactory.generatePrivate(privateKeySpec);
        //Creating a Signature object
        Signature sign = Signature.getInstance("SHA256withRSA");
        //Initialize the signature
        sign.initSign(privatek);
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

    public boolean isApprove() {
        return approve;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", toAddress='" + toAddress + '\'' +
                ", fromAddress='" + fromAddress + '\'' +
                ", hash='" + hash + '\'' +
                ", id=" + id +
                ", active=" + active +
                ", name='" + name + '\'' +
                ", timestamp=" + timestamp + ", approve" + approve +
                '}';
    }
}
