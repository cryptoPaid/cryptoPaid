package com.example.fintech.Classes;

import android.util.Log;

import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Block {
    private Date timestamp;
    private String data="";
    private String hash;
    private String previousHash;
    private int nonce = 0;
    private ArrayList<Transaction> transaction = new ArrayList<>();

    public Block(Date timestamp, String data, String previousHash) {
        this.timestamp = timestamp;
        this.data = data;
        this.previousHash = previousHash;
        this.hash = calculateHash(this);

    }
    public Block(Date timestamp, String data, String previousHash, String hash) {
        this.timestamp = timestamp;
        this.data = data;
        this.previousHash = previousHash;
        this.hash = hash;

    }


    public Block(Date timestamp, ArrayList<Transaction> transaction, String previousHash) {
        this.timestamp = timestamp;
        this.transaction = transaction;
        this.previousHash = previousHash;
        this.hash = calculateHash(this);
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

    public ArrayList<Transaction> getTransaction() {
        return transaction;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
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

    public void mineBlock(int difficulty) {
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < difficulty; i++) {
            sb.append('0');
        }
        while (!this.hash.substring(0, difficulty).equals(sb.toString())) {
            this.nonce++;
            String hash = calculateHash(this);
            this.setHash(hash);
        }
        Log.d("johny", this.hash.toString());
    }

    @Override
    public String toString() {
        return "Block{" +
                "timestamp=" + timestamp +
                ", data='" + data + '\'' +
                ", hash='" + hash + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", nonce=" + nonce +
                ", transaction=" + transaction +
                '}';
    }
}




