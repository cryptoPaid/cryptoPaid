package com.example.fintech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fintech.Classes.DarkModePrefManager;
import com.google.android.material.button.MaterialButton;

public class PersonalActivity extends AppCompatActivity {

    private TextView personal_EDT_email;
    private TextView personal_EDT_lastName;
    private TextView personal_EDT_name;
    private TextView personal_EDT_username;
    private TextView personal_LBL_editPhoto;
    private ImageView personal_IMG_photo;
    private ImageView personal_IMG_back;
    private RelativeLayout personal_LAY_upper;
    private Button settings_BTN_saved;
    private String username;
    String balance;
    byte[] publicKey;
    byte[] privateKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        username = getIntent().getStringExtra("username");
//        publicKey = getIntent().getByteArrayExtra("publicKey");
        privateKey = getIntent().getByteArrayExtra("privateKey");
//        balance = getIntent().getStringExtra("balance");

        findViews();


        personal_EDT_username.setText("private key");
        personal_EDT_name.setText("public key");
        personal_EDT_lastName.setText("Balance Is: " + 100);
        personal_EDT_email.setText("stas.krot1996@gmail.com");



//        personal_EDT_email.setOnClickListener(input);
//        personal_EDT_lastName.setOnClickListener(input);
//        personal_EDT_name.setOnClickListener(input);
//        personal_EDT_username.setOnClickListener(input);
//        personal_LBL_editPhoto.setOnClickListener(input);
//        personal_IMG_photo.setOnClickListener(input);
//        personal_IMG_back.setOnClickListener(input);
//        personal_LAY_upper.setOnClickListener(input);
//        settings_BTN_saved.setOnClickListener(input);

    }

    private View.OnClickListener input = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getTag().toString().equals("back")) {
                finish();
            }
        }
    };


    private void findViews() {
        personal_EDT_email = findViewById(R.id.personal_EDT_email);
        personal_EDT_lastName = findViewById(R.id.personal_EDT_lastName);
        personal_EDT_name = findViewById(R.id.personal_EDT_name);
        personal_EDT_username = findViewById(R.id.personal_EDT_username);
        personal_LBL_editPhoto = findViewById(R.id.personal_LBL_editPhoto);
        personal_IMG_photo = findViewById(R.id.personal_IMG_photo);
        personal_IMG_back = findViewById(R.id.personal_IMG_back);
        personal_LAY_upper = findViewById(R.id.personal_LAY_upper);
        settings_BTN_saved = findViewById(R.id.settings_BTN_saved);
    }
}