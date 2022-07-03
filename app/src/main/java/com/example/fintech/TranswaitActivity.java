package com.example.fintech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fintech.Classes.BlockChain;
import com.example.fintech.Classes.Transaction;
import com.example.fintech.Classes.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class TranswaitActivity extends AppCompatActivity {

    private ArrayList<Transaction> pendingTransaction = new ArrayList<>();
    private TableView table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transwait);
//        getTransactions();
        findViews();
        showWaitingTransactions();

    }




    private void showWaitingTransactions() {
        for (int i=0; i<3; i++){
            pendingTransaction.add(new Transaction("stas"+i,
                    "johny_"+i,
                    100*i,
                    false ,
                    "my Transaction_"+i ,
                    new Timestamp(System.currentTimeMillis())));
        }



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


    private void getTransactions() {

        RequestQueue requestQueue = Volley.newRequestQueue(TranswaitActivity.this);
        String url = "http://10.0.0.6:8050/blockchain/users/login/2021b.johny.stas/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

//                 Loop through the array elements
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject pendingTransactionsJs = response.getJSONObject("pendingTransaction");
                        String date = pendingTransactionsJs.getString("createdTimestamp");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date timeStamp = null;
                        try {
                            timeStamp = sdf.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Transaction tr = new Transaction(pendingTransactionsJs.getString("fromAddress"),
                                pendingTransactionsJs.getString("toAddress"),
                                pendingTransactionsJs.getDouble("amount"),
                                pendingTransactionsJs.getBoolean("active"),
                                pendingTransactionsJs.getString("name"),
                                timeStamp
                                );
                        pendingTransaction.add(tr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


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
        requestQueue.add(jsonObjectRequest);


    }

    private void findViews() {
        table = findViewById(R.id.transwait_TBL_table);

    }


}