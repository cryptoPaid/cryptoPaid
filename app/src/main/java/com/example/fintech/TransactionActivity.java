package com.example.fintech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.fintech.Classes.Block;
import com.example.fintech.Classes.BlockChain;
import com.example.fintech.Classes.Transaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        BlockChain stasCoin = new BlockChain();
//console.log('Blockchain valid?  ' + micaCoin.isChainValidate())

        stasCoin.createTransaction(new Transaction("address1", "address2", 100));
        stasCoin.createTransaction(new Transaction("address2", "address1", 50));

//console.log('Blockchain valid?  ' + micaCoin.isChainValidate())
        Log.d("johny","Starting the miner");
        stasCoin.miningPendingTransaction("Bob");
        Log.d("johny", "Balance of Bob: "+ stasCoin.getBalanceOfAddress("Bob"));


        stasCoin.createTransaction(new Transaction("Bob", "address1", 50));
        stasCoin.miningPendingTransaction("Bob");
        Log.d("johny", "Balance of Bob: "+ stasCoin.getBalanceOfAddress("Bob"));


//
//        ArrayList<JSONObject> blockArr = new ArrayList<>();
//        JSONObject blockchain = new JSONObject();
//
//        for (int i = 0; i < stasCoin.getChain().size(); i++) {
//            try {
//                JSONObject block = new JSONObject();
////                block.put("index", stasCoin.getChain().get(i).getIndex());
//                block.put("previousHash", stasCoin.getChain().get(i).getPreviousHash());
//                block.put("timestamp", stasCoin.getChain().get(i).getTimestamp());
//                block.put("data", stasCoin.getChain().get(i).getData());
//                block.put("hash", stasCoin.getChain().get(i).getHash());
//                blockArr.add(block);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            blockchain.put("chain", blockArr);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
}