package com.example.fintech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
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
import com.example.fintech.Classes.CustomJsonRequest;
import com.example.fintech.Classes.Transaction;
import com.example.fintech.Classes.User;
import com.example.fintech.Classes.UserId;
import com.example.fintech.Classes.Wallet;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private Button Register_BTN_register;
    private EditText Register_EDT_password;
    private EditText Register_EDT_email;
    private EditText Register_EDT_last;
    private EditText Register_EDT_first;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private KeyPairGenerator keyPairGenerator;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private Spinner Register_SPNR_Role;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        Register_BTN_register.setOnClickListener(changeActivity);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.role));
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Register_SPNR_Role.setAdapter(typeAdapter);
        Register_SPNR_Role.setOnItemSelectedListener(onContextItemSelected);
    }

    private View.OnClickListener changeActivity = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d("stas", "private " + view.getTag().toString());
            try {
                postRequest();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }

            if (view.getTag().toString().equals("register")) {

                email = Register_EDT_email.getText().toString();
                firstName = Register_EDT_first.getText().toString();
                lastName = Register_EDT_last.getText().toString();
                password = User.calculateHash(Register_EDT_password.getText().toString());
                if(checkValidity()){
//                    createUser();
                    Log.d("stas", user.toString());
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }



              //  if(checkValidity()){
                    //Wallet wallet = new Wallet("private","public",100000);
                    //User user = new User(email,password,email,wallet,firstName,lastName);


            }
        }


    };

    private void createUser() throws InvalidKeySpecException, NoSuchAlgorithmException {
        generateKeys();
        Log.d("stas", "private " +  privateKey);
        Log.d("stas", "public " + publicKey);
        user = new User(email,password,email,role ,new Wallet(privateKey,publicKey,1000),firstName,lastName, new BlockChain(), new ArrayList<>());
        postRequest();

        // TODO: 01/04/2022
        /*
         *  CHECK VALIDITY OF USER INPUT
         *  SEND USER DETAILS TO DATABASE
         *
         *
         *
         *
         * */


    }


    private void generateKeys() {
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private boolean checkValidity() {
        if (!emailValidator(email)) {
            return false;
        }
        if (!isValidPassword(password)) {
            return false;
        }
        if(role == null){
            role = "Regular";
        }
        return true;
    }


    public boolean emailValidator(String emailAddress) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
            return Pattern.compile(regexPattern)
                    .matcher(emailAddress)
                    .matches();

        // TODO: 01/04/2022
        /*
        *  CHECK IF EMAIL ALREADY IN THE DATABASE
        *
        *
        * */
        }

    public static boolean isValidPassword(String password)
    {
        boolean isValid = true;
        if (password.length() > 15 || password.length() < 8)
        {
            System.out.println("Password must be less than 20 and more than 8 characters in length.");
            isValid = false;
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars ))
        {
            System.out.println("Password must have at least one uppercase character");
            isValid = false;
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars ))
        {
            System.out.println("Password must have at least one lowercase character");
            isValid = false;
        }
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers ))
        {
            System.out.println("Password must have at least one number");
            isValid = false;
        }
        String specialChars = "(.*[!,@,#,$,%].*$)";
        if (!password.matches(specialChars ))
        {
            System.out.println("Password must have at least one special character among !@#$%");
            isValid = false;
        }
        return isValid;
    }


    private AdapterView.OnItemSelectedListener onContextItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(Register_SPNR_Role.getSelectedItem().toString().equals("Admin User")){
                role = "Admin";
            }else if(Register_SPNR_Role.getSelectedItem().toString().equals("Regular User")){
                role = "Regular";
            }else if(Register_SPNR_Role.getSelectedItem().toString().equals("Manager User")){
                role = "MANAGER";
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            return;
        }
    };


    private void findViews() {
        Register_BTN_register = findViewById(R.id.Register_BTN_register);
        Register_EDT_password = findViewById(R.id.Register_EDT_password);
        Register_EDT_email = findViewById(R.id.Register_EDT_email);
        Register_EDT_last = findViewById(R.id.Register_EDT_last);
        Register_EDT_first = findViewById(R.id.Register_EDT_first);
        Register_SPNR_Role = findViewById(R.id.Register_SPNR_Role);
    }






    ////////// TEST FOR USER API ///////////////
    private void postRequest() throws NoSuchAlgorithmException, InvalidKeySpecException {
        generateKeys();
        BlockChain bc = new BlockChain();
        user = new User("stas.krot1996@gmail.com","Password1","stas.krot1996@gmail.com","MANAGER" ,new Wallet(privateKey,publicKey,1000),"Johny","Arami", bc, new ArrayList<>());
        byte[] publicEncode = publicKey.getEncoded();
        byte[] privateEncode = privateKey.getEncoded();

/**********************
            READ KEYS FROM SEREVR

         KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bytes);
        PublicKey pk = keyFactory.generatePublic(publicKeySpec);
 ****************************/
//        Block myblock = new Block(new Timestamp(System.currentTimeMillis()), block1, "0");
//        ArrayList<Block> blockchain =new ArrayList<>();
//        blockchain.add(myblock);
//        blockchain.add(myblock2);

        //User user = new User("stas.krot1996@gmail.com", "MANAGER", "Demo User","451451dvd");
        //UserId userId = new UserId("2021b.johny.stas","stas.krot1996@gmail.com");
        String url = "http://10.0.0.4:8050/blockchain/users/";
        JSONObject js = new JSONObject();
        JSONObject walletJs = new JSONObject();
        JSONObject blockjs = new JSONObject();
        JSONObject blockjs2 = new JSONObject();
        JSONObject blockChainJs = new JSONObject();
        JSONArray blocks = new JSONArray();


        try {
            js.put("role",user.getRole());
            js.put("email", user.getEmail());
            js.put("username",user.getEmail());
            js.put("password",user.getPassword());

            /*********   PUT GENESIS BLOCK  ***********/
            blockjs.put("timestamp", bc.getChain().get(0).getTimestamp());
            blockjs.put("data", bc.getChain().get(0).getData());
            blockjs.put("hash", bc.getChain().get(0).getHash());
            blockjs.put("prevHash", bc.getChain().get(0).getPreviousHash());
            blockjs.put("nonce", 0);
            blocks.put(blockjs);
//            blockChainJs.put("list", blockjs);
//
//            blockjs2.put("timestamp", blockchain.get(1).getTimestamp());
//            blockjs2.put("data", blockchain.get(1).getData());
//            blockjs2.put("hash", blockchain.get(1).getHash());
//            blockjs2.put("prevHash", blockchain.get(1).getPreviousHash());
//            blockjs2.put("nonce", 0);
//            blocks.put(blockjs2);
//
            blockChainJs.put("miningReward", user.getJohnstaCoin().getMiningReward());
            blockChainJs.put("difficulty", user.getJohnstaCoin().getDifficulty());
////            blockChainJs.put("list1", blockjs);
            blockChainJs.put("chain", blocks);
            js.put("johnStaCoin", blockChainJs.toString());

//
            walletJs.put("publicKey", publicEncode.toString());
            walletJs.put("privateKey", privateEncode.toString());
            walletJs.put("balance", user.getWallet().getBalance());
            js.put("wallet", walletJs);

            Log.d("post", js.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("post", "response: " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("post", "Error: " + error.getMessage());
                try {
                    byte[] htmlBodyBytes = error.networkResponse.data;
                    Log.e("post", new String(htmlBodyBytes), error);
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
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Content-Type","application/json; charset=utf-8");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(jsonObjReq);
//        _____________________________________

//        CustomJsonRequest customJsonRequest = new CustomJsonRequest(
//                Request.Method.POST, url, js,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        if(response.length() == 0){
//                            Toast.makeText(RegisterActivity.this,"Failed to register user",Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(RegisterActivity.this,"response " + response.toString(),Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        Log.d("post", response.toString() + " i am queen");
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("post", "Error: " + error.getMessage());
//                try {
//                    byte[] htmlBodyBytes = error.networkResponse.data;
//                    Log.e("post", new String(htmlBodyBytes), error);
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                }
//            }
//        }) {
//            @Override
//            protected Map<String,String> getParams(){
//                Log.d("post","getting params");
//                Map<String,String> params = new HashMap<String,String>();
//
//                Log.d("post","returned params");
//                return params;
//            }
//            @Override
//            public Map<String,String> getHeaders() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String,String>();
//
//                params.put("Content-Type","application/json; charset=utf-8");
//                return params;
//            }
//        };
//
//        Volley.newRequestQueue(this).add(customJsonRequest);
    }







}