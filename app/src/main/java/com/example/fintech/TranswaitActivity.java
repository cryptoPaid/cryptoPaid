package com.example.fintech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.fintech.Classes.BlockChain;
import com.example.fintech.Classes.Transaction;

import java.util.ArrayList;

public class TranswaitActivity extends AppCompatActivity {

    private ArrayList<Transaction> pendingTransaction = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transwait);


    }
}