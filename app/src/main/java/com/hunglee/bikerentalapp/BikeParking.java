package com.hunglee.bikerentalapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hunglee.bikerentalapp.Adapters.ParkingAdapter;
import com.hunglee.bikerentalapp.Models.BikeParkingModel;
import com.hunglee.bikerentalapp.databinding.ActivityBikeParkingBinding;

import java.util.ArrayList;

public class BikeParking extends App {
    ActivityBikeParkingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBikeParkingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<BikeParkingModel> list = new ArrayList<>();
        list = dbHelper.getPakings();
        Log.d("list", list.get(0).getBikeNumber() + "");
        ParkingAdapter adapter = new ParkingAdapter(list, this);
        binding.parkingRV.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.parkingRV.setLayoutManager(linearLayoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.orders:
                startActivity(new Intent(BikeParking.this, RentalActivity.class));
                break;
            case R.id.credit:
                startActivity(new Intent(BikeParking.this, CreditCardActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}