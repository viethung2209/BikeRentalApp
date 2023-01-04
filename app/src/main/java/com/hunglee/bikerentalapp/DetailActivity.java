package com.hunglee.bikerentalapp;

import android.os.Bundle;
import android.widget.Toast;


import com.hunglee.bikerentalapp.databinding.ActivityDetailBinding;
import com.hunglee.bikerentalapp.ultis.DBHelper;

public class DetailActivity extends App {

    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int image = getIntent().getIntExtra("image", 0);
        int price = Integer.parseInt(getIntent().getStringExtra("price"));
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("desc");

        binding.detailImage.setImageResource(image);
        binding.detailPrice.setText(String.format("%d", price));
        binding.detailName.setText(name);
        binding.detailDescription.setText(description);


        binding.insertBtn.setOnClickListener(view -> {
            boolean isInserted = dbHelper.insertOrder(
                    name,
                    Integer.parseInt(binding.quantity.getText().toString()),
                    image,
                    price,
                    description,
                    binding.cardholderName.getText().toString(),
                    binding.cardNumber.getText().toString(),
                    binding.issuingBank.getText().toString(),
                    binding.expDate.getText().toString(),
                    Integer.parseInt(binding.securityCode.getText().toString())
            );

            if (isInserted)
                Toast.makeText(DetailActivity.this, "Data Successed.", Toast.LENGTH_LONG).show();
            else Toast.makeText(DetailActivity.this, "Data Fault.", Toast.LENGTH_LONG).show();

        });


    }
}