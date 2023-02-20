package com.hunglee.bikerentalapp.activities;

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
import com.hunglee.bikerentalapp.databinding.ActivityMainBinding;
import com.hunglee.bikerentalapp.ultis.Constant;
import com.hunglee.bikerentalapp.ultis.service.MyService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends App {

    ActivityMainBinding binding;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);


        List<Bike> list = new ArrayList<>();
        long s1 = sharedPreferences.getLong("parkingId", 0);
        list = mDb.bikeDao().getBikeByParkingId((int) s1);
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
        switch (item.getItemId()) {
            case R.id.code:
                if (mDb.creditcardDao().findAllCardSync().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please create Creditcard to RENT bike", Toast.LENGTH_LONG).show();
                    break;
                } else {
                    List<Order> orders = mDb.orderDao().findOrderWithStatus(Constant.ON_RENTING);
                    if (!orders.isEmpty()) {
                        Toast.makeText(MainActivity.this, "On Renting Bike. Can't Rent More Bike", Toast.LENGTH_LONG).show();

                    } else {

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
//                            order.bikeCode = bike.code;
//                            order.cost = String.valueOf(bike.price);
//                            order.description = bike.description;
//                            order.name = "Thuê Xe " + bike.name;
//                            order.image = bike.image;
//                            order.status = Constant.ON_RENTING;
//                            mDb.orderDao().insertOrder(order);
//
//                            Transaction transaction = new Transaction();
//                            transaction.description = "Đặt cọc tiền thuê xe " + bike.name;
//                            transaction.type = Constant.DEPOSIT;
//                            transaction.name = "Thuê xe " + bike.name;
//                            transaction.value = String.valueOf(bike.price);
//
//                            mDb.transactionDao().insertTransaction(transaction);
                            serverPresenter.createRentBikeTransaction(bike);
                            startService();
                            popupWindow.dismiss();

                        });
                    }
                }
                break;
            case R.id.orders:
                List<Order> orders = mDb.orderDao().findOrderWithStatus(Constant.ON_RENTING);
                List<Order> orders2 = mDb.orderDao().findOrderWithStatus(Constant.ON_PAUSE);
                if (orders.isEmpty() && orders2.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Empty Order Now", Toast.LENGTH_LONG).show();

                } else
                    startActivity(new Intent(MainActivity.this, RentalActivity.class));
                break;
            case R.id.credit:
                if (mDb.creditcardDao().findAllCardSync().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please create Creditcard to RENT bike", Toast.LENGTH_LONG).show();


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
}