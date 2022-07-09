package com.example.fintech;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.fintech.Classes.DarkModePrefManager;


public class SettingsActivity extends AppCompatActivity {

    private Switch Settings_SWC_darkModeSwitch;
    private TextView settings_LBL_Language;
    private TextView settings_LBL_logout;
    private TextView settings_LBL_changePass;
    private TextView settings_LBL_editProfile;
    private ImageView settings_IMG_edit;
    byte[] publicKey;
    byte[] privateKey;
    String username;
    String balance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(new DarkModePrefManager(this).isNightMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        setContentView(R.layout.activity_settings);
        username = getIntent().getStringExtra("username");
        publicKey = getIntent().getByteArrayExtra("publicKey");
        privateKey = getIntent().getByteArrayExtra("privateKey");
        balance = getIntent().getStringExtra("balance");
        findViews();
        //function for enabling dark mode
        setDarkModeSwitch();

        settings_LBL_Language.setOnClickListener(clicked);
        settings_LBL_logout.setOnClickListener(clicked);
        settings_LBL_changePass.setOnClickListener(clicked);
        settings_LBL_editProfile.setOnClickListener(clicked);
        settings_IMG_edit.setOnClickListener(clicked);
    }
    private View.OnClickListener clicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getTag().toString().equals("logout")) {
                finish();
            } else if (view.getTag().toString().equals("language")) {
                showOwnersDialog();
            }else if (view.getTag().toString().equals("changePass")) {
                showOwnersDialog();
            }else if (view.getTag().toString().equals("editProfile")) {
                Intent intent = new Intent(SettingsActivity.this,PersonalActivity.class);
                intent.putExtra("username", "username");
                intent.putExtra("publicKey", publicKey);
                intent.putExtra("privateKey", privateKey);
                intent.putExtra("balance", balance);
                startActivity(intent);
            }
        }

    };

    private void showOwnersDialog() {
        final AlertDialog.Builder ownerDialog = new AlertDialog.Builder(this);
        ownerDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        LinearLayout lila1= new LinearLayout(this);
        lila1.setOrientation(LinearLayout.VERTICAL);
        final TextView input1 = new TextView(this);
        final TextView input2 = new TextView(this);
        input1.setText("    stas.krot1996@gmail.com");
        input2.setText("    yonatani94@gmail.com");
        lila1.addView(input1);
        lila1.addView(input2);
        ownerDialog.setView(lila1);
        ownerDialog.setTitle("Owners Of The App");
        ownerDialog.show();
    }



    private void setDarkModeSwitch(){
        Settings_SWC_darkModeSwitch = findViewById(R.id.Settings_SWC_darkModeSwitch);
        Settings_SWC_darkModeSwitch.setChecked(new DarkModePrefManager(this).isNightMode());
        Settings_SWC_darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DarkModePrefManager darkModePrefManager = new DarkModePrefManager(SettingsActivity.this);
                darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode());
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
            }
        });
    }




    private void findViews() {
        Settings_SWC_darkModeSwitch = findViewById(R.id.Settings_SWC_darkModeSwitch);
        settings_LBL_Language = findViewById(R.id.settings_LBL_Language);
        settings_LBL_logout = findViewById(R.id.settings_LBL_logout);
        settings_LBL_changePass = findViewById(R.id.settings_LBL_changePass);
        settings_LBL_editProfile = findViewById(R.id.settings_LBL_editProfile);
        settings_IMG_edit = findViewById(R.id.settings_IMG_edit);
    }
}