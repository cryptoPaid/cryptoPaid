package com.example.fintech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fintech.Classes.Block;
import com.example.fintech.Classes.BlockChain;
import com.example.fintech.Classes.User;
import com.example.fintech.Classes.Wallet;
//import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private TextView Login_LBL_forgotPass;
    private Button Login_BTN_register;
    private Button Login_BTN_login;
    private EditText Login_EDT_password;
    private EditText Login_EDT_username;
    private String email;
    private String password;
    byte[] publicKey;
    byte[] privateKey;
    Wallet wallet;
//    private ObjectMapper jackson = new ObjectMapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        Login_LBL_forgotPass.setOnClickListener(fillData);
        Login_BTN_register.setOnClickListener(fillData);
        Login_BTN_login.setOnClickListener(fillData);
        Login_EDT_password.setOnClickListener(fillData);
        Login_EDT_username.setOnClickListener(fillData);
    }

    private View.OnClickListener fillData = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            buttonClicked(view);
        }
    };

    private void buttonClicked(View view) {
        if (view.getTag().toString().equals("login")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            Log.d("login", "login clicked ");
            loginRequest();
        } else if ((view.getTag().toString().equals("register"))) {
            Intent intent = new Intent(this, RegisterActivity.class);
            this.startActivity(intent);
        } else if ((view.getTag().toString().equals("forgotPass"))) {

        }

    }

    private void loginRequest() {

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
//        Login_EDT_username.setText("stas.krot1996@gmail.com");
        String url = "http://10.0.0.6:8050/blockchain/users/login/2021b.johny.stas/" + Login_EDT_username.getText().toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("login", "person " + response.toString());

//                 Loop through the array elements
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject userID = response.getJSONObject("userId");
                        String role = response.getString("role");
                        String username = response.getString("username");
                        String password = response.getString("password");
                        if(!checkPasswordValidity(password)){
                            Toast.makeText(LoginActivity.this,"Failed to Login",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonWallet = response.getJSONObject("wallet");
                        if (readPublic(jsonWallet.getString("publicKey")) != null && readPrivate(jsonWallet.getString("privateKey")) != null){
                            publicKey = jsonWallet.getString("publicKey").getBytes();
                            privateKey = jsonWallet.getString("privateKey").getBytes();
//                            wallet = new Wallet(privateKey, publicKey, Double.parseDouble(jsonWallet.getString("balance")));
                        } else {
                            Log.d("error", "Cannot read Keys");
                            return;
                        }

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("role", role);
                        intent.putExtra("username", username);
                        intent.putExtra("publicKey", publicKey);
                        intent.putExtra("privateKey", privateKey);
                        intent.putExtra("balance", Double.parseDouble(jsonWallet.getString("balance")));
                        startActivity(intent);
                        finish();

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

            private boolean checkPasswordValidity(String password) {
                if (password != User.calculateHash(Login_EDT_password.getText().toString())){
                    return false;
                }
                return true;
            }

            private PublicKey readPublic(String publicKey) {
                KeyFactory keyFactory = null;
                try {
                    keyFactory = KeyFactory.getInstance("RSA");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKey.getBytes());
                PublicKey pk = null;
                try {
                    pk = keyFactory.generatePublic(publicKeySpec);
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }
                return pk;

            }

            private PrivateKey readPrivate(String privateKey) {
                KeyFactory keyFactory = null;
                try {
                    keyFactory = KeyFactory.getInstance("RSA");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                X509EncodedKeySpec privateKeySpec = new X509EncodedKeySpec(privateKey.getBytes());
                PrivateKey pk = null;
                try {
                    pk = keyFactory.generatePrivate(privateKeySpec);
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }
                return pk;
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onLoginFailed();
            }
        });
        requestQueue.add(jsonObjectRequest);


    }

    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Failed to Login" , Toast.LENGTH_LONG).show();
    }



//    // use Jackson to convert JSON to Object
//    private <T> T unmarshal(String json, Class<T> type) {
//        try {
//            //jackson.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//            //System.out.println("in unmarshal " + json.toString());
//            return this.jackson.readValue(json, type);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private String marshal(Object moreDetails) {
//        try {
//            //jackson.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//            //System.out.println("in marshal " + moreDetails.toString());
//            return this.jackson.writeValueAsString(moreDetails.toString());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private void findViews() {
        Login_LBL_forgotPass = findViewById(R.id.Login_LBL_forgotPass);
        Login_BTN_register = findViewById(R.id.Login_BTN_register);
        Login_BTN_login = findViewById(R.id.Login_BTN_login);
        Login_EDT_password = findViewById(R.id.Login_EDT_password);
        Login_EDT_username = findViewById(R.id.Login_EDT_username);
    }
}