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

import com.example.fintech.Classes.BlockChain;
import com.example.fintech.Classes.User;
import com.example.fintech.Classes.Wallet;
import com.google.android.material.button.MaterialButton;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
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

            if (view.getTag().toString().equals("register")) {

                email = Register_EDT_email.getText().toString();
                firstName = Register_EDT_first.getText().toString();
                lastName = Register_EDT_last.getText().toString();
                password = Register_EDT_password.getText().toString();
                if(checkValidity()){
                    createUser();
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

    private void createUser() {
        generateKeys();
        Log.d("stas", "private " +  privateKey);
        Log.d("stas", "public " + publicKey);
        user = new User(email,password,email,role ,new Wallet(privateKey,publicKey,1000),firstName,lastName, new BlockChain(), new ArrayList<>());
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
}