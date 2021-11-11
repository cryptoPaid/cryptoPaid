package com.example.fintech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.fintech.Classes.Block;
import com.example.fintech.Classes.BlockChain;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class TransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        BlockChain stasCoin = new BlockChain();
        stasCoin.addBlock(new Block(1, new Timestamp(System.currentTimeMillis()), "amount 10"));
        stasCoin.addBlock(new Block(2, new Timestamp(System.currentTimeMillis()), "amount 150"));
        JSONObject block = new JSONObject();
        JSONObject blockchain = new JSONObject();
        for (int i = 0; i < stasCoin.getChain().size(); i++) {
            try {
                block.put("index", stasCoin.getChain().get(i).getIndex());
                block.put("previousHash", stasCoin.getChain().get(i).getPreviousHash());
                block.put("timestamp", stasCoin.getChain().get(i).getTimestamp());
                block.put("data", stasCoin.getChain().get(i).getData());
                block.put("hash", stasCoin.getChain().get(i).getHash());
                blockchain.put("chain", block);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.d("stas",blockchain.toString());

    }
}