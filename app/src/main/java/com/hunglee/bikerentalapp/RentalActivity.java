package com.hunglee.bikerentalapp;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.hunglee.bikerentalapp.Adapters.OrdersAdapter;
import com.hunglee.bikerentalapp.Models.OrdersModel;
import com.hunglee.bikerentalapp.databinding.ActivityRentalBinding;

import java.util.ArrayList;

public class RentalActivity extends App {

    ActivityRentalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRentalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<OrdersModel> list = new ArrayList<>();

        list = dbHelper.getOrders();

        OrdersAdapter adapter = new OrdersAdapter(list, this);
        binding.orderRV.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.orderRV.setLayoutManager(linearLayoutManager);
    }
}