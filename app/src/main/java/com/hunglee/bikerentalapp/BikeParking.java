package com.hunglee.bikerentalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.hunglee.bikerentalapp.Adapters.ParkingAdapter;
import com.hunglee.bikerentalapp.databinding.ActivityBikeParkingBinding;
import com.hunglee.bikerentalapp.service.MyService;
import com.hunglee.bikerentalapp.ultis.Constant;
import com.hunglee.bikerentalapp.ultis.roomdb.creditcards.Creditcard;
import com.hunglee.bikerentalapp.ultis.roomdb.orders.Order;

import java.util.ArrayList;
import java.util.List;

public class BikeParking extends App {
    ActivityBikeParkingBinding binding;
    SharedPreferences sharedPreferences;
    public MyService myService = new MyService();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBikeParkingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<com.hunglee.bikerentalapp.ultis.roomdb.bikeparkings.BikeParking> list = new ArrayList<>();
        list = mDb.bikeParkingDao().findAllBikeParkingSync();
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
                String s1 = sharedPreferences.getString("parkingName", null);
                if (s1 == null) {
                    Toast.makeText(BikeParking.this, "Choose parking first!", Toast.LENGTH_LONG).show();
                    break;
                } else {


                    List<Order> orders = mDb.orderDao().findOrderWithStatus(Constant.ON_RENTING);
                    if (orders.isEmpty()) {
                        Toast.makeText(BikeParking.this, "Empty Order Now", Toast.LENGTH_LONG).show();

                    } else
                        startActivity(new Intent(BikeParking.this, RentalActivity.class));
                }
                break;
            case R.id.credit:
                if (mDb.creditcardDao().findAllCardSync().isEmpty()) {
                    Toast.makeText(BikeParking.this, "Please create Creditcard to RENT bike", Toast.LENGTH_LONG).show();


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

                        final EditText name = (EditText) popupView.findViewById(R.id.cardholderName);
                        final EditText accountNumber = (EditText) popupView.findViewById(R.id.cardNumber);
                        final EditText issuingBank = (EditText) popupView.findViewById(R.id.issuingBank);
                        final EditText expDate = (EditText) popupView.findViewById(R.id.expDate);
                        final EditText code = (EditText) popupView.findViewById(R.id.securityCode);
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
                    startActivity(new Intent(BikeParking.this, CreditCardActivity.class));
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}