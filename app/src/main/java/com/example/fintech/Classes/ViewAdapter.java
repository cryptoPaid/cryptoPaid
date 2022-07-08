package com.example.fintech.Classes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fintech.R;

import java.util.ArrayList;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.TransactionViewHolder> {
    private ArrayList<Transaction> transactions;


    public ViewAdapter(ArrayList<Transaction> transactions){
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_row_item, parent, false);
        TransactionViewHolder transactionViewHolder = new TransactionViewHolder(view);
        return transactionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Transaction transaction = transactions.get(position);
        holder.transaction_row_item_LBL_amount.setText(transaction.getAmount() + " JonhSta");
        holder.transaction_row_item_LBL_toAddress.setText(transaction.getToAddress());
        holder.transaction_row_item_LBL_title.setText(transaction.getName());
        holder.transaction_row_item_LBL_time.setText(transaction.getTimestamp()+"");
        holder.position = position;
        holder.transaction = transaction;
    }



    @Override
    public int getItemCount() {
        Log.d("stas", "adapter size " + this.transactions.size());
        return this.transactions.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder{
        private TextView transaction_row_item_LBL_amount;
        private TextView transaction_row_item_LBL_toAddress;
        private TextView transaction_row_item_LBL_title;
        private TextView transaction_row_item_LBL_time;
        private View rootView;
        private int position;
        private Transaction transaction;

        public TransactionViewHolder(@NonNull View view){
            super(view);
            rootView = view;
            transaction_row_item_LBL_amount = view.findViewById(R.id.transaction_row_item_LBL_amount);
            transaction_row_item_LBL_toAddress = view.findViewById(R.id.transaction_row_item_LBL_toAddress);
            transaction_row_item_LBL_title = view.findViewById(R.id.transaction_row_item_LBL_title);
            transaction_row_item_LBL_time = view.findViewById(R.id.transaction_row_item_LBL_time);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("stas", "transactio nselected: " + position  + " transaction " + transaction.toString());

                }
            });
            view.findViewById(R.id.transaction_row_item_BTN_ming).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getTag().toString().equals("ming")){
//                        Intent intent = new Intent(this, )
                    }
                }
            });
        }
    }



}
