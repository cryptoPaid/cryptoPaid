package com.example.fintech.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fintech.R;

import java.util.List;

public class TransactionAdapter extends ArrayAdapter<Transaction> {


    public TransactionAdapter(@NonNull Context context, int resource, @NonNull List<Transaction> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.transaction_row_item, parent, false);
        }

        Transaction transaction = getItem(position);
        TextView transaction_row_item_LBL_amount = convertView.findViewById(R.id.transaction_row_item_LBL_amount);
        TextView transaction_row_item_LBL_title = convertView.findViewById(R.id.transaction_row_item_LBL_title);
        TextView transaction_row_item_LBL_toAddress = convertView.findViewById(R.id.transaction_row_item_LBL_toAddress);
        transaction_row_item_LBL_title.setText(transaction.getName());
        transaction_row_item_LBL_amount.setText(transaction.getAmount()+" JohnSta");
        transaction_row_item_LBL_toAddress.setText("sending to: " + transaction.getToAddress());

        return convertView;
    }



}
