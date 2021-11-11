package com.example.fintech.Classes;

import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;

public class Block {
    private int index;
    private Date timestamp;
    private String data;
    private String hash;
    private String previousHash;

    public Block(int i, Timestamp timestamp, String genesis_block, String s){}

        public Block(int index, Date timestamp,String data) {
            this.index = index;
            this.timestamp = timestamp;
            this.data = data;
            this.previousHash = "";
            this.hash = calculateHash(this);
        }

    public static String calculateHash(Block block) {
        MessageDigest digest=null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        digest.reset();

        byte[] dig = digest.digest(block.toString().getBytes());
        return bin2hex(dig);
    }
    static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data));
    }

    public int getIndex() {
        return index;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getData() {
        return data;
    }
    

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }
}



