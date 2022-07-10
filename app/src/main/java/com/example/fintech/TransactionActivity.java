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
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
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
    private Spinner transaction_SPNR_difficulty;
    private Spinner transaction_SPNR_reward;
    BlockChain johnstaCoin = new BlockChain();
    ArrayList<Transaction> pendingTransaction = new ArrayList<>();
    HashMap<String, JSONObject> myJson = new HashMap<>();
    private String username;
    private String role;
    double balance;
    byte[] publicKey;
    byte[] privateKey;
    byte[] encodePublicKey;
    byte[] encodePrivateKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        role = getIntent().getStringExtra("role");
        username = getIntent().getStringExtra("username");
        publicKey = getIntent().getByteArrayExtra("publicKey");
        privateKey = getIntent().getByteArrayExtra("privateKey");
        balance = Double.parseDouble(getIntent().getStringExtra("balance"));
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

        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<String>(TransactionActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.difficulty));
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transaction_SPNR_difficulty.setAdapter(difficultyAdapter);
        transaction_SPNR_difficulty.setOnItemSelectedListener(onContextItemSelected);

        ArrayAdapter<String> rewardAdapter = new ArrayAdapter<String>(TransactionActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.reward));
        rewardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transaction_SPNR_reward.setAdapter(rewardAdapter);
        transaction_SPNR_reward.setOnItemSelectedListener(onContextItemSelected);

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
                } catch (SignatureException | InvalidKeySpecException e) {
                    e.printStackTrace();
                }
            }

        }
    };

    private void createTransaction() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException {
        transaction_SPNR_reward.getSelectedItem().toString();
        if(transaction_SPNR_transType.getSelectedItem().toString().equals("Crypto johnSta")){
            StringBuffer sb = new StringBuffer("Sending ");
            sb.append(transaction_EDT_amount.getText().toString() + " " + "to " + transaction_EDT_address.getText().toString());

            if (!transaction_SPNR_difficulty.getSelectedItem().toString().equals("Select Difficulty")){
                johnstaCoin.setDifficulty(Integer.parseInt(transaction_SPNR_difficulty.getSelectedItem().toString()));
            }
            if (!transaction_SPNR_reward.getSelectedItem().toString().equals("Select Reward")){
                johnstaCoin.setMiningReward(Integer.parseInt(transaction_SPNR_reward.getTransitionName().toString()));
            }

            johnstaCoin.isChainValidate(pendingTransaction, privateKey);
            johnstaCoin.createTransaction(new Transaction(username, "yonatani94@gmail.com", Double.parseDouble(transaction_EDT_amount.getText().toString()), false , sb.toString(), new Date(System.currentTimeMillis()),privateKey, false), pendingTransaction);
            johnstaCoin.miningPendingTransaction(pendingTransaction.get(pendingTransaction.size()-1).getId(),username,pendingTransaction, sb.toString(), new Date(System.currentTimeMillis()), privateKey);

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
            if(transaction_SPNR_transType.getSelectedItem().toString().equals("Crypto johnSta")){
                transaction_EDT_address.setVisibility(View.VISIBLE);
                transaction_EDT_amount.setVisibility(View.VISIBLE);
                transaction_SPNR_contractType.setVisibility(View.INVISIBLE);
            }else if(transaction_SPNR_transType.getSelectedItem().toString().equals("Contract")){
                transaction_SPNR_contractType.setVisibility(View.VISIBLE);
                transaction_EDT_address.setVisibility(View.INVISIBLE);
                transaction_EDT_amount.setVisibility(View.INVISIBLE);
            }else if(transaction_SPNR_difficulty.getSelectedItem().toString().equals("difficulty")){

            }else if (transaction_SPNR_reward.getSelectedItem().toString().equals("reward")){

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
        transaction_SPNR_difficulty = findViewById(R.id.transaction_SPNR_difficulty);
        transaction_SPNR_reward = findViewById(R.id.transaction_SPNR_reward);

    }

    private void send(String s) {
        int i = 0;

        while (i < 100000){
            i++;
        }
    }


    ////////// TEST FOR USER API ///////////////
    private void postTransaction() {

        String url = "http://10.0.0.4:8050/blockchain/items/2021b.johny.stas/" + username;
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
                js.put("active",false);
                js.put("approve", false);
                js.put("amount",pendingTransaction.get(pendingTransaction.size()-1).getAmount());
//                js.put("toAddress",pendingTransaction.get(pendingTransaction.size()-1).getToAddress());
//                js.put("fromAddress",pendingTransaction.get(pendingTransaction.size()-1).getFromAddress());                js.put("toAddress",pendingTransaction.get(pendingTransaction.size()-1).getToAddress());
                js.put("fromAddress","stas.krot1996@gmail.com");
                js.put("toAddress","yonatani94@gmail.com");

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