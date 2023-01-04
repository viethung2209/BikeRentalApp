package com.hunglee.bikerentalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hunglee.bikerentalapp.Adapters.MainAdapter;
import com.hunglee.bikerentalapp.Models.MainModel;
import com.hunglee.bikerentalapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends App {

    ActivityMainBinding binding;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        ArrayList<MainModel> list = new ArrayList<>();

        list = dbHelper.getBikes();
        MainAdapter adapter = new MainAdapter(list, this);
        binding.recyclerview.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerview.setLayoutManager(linearLayoutManager);

        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
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
                startActivity(new Intent(MainActivity.this, RentalActivity.class));
                break;
            case R.id.purchase:
//                startActivity(new Intent(MainActivity.this, PurchaseActivity.class));
                String s1 = sharedPreferences.getString("parkingName", null);
                if (s1 == null) {
                    Toast.makeText(MainActivity.this, "Choose parking first!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, s1, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.credit:
                startActivity(new Intent(MainActivity.this, CreditCardActivity.class));
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}