package com.hunglee.bikerentalapp;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.hunglee.bikerentalapp.Adapters.TransactionAdapter;
import com.hunglee.bikerentalapp.databinding.ActivityCreditCardBinding;
import com.hunglee.bikerentalapp.ultis.roomdb.creditcards.Creditcard;
import com.hunglee.bikerentalapp.ultis.roomdb.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

public class CreditCardActivity extends App {

    ActivityCreditCardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreditCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Creditcard creditcard = mDb.creditcardDao().findAllCardSync().get(0);
        List<Transaction> transactions = new ArrayList<>();
        transactions = mDb.transactionDao().findAllTransaction();
        Log.d("Size: ", "" + transactions.size());
        TransactionAdapter transactionAdapter = new TransactionAdapter(transactions, this);
        binding.partial.transactionRV.setAdapter(transactionAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.partial.transactionRV.setLayoutManager(linearLayoutManager);

        binding.partial.accountNumber.setText(creditcard.accountNumber);
        binding.partial.balance.setText(String.valueOf(creditcard.balance));
        binding.partial.expDate.setText(creditcard.expirationDate);
        binding.partial.nameHolder.setText(creditcard.name);

    }
}