package com.example.fintech.Classes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fintech.LoadingActivity;
import com.example.fintech.LoginActivity;
import com.example.fintech.MainActivity;
import com.example.fintech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.TransactionViewHolder> {
    private ArrayList<Transaction> transactions;
    public static int buttonVisible;



    public ViewAdapter(ArrayList<Transaction> transactions, int buttonVisible){
        this.transactions = transactions;
        this.buttonVisible = buttonVisible;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_row_item, parent, false);
        TransactionViewHolder transactionViewHolder = new TransactionViewHolder(view);
        return transactionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Transaction transaction = transactions.get(position);
        Log.d("pttt", "position  " + position);

        Log.d("pttt", "trsnsaction " + transaction);
        holder.transaction_row_item_LBL_amount.setText(transaction.getAmount() + " JonhSta");
        holder.transaction_row_item_LBL_toAddress.setText(transaction.getToAddress());
        holder.transaction_row_item_LBL_title.setText(transaction.getName());
        holder.transaction_row_item_LBL_time.setText(transaction.getTimestamp()+"");
        holder.position = position;
        holder.transaction = transaction;
    }



    @Override
    public int getItemCount() {
        Log.d("stas", "adapter size " + this.transactions.size());
        return this.transactions.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder{
        private TextView transaction_row_item_LBL_amount;
        private TextView transaction_row_item_LBL_toAddress;
        private TextView transaction_row_item_LBL_title;
        private TextView transaction_row_item_LBL_time;
        private Button transaction_row_item_BTN_ming;
        private Button transaction_row_item_BTN_approve;
        private TextView transaction_row_item_LBL_checked;


        private View rootView;
        private int position;
        private Transaction transaction;

        public TransactionViewHolder(@NonNull View view){
            super(view);
            rootView = view;
            transaction_row_item_LBL_amount = view.findViewById(R.id.transaction_row_item_LBL_amount);
            transaction_row_item_LBL_toAddress = view.findViewById(R.id.transaction_row_item_LBL_toAddress);
            transaction_row_item_LBL_title = view.findViewById(R.id.transaction_row_item_LBL_title);
            transaction_row_item_LBL_time = view.findViewById(R.id.transaction_row_item_LBL_time);
            transaction_row_item_BTN_ming = view.findViewById(R.id.transaction_row_item_BTN_ming);
            transaction_row_item_BTN_approve = view.findViewById(R.id.transaction_row_item_BTN_approve);
            transaction_row_item_LBL_checked = view.findViewById(R.id.transaction_row_item_LBL_checked);

            if (buttonVisible == 0){
                transaction_row_item_BTN_ming.setVisibility(View.INVISIBLE);
                transaction_row_item_BTN_approve.setVisibility(View.INVISIBLE);
                                    transaction_row_item_LBL_checked.setVisibility(View.VISIBLE);
//
//                Log.d("activeapprove", transaction.isApprove() + " " + transaction.getActive());
//                if(transaction.getActive() && transaction.isApprove()){
////                    transaction_row_item_LBL_checked.setVisibility(View.VISIBLE);
//                }
            }else if (buttonVisible == 1){
                transaction_row_item_BTN_ming.setVisibility(View.VISIBLE);
                transaction_row_item_BTN_approve.setVisibility(View.INVISIBLE);

            }else if (buttonVisible == 2){
                transaction_row_item_BTN_ming.setVisibility(View.INVISIBLE);
                transaction_row_item_BTN_approve.setVisibility(View.VISIBLE);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("stas", "transaction selected: " + position  + " transaction " + transaction.toString());

                }
            });

            view.findViewById(R.id.transaction_row_item_BTN_ming).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getTag().toString().equals("ming")){

                        Intent intent = new Intent(view.getContext(), LoadingActivity.class);
                        view.getContext().startActivity(intent);
                        updateTransaction(transaction.getId()+"", view, false, transaction);

                    }
                }
            });
            view.findViewById(R.id.transaction_row_item_BTN_approve).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getTag().toString().equals("approve")) {
                        Log.d("block", "test 1");
                        try {
                            getBlockChain(view, transaction);
                        } catch (SignatureException e) {
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (InvalidKeyException e) {
                            e.printStackTrace();
                        }


                    }
                }
            });
        }
    }


    private static void updateBlockChain(View view,  ArrayList<Block> block , Block b, BlockChain bc) {
        String url = "http://10.0.0.4:8050/blockchain/users/login/2021b.johny.stas/stas.krot1996@gmail.com/blockchain";
        JSONObject js = new JSONObject();
        JSONArray chainjs = new JSONArray();
        try {
            for (int i=0; i < bc.getChain().size(); i++) {
                JSONObject itemIdJs = new JSONObject();

                itemIdJs.put("timestamp", bc.getChain().get(i).getTimestamp());
                itemIdJs.put("data", bc.getChain().get(i).getData());
                itemIdJs.put("hash", bc.getChain().get(i).getHash());
                itemIdJs.put("prevHash", bc.getChain().get(i).getPreviousHash());
                itemIdJs.put("nonce", 0);
                chainjs.put(itemIdJs);
//
                js.put("miningReward", bc.getMiningReward());
                js.put("difficulty", bc.getDifficulty());
////            blockChainJs.put("list1", blockjs);
                js.put("chain", itemIdJs);


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.PUT, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("blockchain", response.toString() + " i am queen");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("blockchain", "Error: " + error.getMessage());
                try {
                    byte[] htmlBodyBytes = error.networkResponse.data;
                    Log.e("stasptt", new String(htmlBodyBytes), error);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            protected Map<String,String> getParams(){
                Log.d("stas1","getting params");
//                Gson gson = new Gson();
//                String json = gson.toJson(account);
                Map<String,String> params = new HashMap<String,String>();

                Log.d("stas1","returned params");
                return params;
            }
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                params.put("Content-Type","application/json; charset=utf-8");
                return params;
            }
        };

        Volley.newRequestQueue(view.getContext()).add(jsonObjReq);


    }

    private static void getBlockChain(View view, Transaction transaction) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        Log.d("block","test");
//        BlockChain john = new BlockChain();
//        ArrayList<Block> johnblocks = new ArrayList<>();
//        Block johnblock = new Block(new Date(System.currentTimeMillis()), "testing", john.getChain().get(0).getPreviousHash(), john.getChain().get(0).getHash());
//        ArrayList<Transaction> tra = new ArrayList<>();
//        tra.add(transaction);
//        Block newBlock = new Block(new Date(System.currentTimeMillis()), tra, john.getChain().get(0).getHash());
//        johnblocks.add(johnblock);
//        john.addBlock(newBlock);
//        Log.d("john blocks ", john.getChain().toString());



        //                    RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
//        Login_EDT_username.setText("stas.krot1996@gmail.com");
        String url = "http://10.0.0.4:8050/blockchain/users/login/2021b.johny.stas/stas.krot1996@gmail.com/blockchain";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<Block> block = new ArrayList<>();
//                 Loop through the array elements
                Log.d("blockchain", "blockchain " + response.toString());
                try {
                    double reward = response.getDouble("miningReward");
                    int diff = response.getInt("difficulty");
                    JSONArray jsArr = response.getJSONArray("chain");
                    Log.d("stas", jsArr + "");
                    for (int i=0; i<jsArr.length(); i++){
                        JSONObject chain = jsArr.getJSONObject(i);
                        String timestamp = chain.getString("timestamp");
                        String data = chain.getString("data");
                        String hash = chain.getString("hash");
                        String prevHash = chain.getString("prevHash");
                        int nonce = chain.getInt("nonce");
                        Block b1 = new Block(new Date(System.currentTimeMillis()),data,prevHash, hash);
                        block.add(b1);
                        Log.d("blocks", block.toString());

                    }
                    BlockChain bc  =  new BlockChain(block);
                    Block b = new Block(transaction.getTimestamp(),transaction.getName(), bc.getChain().get(bc.getChain().size()-1).getHash());
                    try {
                        bc.addBlock(b);
                    } catch (SignatureException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    }
                    updateTransaction(transaction.getId()+"", view, true, transaction);
                    updateBlockChain(view, block, b, bc);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                        Log.d("ptt",c.toString());
////                        Intent intent = new Intent(LoginActivity.this, StartUpActivity.class);
//                        intent.putExtra("EMAIL", c.getEmail());
//                        intent.putExtra("ROLE", c.getRole());
//                        Log.d("ptt","role is " + c.getRole());
//                        intent.putExtra("AVATAR", c.getAvatar());
//                        intent.putExtra("USERNAME", c.getUsername());
//                        intent.putExtra("NAME",c.getUsername());
//                        onLoginSuccess(c, intent);
//                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
//                    requestQueue.add(jsonObjectRequest);
        Volley.newRequestQueue(view.getContext()).add(jsonObjectRequest);



    }




    private static void updateTransaction(String id, View view, boolean approve, Transaction transaction) {
        String email = "stas.krot1996@gmail.com";
        Log.d("pttt", "id " + id);
        String url = "http://10.0.0.4:8050/blockchain/items/2021b.johny.stas/stas.krot1996@gmail.com/2021b.johny.stas/"+id;
        JSONObject js = new JSONObject();
        JSONObject itemIdJs = new JSONObject();
//        JSONArray tranJS = new JSONArray();

        try {
//            for (int i=0; i < pendingTransaction.size(); i++){
            JSONObject transactionJs = new JSONObject();
            itemIdJs.put("space","2021b.johny.stas");
            itemIdJs.put("id",id);
            js.put("itemId", itemIdJs);
            js.put("type","transaction");
            js.put("name", transaction.getName());
            js.put("email", email);
            js.put("active",true);
            js.put("approve", approve);
            js.put("amount",transaction.getAmount());
            js.put("toAddress",transaction.getToAddress());
            js.put("fromAddress",transaction.getFromAddress());
            js.put("createdTimestamp", transaction.getTimestamp());
            js.put("hash",transaction.getHash());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.PUT, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("pttt", response.toString() + " i am queen");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("pstt", "Error: " + error.getMessage());
                try {
                    byte[] htmlBodyBytes = error.networkResponse.data;
                    Log.e("stasptt", new String(htmlBodyBytes), error);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            protected Map<String,String> getParams(){
                Log.d("stas1","getting params");
//                Gson gson = new Gson();
//                String json = gson.toJson(account);
                Map<String,String> params = new HashMap<String,String>();

                Log.d("stas1","returned params");
                return params;
            }
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                params.put("Content-Type","application/json; charset=utf-8");
                return params;
            }
        };

        Volley.newRequestQueue(view.getContext()).add(jsonObjReq);
    }

}
