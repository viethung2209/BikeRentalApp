package com.hunglee.bikerentalapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hunglee.bikerentalapp.databinding.ActivityCreditCardBinding;

public class CreditCardActivity extends AppCompatActivity {

    ActivityCreditCardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreditCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}