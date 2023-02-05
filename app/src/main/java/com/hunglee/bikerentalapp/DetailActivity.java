package com.hunglee.bikerentalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.hunglee.bikerentalapp.databinding.ActivityDetailBinding;
import com.hunglee.bikerentalapp.service.MyService;
import com.hunglee.bikerentalapp.ultis.Constant;
import com.hunglee.bikerentalapp.ultis.roomdb.orders.Order;
import com.hunglee.bikerentalapp.ultis.roomdb.transaction.Transaction;

public class DetailActivity extends App {

    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int image = getIntent().getIntExtra("image", 0);
        int price = getIntent().getIntExtra("price", 0);
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("desc");
        String bikeCode = getIntent().getStringExtra("code");

        binding.detailImage.setImageResource(image);
        binding.detailPrice.setText(String.format("%d", price));
        binding.detailName.setText(name);
        binding.detailDescription.setText(description);


        binding.insertBtn.setOnClickListener(view -> {
            if (mDb.creditcardDao().findAllCardSync().isEmpty()) {
                Toast.makeText(DetailActivity.this, "Please create Creditcard to RENT bike!", Toast.LENGTH_LONG).show();
            } else if (mDb.orderDao().findOrderWithStatus(Constant.ON_PAUSE).isEmpty() && mDb.orderDao().findOrderWithStatus(Constant.ON_RENTING).isEmpty()) {
                Order order = new Order();
                order.cost = String.valueOf(price);
                order.description = description;
                order.name = name;
                order.status = Constant.ON_RENTING;
                order.bikeCode = bikeCode;
                mDb.orderDao().insertOrder(order);

                Transaction transaction = new Transaction();
                transaction.description = "Đặt cọc tiền thuê xe " + name;
                transaction.type = Constant.DEPOSIT;
                transaction.name = "Thuê xe " + name;
                transaction.value = String.valueOf(price);

                mDb.transactionDao().insertTransaction(transaction);

                //Start Service
                Intent intent = new Intent();
                intent.setAction(Constant.ACTION_START);
                sendBroadcast(intent);
                getApplicationContext().startService(new Intent(getApplicationContext(), MyService.class));
                startActivity(new Intent(DetailActivity.this, RentalActivity.class));

            } else {
                Toast.makeText(DetailActivity.this, "Only 1 bike can rent at the same time!", Toast.LENGTH_LONG).show();
            }

        });


    }
}