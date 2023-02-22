package com.hunglee.bikerentalapp.activities.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hunglee.bikerentalapp.Adapters.MainAdapter;
import com.hunglee.bikerentalapp.App;
import com.hunglee.bikerentalapp.Models.bikes.Bike;
import com.hunglee.bikerentalapp.Models.orders.Order;
import com.hunglee.bikerentalapp.R;
import com.hunglee.bikerentalapp.activities.bikeparking.BikeParking;
import com.hunglee.bikerentalapp.activities.creditcard.CreditCardActivity;
import com.hunglee.bikerentalapp.activities.rental.RentalActivity;
import com.hunglee.bikerentalapp.databinding.ActivityMainBinding;
import com.hunglee.bikerentalapp.ultis.Constant;
import com.hunglee.bikerentalapp.ultis.service.MyService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends App implements MainContract.View {

    ActivityMainBinding binding;
    SharedPreferences sharedPreferences;
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        mainPresenter = new MainPresenter(mDb);

        List<Bike> list = new ArrayList<>();
        long s1 = sharedPreferences.getLong("parkingId", 0);
        list = mainPresenter.getListBikeByParkingId((int) s1);
        MainAdapter adapter = new MainAdapter(list, this);
        binding.recyclerview.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerview.setLayoutManager(linearLayoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        List<Order> orders = mainPresenter.getListOrderByStatus(Constant.ON_RENTING);
        List<Order> orders2 = mainPresenter.getListOrderByStatus(Constant.ON_PAUSE);

        switch (item.getItemId()) {
            case R.id.code:
                if (mainPresenter.getUserCreditCard().isEmpty()) {
                    errCreditCardMsg();
                    break;
                } else {
                    if (!(orders.isEmpty() && orders2.isEmpty())) {
                        errRentBikeMsg();
                    } else {
                        popupRentByCode();
                    }
                }
                break;
            case R.id.orders:
                if (orders.isEmpty() && orders2.isEmpty()) {
                    emptyOrderMsg();
                } else
                    startActivity(new Intent(MainActivity.this, RentalActivity.class));
                break;
            case R.id.credit:
                if (mainPresenter.getUserCreditCard().isEmpty()) {
                    errCreditCardMsg();

                    popupCreditCard();

                } else
                    startActivity(new Intent(MainActivity.this, CreditCardActivity.class));
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainActivity.this, BikeParking.class));
    }

    private void startService() {
        Intent intent = new Intent(MainActivity.this, RentalActivity.class);
        Intent broadCastIntent = new Intent();
        broadCastIntent.setAction(Constant.ACTION_START);
        sendBroadcast(broadCastIntent);
        getApplicationContext().startService(new Intent(getApplicationContext(), MyService.class));
        startActivity(intent);

    }

    @Override
    public void errCreditCardMsg() {
        Toast.makeText(MainActivity.this, "Please create Creditcard to RENT bike", Toast.LENGTH_LONG).show();
    }

    @Override
    public void errRentBikeMsg() {
        Toast.makeText(MainActivity.this, "On Renting Bike. Can't Rent More Bike", Toast.LENGTH_LONG).show();

    }

    @Override
    public void emptyOrderMsg() {
        Toast.makeText(MainActivity.this, "Empty Order Now", Toast.LENGTH_LONG).show();
    }

    @Override
    public void popupRentByCode() {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_rentbycode, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(binding.getRoot(), Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
        Button button = popupView.findViewById(R.id.rent_bike);
        button.setOnClickListener(view -> {

            final EditText bikeCode = popupView.findViewById(R.id.bikeCode);

            int code = Integer.parseInt(bikeCode.getText().toString());
            Bike bike = mDb.bikeDao().getBikeByCode(code);
            serverPresenter.createRentBikeTransaction(bike);
            startService();
            popupWindow.dismiss();

        });
    }

    @Override
    public void popupCreditCard() {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_creditcard, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(binding.getRoot(), Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
        Button button = popupView.findViewById(R.id.create_creditcard);
        button.setOnClickListener(view -> {

            final EditText name = popupView.findViewById(R.id.cardholderName);
            final EditText accountNumber = popupView.findViewById(R.id.cardNumber);
            final EditText issuingBank = popupView.findViewById(R.id.issuingBank);
            final EditText expDate = popupView.findViewById(R.id.expDate);
            final EditText code = popupView.findViewById(R.id.securityCode);

            serverPresenter.syncCreditCardWithInterBank(
                    name.getText().toString(),
                    accountNumber.getText().toString(),
                    issuingBank.getText().toString(),
                    expDate.getText().toString(),
                    code.getText().toString()
            );

            popupWindow.dismiss();
        });
    }
}