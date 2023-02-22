package com.hunglee.bikerentalapp.activities.bikeparking;

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
import com.hunglee.bikerentalapp.App;
import com.hunglee.bikerentalapp.Models.creditcards.Creditcard;
import com.hunglee.bikerentalapp.R;
import com.hunglee.bikerentalapp.activities.creditcard.CreditCardActivity;
import com.hunglee.bikerentalapp.databinding.ActivityBikeParkingBinding;

import java.util.List;

public class BikeParking extends App implements ParkingContract.View {
    ActivityBikeParkingBinding binding;
    SharedPreferences sharedPreferences;
    ParkingPresenter parkingPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBikeParkingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        parkingPresenter = new ParkingPresenter(mDb);
        List<com.hunglee.bikerentalapp.Models.bikeparkings.BikeParking> list = parkingPresenter.getListBikeFromDb();

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
            case R.id.code:
            case R.id.orders:
                ErrParkingMsg();
                break;

            case R.id.credit:
                ErrCreditcardMsg();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public void ErrParkingMsg() {
        Toast.makeText(BikeParking.this, "Choose parking first!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void ErrCreditcardMsg() {
        List<Creditcard> list = parkingPresenter.getListCreditcard();
        if (list.isEmpty()) {
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
        } else {
            startActivity(new Intent(BikeParking.this, CreditCardActivity.class));
        }
    }

}