package com.example.fintech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import java.math.BigInteger;


public class MainActivity extends AppCompatActivity {


    private RelativeLayout main_LAY_input4;
    private RelativeLayout main_LAY_Qtransaction;
    private RelativeLayout main_LAY_showAll;
    private RelativeLayout main_LAY_update;
    private RelativeLayout main_LAY_create;
    private RelativeLayout main_LAY_Settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        main_LAY_input4.setOnClickListener(changeActivity);
        main_LAY_Qtransaction.setOnClickListener(changeActivity);
        main_LAY_showAll.setOnClickListener(changeActivity);
        main_LAY_update.setOnClickListener(changeActivity);
        main_LAY_create.setOnClickListener(changeActivity);
        main_LAY_Settings.setOnClickListener(changeActivity);
    }


    private View.OnClickListener changeActivity = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getTag().toString().equals("settings")) {
                settingActivity();
            } else if ((view.getTag().toString().equals("create"))) {
            Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
            startActivity(intent);
            } else if ((view.getTag().toString().equals("update"))) {
//            Intent intent = new Intent(this, RegisterActivity.class);
//            this.startActivity(intent);
            }else if ((view.getTag().toString().equals("show"))) {
//            Intent intent = new Intent(this, RegisterActivity.class);
//            this.startActivity(intent);
            }else if ((view.getTag().toString().equals("waiting"))) {
            Intent intent = new Intent(MainActivity.this, TranswaitActivity.class);
            startActivity(intent);
            }else if ((view.getTag().toString().equals("input4"))) {
//            Intent intent = new Intent(this, RegisterActivity.class);
//            this.startActivity(intent);
            }else if ((view.getTag().toString().equals("input4"))) {
//            Intent intent = new Intent(this, RegisterActivity.class);
//            this.startActivity(intent);
            }
        }
    };

    private void settingActivity(){
        Intent intent = new Intent(this,SettingsActivity.class);
        this.startActivity(intent);

    }

    private void findViews() {
        main_LAY_input4 = findViewById(R.id.main_LAY_input4);
        main_LAY_Qtransaction = findViewById(R.id.main_LAY_Qtransaction);
        main_LAY_showAll = findViewById(R.id.main_LAY_showAll);
        main_LAY_update = findViewById(R.id.main_LAY_update);
        main_LAY_create = findViewById(R.id.main_LAY_create);
        main_LAY_Settings = findViewById(R.id.main_LAY_Settings);
    }
}