package com.example.fintech;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fintech.Classes.BlockChain;
import com.example.fintech.Classes.CustomJsonRequest;
import com.example.fintech.Classes.Transaction;
import com.example.fintech.Classes.User;

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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class TranswaitActivity extends AppCompatActivity {

    private ArrayList<Transaction> pendingTransaction = new ArrayList<>();
    private TableView table;
    private Button transwait_BTN_update;
    private Button transwait_BTN_waiting;
    private Button transwait_BTN_show;
    private LinearLayout transwait_LAY_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transwait);
//        getTransactions();
        findViews();
        transwait_BTN_update.setOnClickListener(clicked);
        transwait_BTN_waiting.setOnClickListener(clicked);
        transwait_BTN_show.setOnClickListener(clicked);
        transwait_LAY_button.setOnClickListener(clicked);

        //getTransactions();

//        showWaitingTransactions();

    }




    private View.OnClickListener clicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("stas", v.getTag().toString());
            if(v.getTag().toString().equals("update")){
                updateTransaction();
            }
            else if(v.getTag().toString().equals("waiting")){
                try {
                    showTransactions("noactive");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (SignatureException e) {
                    e.printStackTrace();
                }
            }
            else if(v.getTag().toString().equals("show")){
                try {
                    showTransactions("active");
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

    private void updateTransaction() {
        String email = "stas.krot1996@gmail.com";
        String url = "http://192.168.1.223:8050/blockchain/items/2021b.johny.stas/stas.krot1996@gmail.com/2021b.johny.stas/1";
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

        Volley.newRequestQueue(this).add(jsonObjReq);
    }




    private void showTransactions(String option) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
//        for (int i=0; i<3; i++){
//            pendingTransaction.add(new Transaction("stas"+i,
//                    "johny_"+i,
//                    100*i,
//                    false ,
//                    "my Transaction_"+i ,
//                    new Timestamp(System.currentTimeMillis())));
//        }


        getTransactions(option);

        String[] headers = {"toAddress", "amount", "date"};
        table.setHeaderAdapter(new SimpleTableHeaderAdapter(this, headers));
        List<String[]> list = new ArrayList<>();
        for (int i=0; i<pendingTransaction.size(); i++){
            String[] myData = {pendingTransaction.get(i).getToAddress(),
                    String.valueOf(pendingTransaction.get(i).getAmount()),
                    pendingTransaction.get(i).getTimestamp().toString()};
            list.add(myData);
            table.setDataAdapter(new SimpleTableDataAdapter(this, list));

        }


    }


    private void getTransactions(String option) {

        RequestQueue requestQueue = Volley.newRequestQueue(TranswaitActivity.this);
        String url = "http://10.0.0.4:8050/blockchain/items/2021b.johny.stas/stas.krot1996@gmail.com/"+option;

        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("waitstas","JSON " + response.toString());
                for (int i=0; i < response.length()-1; i++)
                {
                    try {

//            pendingTransaction.add(new Transaction("stas"+i,
//                    "johny_"+i,
//                    100*i,
//                    false ,
//                    "my Transaction_"+i ,
//                    new Timestamp(System.currentTimeMillis())));


                        JSONObject transaction = response.getJSONObject(i);
                        String from = transaction.getString("fromAddress");
                        String to = transaction.getString("toAddress");
                        Double amount = transaction.getDouble("amount");
                        Boolean active = transaction.getBoolean("active");
                        String name = transaction.getString("name");
                        String type = transaction.getString("type");
                        String timestamp = transaction.getString("timestamp");
                        Date date = convetTime(timestamp);

                        Transaction tr = new Transaction(from,to,amount, active,name,date);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (SignatureException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    }


                }


            }

            private Date convetTime(String timestamp) {


                Date date = Date.valueOf(timestamp);
                return date;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("waitstas", "error " + error.getMessage());
            }
        });
        requestQueue.add(JsonArrayRequest);
    }


    private void findViews() {
        table = findViewById(R.id.transwait_TBL_table);
        transwait_BTN_update = findViewById(R.id.transwait_BTN_update);
        transwait_BTN_waiting = findViewById(R.id.transwait_BTN_waiting);
        transwait_BTN_show = findViewById(R.id.transwait_BTN_show);
        transwait_LAY_button = findViewById(R.id.transwait_LAY_button);
    }


}