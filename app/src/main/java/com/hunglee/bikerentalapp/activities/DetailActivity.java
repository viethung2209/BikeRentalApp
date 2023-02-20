package com.hunglee.bikerentalapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.hunglee.bikerentalapp.App;
import com.hunglee.bikerentalapp.Models.bikes.Bike;
import com.hunglee.bikerentalapp.databinding.ActivityDetailBinding;
import com.hunglee.bikerentalapp.ultis.Constant;
import com.hunglee.bikerentalapp.ultis.service.MyService;

public class DetailActivity extends App {

    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int image = getIntent().getIntExtra("image", 0);
        int price = getIntent().getIntExtra("price", 0);
        int parkingId = getIntent().getIntExtra("parkingId", 0);
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
                Bike bike = new Bike();
                bike.name = name;
                bike.parkingId = parkingId;
                bike.price = price;
                bike.code = bikeCode;
                bike.description = description;
                bike.image = image;

                serverPresenter.createRentBikeTransaction(bike);
//
//                Order order = new Order();
//                order.cost = String.valueOf(price);
//                order.description = description;
//                order.name = "Thuê xe" + name;
//                order.status = Constant.ON_RENTING;
//                order.bikeCode = bikeCode;
//                order.image = image;
//                mDb.orderDao().insertOrder(order);
//
//                Transaction transaction = new Transaction();
//                transaction.description = "Đặt cọc tiền thuê xe " + name;
//                transaction.type = Constant.DEPOSIT;
//                transaction.name = "Thuê xe " + name;
//                transaction.value = String.valueOf(price);
//
//                mDb.transactionDao().insertTransaction(transaction);

                //Start Service
                startService();

            } else {
                Toast.makeText(DetailActivity.this, "Only 1 bike can rent at the same time!", Toast.LENGTH_LONG).show();
            }

        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DetailActivity.this, MainActivity.class));
    }

    private void startService() {
        Intent intent = new Intent();
        intent.setAction(Constant.ACTION_START);
        sendBroadcast(intent);
        getApplicationContext().startService(new Intent(getApplicationContext(), MyService.class));
        startActivity(new Intent(DetailActivity.this, RentalActivity.class));
    }
}