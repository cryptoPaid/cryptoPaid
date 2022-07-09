package com.example.fintech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.example.fintech.Classes.TransactionAdapter;
import com.example.fintech.Classes.User;
import com.example.fintech.Classes.ViewAdapter;

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

//    private TableView transwait_TBL_table;
    private Button transwait_BTN_waiting;
    private Button transwait_BTN_show;
    private Button transwait_BTN_approve;

    private ArrayList<Transaction> pendingTransaction = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private ViewAdapter viewAdapter;
    private RecyclerView traswait_LST_listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transwait);
        findViews();

        transwait_BTN_show.setVisibility(View.VISIBLE);
        transwait_BTN_waiting.setVisibility(View.VISIBLE);
        transwait_BTN_show.setOnClickListener(clicked);
        transwait_BTN_waiting.setOnClickListener(clicked);
        transwait_BTN_approve.setOnClickListener(clicked);

    }


    private View.OnClickListener clicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("stas", v.getTag().toString());
            if(v.getTag().toString().equals("waiting")){
                try {
                    showTransactions("noactive");
                    transwait_BTN_waiting.setVisibility(View.INVISIBLE);
                    transwait_BTN_show.setVisibility(View.INVISIBLE);


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
                    transwait_BTN_waiting.setVisibility(View.INVISIBLE);
                    transwait_BTN_show.setVisibility(View.INVISIBLE);
                    showTransactions("active");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (SignatureException e) {
                    e.printStackTrace();
                }
            }
            else if(v.getTag().toString().equals("approve")){
                try {
                    transwait_BTN_waiting.setVisibility(View.INVISIBLE);
                    transwait_BTN_show.setVisibility(View.INVISIBLE);
                    showTransactions("approve");
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





    private void showTransactions(String option) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

//        for (int i=0; i<10; i++){
//            pendingTransaction.add(new Transaction("stas"+i,
//                    "johny_"+i,
//                    100*i,
//                    false ,
//                    "my Transaction_"+i ,
//                    new Timestamp(System.currentTimeMillis())));
//        }
        getTransactions(option);


    }


    private void getTransactions(String option) {

        RequestQueue requestQueue = Volley.newRequestQueue(TranswaitActivity.this);
        String url = "http://10.0.0.4:8050/blockchain/items/2021b.johny.stas/stas.krot1996@gmail.com/"+option;

        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("waitstas","JSON " + response.toString());
                for (int i=0; i < response.length(); i++)
                {
                    try {
                        JSONObject transaction = response.getJSONObject(i);
                        String from = transaction.getString("fromAddress");
                        String to = transaction.getString("toAddress");
                        Double amount = transaction.getDouble("amount");
                        Boolean active = transaction.getBoolean("active");
                        String name = transaction.getString("name");
                        String timestamp = transaction.getString("createdTimestamp");
                        JSONObject item = transaction.getJSONObject("itemId");
                        int id = item.getInt("id");
                   //     Date date = convetTime(timestamp);

                        Transaction tr = new Transaction(id,from,to,amount, active,name,new Date(System.currentTimeMillis()));
                        pendingTransaction.add(tr);


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
                Log.d("stas", "sizeing " + pendingTransaction.size());
                if (option.equals("active")){
                    Log.d("pttt", option);
                    initAdapter(0);
                } else if (option.equals("noactive")){
                    Log.d("pttt", option);
                    initAdapter(1);
                } else if (option.equals("approve")){
                    initAdapter(2);
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

    private void initAdapter(int visible) {
        Log.d("stas", "size " + pendingTransaction.size());
        linearLayoutManager = new LinearLayoutManager(TranswaitActivity.this);
        traswait_LST_listView.setLayoutManager(linearLayoutManager);
        viewAdapter = new ViewAdapter(pendingTransaction, visible);
        traswait_LST_listView.setAdapter(viewAdapter);
    }


    private void findViews() {
        transwait_BTN_show = findViewById(R.id.transwait_BTN_show);
        transwait_BTN_waiting = findViewById(R.id.transwait_BTN_waiting);
        traswait_LST_listView = findViewById(R.id.traswait_LST_listView);
        transwait_BTN_approve = findViewById(R.id.transwait_BTN_approve);
        traswait_LST_listView.setHasFixedSize(true);

    }


}