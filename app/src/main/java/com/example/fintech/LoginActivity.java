package com.example.fintech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {
    private TextView Login_LBL_forgotPass;
    private Button Login_BTN_register;
    private Button Login_BTN_login;
    private EditText Login_EDT_password;
    private EditText Login_EDT_username;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        Login_LBL_forgotPass.setOnClickListener(fillData);
        Login_BTN_register.setOnClickListener(fillData);
//        Login_BTN_login.setOnClickListener(fillData);
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

        } else if ((view.getTag().toString().equals("register"))) {
            Intent intent = new Intent(this, RegisterActivity.class);
            this.startActivity(intent);
        } else if ((view.getTag().toString().equals("forgotPass"))) {

        }

    }

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