package com.example.fintech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.math.BigInteger;


public class MainActivity extends AppCompatActivity {


    private RelativeLayout main_LAY_Qtransaction;
    private RelativeLayout main_LAY_create;
    private RelativeLayout main_LAY_Settings;
    private String username;
    private String role;
    double balance;

    byte[] publicKey;
    byte[] privateKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        role = getIntent().getStringExtra("role");
        username = getIntent().getStringExtra("username");
        publicKey = getIntent().getByteArrayExtra("publicKey");
        privateKey = getIntent().getByteArrayExtra("privateKey");
        balance = Double.parseDouble(getIntent().getStringExtra("balance"));

        findViews();
        main_LAY_Qtransaction.setOnClickListener(changeActivity);
        main_LAY_create.setOnClickListener(changeActivity);
        main_LAY_Settings.setOnClickListener(changeActivity);
//        username = this.getIntent().getStringExtra("username");
//        role = this.getIntent().getStringExtra("role");
//        publicKey = this.getIntent().getStringExtra("publicKey").getBytes();
//        privateKey = this.getIntent().getStringExtra("privateKey").getBytes();
    }


    private View.OnClickListener changeActivity = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getTag().toString().equals("settings")) {
                settingActivity();
            } else if ((view.getTag().toString().equals("create"))) {
                Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
                intent.putExtra("role", role);
                intent.putExtra("username", username);
                intent.putExtra("publicKey", publicKey);
                intent.putExtra("privateKey", privateKey);
                intent.putExtra("balance", String.valueOf(100));
                startActivity(intent);
            } else if ((view.getTag().toString().equals("transactions"))) {

                Intent intent = new Intent(MainActivity.this, TranswaitActivity.class);
                intent.putExtra("role", role);
                intent.putExtra("username", username);
                intent.putExtra("publicKey", publicKey);
                intent.putExtra("privateKey", privateKey);
                intent.putExtra("balance", String.valueOf(100));
                startActivity(intent);
            }else if ((view.getTag().toString().equals("settings"))) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                intent.putExtra("username", "username");
                intent.putExtra("publicKey", publicKey.toString());
                intent.putExtra("privateKey", privateKey.toString());
                intent.putExtra("balance", String.valueOf(100));

                startActivity(intent);
            }
        }
    };

    private void settingActivity(){
        Intent intent = new Intent(this,SettingsActivity.class);
        this.startActivity(intent);

    }

    private void findViews() {
        main_LAY_Qtransaction = findViewById(R.id.main_LAY_Qtransaction);
        main_LAY_create = findViewById(R.id.main_LAY_create);
        main_LAY_Settings = findViewById(R.id.main_LAY_Settings);
    }
}