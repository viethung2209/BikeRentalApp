package com.hunglee.bikerentalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hunglee.bikerentalapp.Adapters.ParkingAdapter;
import com.hunglee.bikerentalapp.Models.BikeParkingModel;
import com.hunglee.bikerentalapp.databinding.ActivityBikeParkingBinding;

import java.util.ArrayList;

public class BikeParking extends App {
    ActivityBikeParkingBinding binding;
    SharedPreferences sharedPreferences;

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

        sharedPreferences = this.getSharedPreferences("MySharedPref", MODE_PRIVATE);

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
            case R.id.purchase:
                String s1 = sharedPreferences.getString("parkingName", null);
                if (s1 == null) {
                    Toast.makeText(BikeParking.this, "Choose parking first!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(BikeParking.this, s1, Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}