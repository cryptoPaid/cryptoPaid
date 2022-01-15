package com.example.fintech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fintech.Classes.Block;
import com.example.fintech.Classes.BlockChain;
import com.example.fintech.Classes.Transaction;
import com.google.android.material.button.MaterialButton;

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
    private ArrayList<Transaction> pendingTransaction = new ArrayList<>();
    private Spinner transaction_SPNR_transType;
    private Button transaction_BTN_create;
    private Spinner transaction_SPNR_contractType;
    private EditText transaction_EDT_address;
    private EditText transaction_EDT_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        findViews();
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(TransactionActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types));
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transaction_SPNR_transType.setAdapter(typeAdapter);
        transaction_SPNR_transType.setOnItemSelectedListener(onContextItemSelected);

        ArrayAdapter<String> contractAdapter = new ArrayAdapter<String>(TransactionActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.contract));
        contractAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transaction_SPNR_contractType.setAdapter(contractAdapter);
        transaction_SPNR_contractType.setOnItemSelectedListener(onContextItemSelected);


//
//        BlockChain stasCoin = new BlockChain();
////console.log('Blockchain valid?  ' + micaCoin.isChainValidate())
//
//        stasCoin.createTransaction(new Transaction("address1", "address2", 100), pendingTransaction);
//        stasCoin.createTransaction(new Transaction("address2", "address1", 50), pendingTransaction);
//
////console.log('Blockchain valid?  ' + micaCoin.isChainValidate())
//        Log.d("johny","Starting the miner");
//        stasCoin.miningPendingTransaction("Bob", pendingTransaction);
//        Log.d("johny", "Balance of Bob: "+ stasCoin.getBalanceOfAddress("Bob"));
//
//        pendingTransaction = new ArrayList<>();
//        stasCoin.createTransaction(new Transaction("Bob", "address1", 50), pendingTransaction);
//        stasCoin.miningPendingTransaction("Bob", pendingTransaction);
//        Log.d("johny", "Balance of Bob: "+ stasCoin.getBalanceOfAddress("Bob"));


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

    private AdapterView.OnItemSelectedListener onContextItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(transaction_SPNR_transType.getSelectedItem().toString().equals("Money")){
                transaction_EDT_address.setVisibility(View.VISIBLE);
                transaction_EDT_amount.setVisibility(View.VISIBLE);
                transaction_SPNR_contractType.setVisibility(View.INVISIBLE);
            }else if(transaction_SPNR_transType.getSelectedItem().toString().equals("Contract")){
                transaction_SPNR_contractType.setVisibility(View.VISIBLE);
                transaction_EDT_address.setVisibility(View.INVISIBLE);
                transaction_EDT_amount.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            return;
        }
    };


    private void findViews() {
        transaction_SPNR_transType = findViewById(R.id.transaction_SPNR_transType);
        transaction_BTN_create = findViewById(R.id.transaction_BTN_create);
        transaction_SPNR_contractType = findViewById(R.id.transaction_SPNR_contractType);
        transaction_EDT_address = findViewById(R.id.transaction_EDT_address);
        transaction_EDT_amount = findViewById(R.id.transaction_EDT_amount);
    }

    private void send(String s) {
        int i = 0;

        while (i < 100000){
            i++;
        }
    }
}