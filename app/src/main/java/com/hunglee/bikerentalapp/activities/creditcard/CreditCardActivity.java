package com.hunglee.bikerentalapp.activities.creditcard;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.hunglee.bikerentalapp.Adapters.TransactionAdapter;
import com.hunglee.bikerentalapp.App;
import com.hunglee.bikerentalapp.Models.creditcards.Creditcard;
import com.hunglee.bikerentalapp.Models.transaction.Transaction;
import com.hunglee.bikerentalapp.activities.main.MainActivity;
import com.hunglee.bikerentalapp.databinding.ActivityCreditCardBinding;

import java.util.ArrayList;
import java.util.List;

public class CreditCardActivity extends App implements CreditcardContract.View {

    ActivityCreditCardBinding binding;
    CreditcardPresenter creditcardPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreditCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        creditcardPresenter = new CreditcardPresenter(mDb);

        Creditcard creditcard = creditcardPresenter.getOwnerCreditCard();
        List<Transaction> transactions = new ArrayList<>();
        transactions = creditcardPresenter.getListTransaction();

        TransactionAdapter transactionAdapter = new TransactionAdapter(transactions, this);
        binding.partial.transactionRV.setAdapter(transactionAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.partial.transactionRV.setLayoutManager(linearLayoutManager);

        initUserInforCreditcard(creditcard);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CreditCardActivity.this, MainActivity.class));
    }

    @Override
    public void initUserInforCreditcard(Creditcard creditcard) {
        binding.partial.accountNumber.setText(creditcard.accountNumber);
        binding.partial.balance.setText(String.valueOf(creditcard.balance));
        binding.partial.expDate.setText(creditcard.expirationDate);
        binding.partial.nameHolder.setText(creditcard.name);
    }
}