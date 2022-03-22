package com.example.fintech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fintech.Classes.User;
import com.example.fintech.Classes.Wallet;
import com.google.android.material.button.MaterialButton;

public class RegisterActivity extends AppCompatActivity {

    private MaterialButton Register_BTN_register;
    private EditText Register_EDT_password;
    private EditText Register_EDT_email;
    private EditText Register_EDT_last;
    private EditText Register_EDT_first;
    private String email;
    private String password;
    private String firstName;
    private String lastName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        Register_BTN_register.setOnClickListener(changeActivity);
    }

    private View.OnClickListener changeActivity = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getTag().toString().equals("register")) {
                email = Register_EDT_email.getText().toString();
                firstName = Register_EDT_first.getText().toString();
                lastName = Register_EDT_last.getText().toString();
                password = Register_EDT_password.getText().toString();


                if(checkValidity()){
                    Wallet wallet = new Wallet("private","public",100000);
                    User user = new User(email,password,email,wallet,firstName,lastName);
                }
            }
        }
    };

    private boolean checkValidity() {
        if (Register_BTN_register.getText().toString().equals("") || Register_BTN_register.getText().toString().contains("@")) {
            return false;
        } else if (Register_EDT_email.getText().toString().equals("")) {
            return false;
        } else if (Register_EDT_first.getText().toString().equals("")) {
            return false;
        } else if (Register_EDT_last.getText().toString().equals("")) {
            return false;
        } else if (Register_EDT_password.getText().toString().equals("")) {
            return false;
        }
        return true;
    }


    private void findViews() {
        Register_BTN_register = findViewById(R.id.Register_BTN_register);
        Register_EDT_password = findViewById(R.id.Register_EDT_password);
        Register_EDT_email = findViewById(R.id.Register_EDT_email);
        Register_EDT_last = findViewById(R.id.Register_EDT_last);
        Register_EDT_first = findViewById(R.id.Register_EDT_first);

    }
}