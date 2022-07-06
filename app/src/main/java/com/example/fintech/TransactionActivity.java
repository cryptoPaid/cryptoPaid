package com.example.fintech;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fintech.Classes.Block;
import com.example.fintech.Classes.BlockChain;
import com.example.fintech.Classes.Transaction;
import com.example.fintech.Classes.User;
import com.example.fintech.Classes.UserId;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import java.security.SignatureException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TransactionActivity extends AppCompatActivity {
    //    private ArrayList<Transaction> pendingTransaction = new ArrayList<>();
    private Spinner transaction_SPNR_transType;
    private Button transaction_BTN_create;
    private Spinner transaction_SPNR_contractType;
    private EditText transaction_EDT_address;
    private EditText transaction_EDT_amount;
    BlockChain johnstaCoin = new BlockChain();
    ArrayList<Transaction> pendingTransaction = new ArrayList<>();
    HashMap<String, JSONObject> myJson = new HashMap<>();

    private String username = "stas.krot1996@gmail.com";
    private String role;
    byte[] encodePublicKey;
    byte[] encodePrivateKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        findViews();
        transaction_BTN_create.setOnClickListener(clicked);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(TransactionActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types));
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transaction_SPNR_transType.setAdapter(typeAdapter);
        transaction_SPNR_transType.setOnItemSelectedListener(onContextItemSelected);

        ArrayAdapter<String> contractAdapter = new ArrayAdapter<String>(TransactionActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.contract));
        contractAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transaction_SPNR_contractType.setAdapter(contractAdapter);
        transaction_SPNR_contractType.setOnItemSelectedListener(onContextItemSelected);
//        username = this.getIntent().getStringExtra("username");
//        role = this.getIntent().getStringExtra("role");
//        encodePublicKey = this.getIntent().getStringExtra("publicKey").getBytes();
//        encodePrivateKey = this.getIntent().getStringExtra("privateKey").getBytes();
//        postRequest();

//
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
//        stasCoin.miningPendingTransaction("Bob", pendingTransaction);add
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

    private View.OnClickListener clicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getTag().toString().equals("create")){
                try {
                    createTransaction();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (SignatureException e) {
                    e.printStackTrace();
                }
            }

        }
    };

    private void createTransaction() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        if(transaction_SPNR_transType.getSelectedItem().toString().equals("Money")){
            StringBuffer sb = new StringBuffer("Sending ");
            sb.append(transaction_EDT_amount.getText().toString() + " " + "to " + transaction_EDT_address.getText().toString());
//            String date = new Timestamp(System.currentTimeMillis()+"");

//            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date timeStamp = null;
//            try {
//                timeStamp = sdf.parse(date);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            Log.d("mytransaction", timeStamp + "");

            johnstaCoin.createTransaction(new Transaction(username, "yonatani94@gmail.com", 10, false , sb.toString(), new Date(System.currentTimeMillis())), pendingTransaction);
            johnstaCoin.miningPendingTransaction(username,pendingTransaction, sb.toString(), new Date(System.currentTimeMillis()));
            // TODO: 7/6/2022
            /*****************
             *
             * POST UPDATE USER WITH NEW BLOCKCHAIN
             *
             *
             * *****************/
            Log.d("stas", new Date(System.currentTimeMillis()) + "");

            Log.d("stas", "address: " + transaction_EDT_address.getText().toString());
            Log.d("stas", "amount " + transaction_EDT_amount.getText().toString());

            postTransaction();

        }else if(transaction_SPNR_transType.getSelectedItem().toString().equals("Contract")){

            // TODO: 16/04/2022
            /*
             *  GET PRIVATE KEY FROM USER
             *  GET BLOCKCHAIN FROM USER
             *  GET USER PENDING TRANSACTIONS
             * */

//            johnstaCoin.createTransaction(new Transaction("address1", transaction_EDT_amount.getText().toString(), Double.parseDouble(transaction_EDT_amount.getText().toString())), pendingTransaction);

        }

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


    ////////// TEST FOR USER API ///////////////
    private void postTransaction() {

        String url = "http://192.168.1.223:8050/blockchain/items/2021b.johny.stas/" + username;
        JSONObject js = new JSONObject();
        JSONObject itemIdJs = new JSONObject();
//        JSONArray tranJS = new JSONArray();

        try {
//            for (int i=0; i < pendingTransaction.size(); i++){
                JSONObject transactionJs = new JSONObject();
                itemIdJs.put("space","2021b.johny.stas");
                itemIdJs.put("id",pendingTransaction.get(pendingTransaction.size()-1).getId());
                js.put("itemId", itemIdJs);
                js.put("type","transaction");
                js.put("name", pendingTransaction.get(pendingTransaction.size()-1).getName());
                js.put("active",pendingTransaction.get(pendingTransaction.size()-1).getActive());
                js.put("approve", false);
                js.put("amount",pendingTransaction.get(pendingTransaction.size()-1).getAmount());
                js.put("toAddress",pendingTransaction.get(pendingTransaction.size()-1).getToAddress());
                js.put("fromAddress",pendingTransaction.get(pendingTransaction.size()-1).getFromAddress());
                js.put("createdTimestamp", pendingTransaction.get(pendingTransaction.size()-1).getTimestamp());
                js.put("hash",pendingTransaction.get(pendingTransaction.size()-1).getHash());
//                tranJS.put(transactionJs);
//            }
//            js.put("pendingTransaction",tranJS);
            Log.d("mytransaction", js.toString());
            Log.d("mytransaction", pendingTransaction.size()+"");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("mytransaction", "response: " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d( "mytransaction", "Error: " + error.getMessage());
                try {
                    byte[] htmlBodyBytes = error.networkResponse.data;
                    Log.e("mytransaction", new String(htmlBodyBytes), error);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                return params;
            }
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError{
                Map<String,String> params = new HashMap<String,String>();
                params.put("Content-Type","application/json; charset=utf-8");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(jsonObjReq);
    }


}