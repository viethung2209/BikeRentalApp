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
import com.hunglee.bikerentalapp.Models.creditcards.Creditcard;
import com.hunglee.bikerentalapp.Models.orders.Order;
import com.hunglee.bikerentalapp.R;
import com.hunglee.bikerentalapp.databinding.ActivityMainBinding;
import com.hunglee.bikerentalapp.ultis.Constant;

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


        List<Bike> list = new ArrayList<>();

        list = mDb.bikeDao().findAllBikeSync();
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
            case R.id.code:
                String s1 = sharedPreferences.getString("parkingName", null);
                if (s1 == null) {
                    Toast.makeText(MainActivity.this, "Choose parking first!", Toast.LENGTH_LONG).show();
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
                            Intent broadCastIntent = new Intent();
                            broadCastIntent.setAction(Constant.ACTION_START);
                            sendBroadcast(broadCastIntent);
                            final EditText bikeCode = popupView.findViewById(R.id.bikeCode);
                            Intent intent = new Intent(MainActivity.this, RentalActivity.class);
                            intent.putExtra("code", bikeCode.toString());
                            popupWindow.dismiss();

                            startActivity(intent);
                        });
                    }
                }
                break;
            case R.id.orders:
                List<Order> orders = mDb.orderDao().findOrderWithStatus(Constant.ON_RENTING);
                if (orders.isEmpty()) {
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
                        Creditcard creditcard = new Creditcard();
                        creditcard.name = name.getText().toString();
                        creditcard.accountNumber = accountNumber.getText().toString();
                        creditcard.inssingBank = issuingBank.getText().toString();
                        creditcard.expirationDate = expDate.getText().toString();
                        creditcard.securityCode = code.getText().toString();

                        mDb.creditcardDao().insertCard(creditcard);
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
}