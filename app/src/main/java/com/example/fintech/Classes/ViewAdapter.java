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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fintech.LoadingActivity;
import com.example.fintech.R;

import org.json.JSONException;
import org.json.JSONObject;

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

            if (buttonVisible == 0){
                transaction_row_item_BTN_ming.setVisibility(View.INVISIBLE);
            }else{
                transaction_row_item_BTN_ming.setVisibility(View.VISIBLE);
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
                        updateTransaction(transaction.getId()+"", view);

                    }
                }
            });
        }
    }


    private static void updateTransaction(String id, View view) {
        String email = "stas.krot1996@gmail.com";
        Log.d("pttt", "id " + id);
        String url = "http://192.168.1.223:8050/blockchain/items/2021b.johny.stas/stas.krot1996@gmail.com/2021b.johny.stas/"+ id;
        JSONObject js = new JSONObject();
        JSONObject itemIdJs = new JSONObject();
//        JSONArray tranJS = new JSONArray();

        try {
//            for (int i=0; i < pendingTransaction.size(); i++){
            JSONObject transactionJs = new JSONObject();
            itemIdJs.put("space","2021b.johny.stas");
            itemIdJs.put("id",1);
            js.put("itemId", itemIdJs);
            js.put("type","transaction");
            js.put("name", "sending 123 to XXX");
            js.put("active",true);
            js.put("approve", true);
            js.put("amount",10);
            js.put("toAddress","yonatani94@gmail.com");
            js.put("fromAddress","stas.krot1996@gmail.com");
            js.put("createdTimestamp", new Date(System.currentTimeMillis()));
            js.put("hash","abcdefg");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.PUT, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("pstt", response.toString() + " i am queen");
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
