package com.hunglee.bikerentalapp.activities.detail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.hunglee.bikerentalapp.App;
import com.hunglee.bikerentalapp.Models.bikes.Bike;
import com.hunglee.bikerentalapp.activities.main.MainActivity;
import com.hunglee.bikerentalapp.activities.rental.RentalActivity;
import com.hunglee.bikerentalapp.databinding.ActivityDetailBinding;
import com.hunglee.bikerentalapp.ultis.Constant;
import com.hunglee.bikerentalapp.ultis.service.MyService;

public class DetailActivity extends App implements DetailContract.View {

    ActivityDetailBinding binding;
    DetailPresenter detailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        detailPresenter = new DetailPresenter(mDb);

        int image = getIntent().getIntExtra("image", 0);
        int price = getIntent().getIntExtra("price", 0);
        int parkingId = getIntent().getIntExtra("parkingId", 0);
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("desc");
        String bikeCode = getIntent().getStringExtra("code");
        Bike mBike = new Bike();
        mBike.image = image;
        mBike.price = price;
        mBike.parkingId = parkingId;
        mBike.name = name;
        mBike.description = description;
        mBike.code = bikeCode;

        initViewDetail(mBike);


        binding.insertBtn.setOnClickListener(view -> onClickInsertButton(mBike));


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

    @Override
    public void initViewDetail(Bike bike) {
        binding.detailImage.setImageResource(bike.image);
        binding.detailPrice.setText(String.format("%d", bike.price));
        binding.detailName.setText(bike.name);
        binding.detailDescription.setText(bike.description);

    }

    @Override
    public void errRentBikeMsg() {
        Toast.makeText(DetailActivity.this, "Only 1 bike can rent at the same time!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void errCreditcardMsg() {
        Toast.makeText(DetailActivity.this, "Please create Creditcard to RENT bike!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClickInsertButton(Bike bike) {
        if (detailPresenter.getUserCreditcard().isEmpty()) {
            errCreditcardMsg();
        } else if (detailPresenter.getListOrderByStatus(Constant.ON_PAUSE).isEmpty() && detailPresenter.getListOrderByStatus(Constant.ON_RENTING).isEmpty()) {
            serverPresenter.createRentBikeTransaction(bike);
            startService();

        } else {
            errRentBikeMsg();
        }
    }
}