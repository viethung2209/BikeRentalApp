package com.hunglee.bikerentalapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.hunglee.bikerentalapp.App;
import com.hunglee.bikerentalapp.Models.creditcards.Creditcard;
import com.hunglee.bikerentalapp.Models.orders.Order;
import com.hunglee.bikerentalapp.Models.transaction.Transaction;
import com.hunglee.bikerentalapp.databinding.ActivityRentalBinding;
import com.hunglee.bikerentalapp.ultis.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RentalActivity extends App {

    ActivityRentalBinding binding;
    Handler handler = new Handler();
    Boolean pause = true;
    Boolean mContinue = false;
    long timeCounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRentalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        registerReceiver(broadcastReceiver, new IntentFilter(Constant.ACTION_SEND_DATA));

        runnable.run();

        List<Order> list = new ArrayList<>();

        Intent intent = new Intent();

        String code = intent.getStringExtra("code");
//        Order order = new Order();
//        if (!code.isEmpty()) {
//            Bike bike = mDb.bikeDao().getBikeByCode(code);
//            order.bikeCode = code;
//            order.cost = String.valueOf(bike.price);
//            order.description = bike.description;
//            order.name = "Thuê Xe";
//            mDb.orderDao().insertOrder(order);
//
//        } else {
//
        list = mDb.orderDao().findOrderWithStatus(Constant.ON_RENTING);
        Order order = list.get(0);
//        }

        binding.detailImage.setImageResource(order.image);
        binding.detailDescription.setText(order.description);
        binding.detailName.setText(order.name);
        binding.detailPrice.setText(order.cost);
        binding.pauseBtn.setOnClickListener(view -> {
            if (pause) {
                binding.pauseBtn.setVisibility(View.GONE);
                binding.continueBtn.setVisibility(View.VISIBLE);
                Intent pauseAction = new Intent();
                pauseAction.setAction(Constant.ACTION_PAUSE);
                sendBroadcast(pauseAction);

                order.status = Constant.ON_PAUSE;
                mDb.orderDao().updateOrder(order);
                pause = false;
                mContinue = true;

            } else {
                binding.pauseBtn.setVisibility(View.VISIBLE);
                binding.continueBtn.setVisibility(View.GONE);
                pause = true;
                mContinue = false;
            }

        });

        binding.continueBtn.setOnClickListener(view -> {
            if (mContinue) {
                binding.pauseBtn.setVisibility(View.VISIBLE);
                binding.continueBtn.setVisibility(View.GONE);
                Intent resumeAction = new Intent();
                resumeAction.setAction(Constant.ACTION_START);
                sendBroadcast(resumeAction);

                order.status = Constant.ON_RENTING;
                mDb.orderDao().updateOrder(order);
                mContinue = false;
                pause = true;

            } else {
                binding.pauseBtn.setVisibility(View.GONE);
                binding.continueBtn.setVisibility(View.VISIBLE);

                mContinue = true;
                pause = false;

            }

        });

        binding.giveBackBtn.setOnClickListener(view -> {
            Intent stopAction = new Intent();
            stopAction.setAction(Constant.ACTION_STOP);
            sendBroadcast(stopAction);

            Transaction transaction = new Transaction();
            transaction.type = Constant.WITHDRAW;
            transaction.name = "Hoàn trả tiền đặt cọc xe";
            transaction.value = order.cost;
            transaction.description = "Hoàn trả tiền đặt cọc xe " + order.name;

            mDb.transactionDao().insertTransaction(transaction);
            transaction.type = Constant.DEPOSIT;
            transaction.name = "Thanh toán tiền thuê xe";
            transaction.value = String.valueOf(calculateMoney(Integer.parseInt(order.bikeCode)));
            transaction.description = "Thanh toán tiền thuê xe " + order.name;
            mDb.transactionDao().insertTransaction(transaction);

            Creditcard creditcard = new Creditcard();
            creditcard = mDb.creditcardDao().findAllCardSync().get(0);
            creditcard.balance -= calculateMoney(Integer.parseInt(order.bikeCode));
            mDb.creditcardDao().updateCard(creditcard);

            order.status = Constant.ON_STOP;
            mDb.orderDao().updateOrder(order);

            startActivity(new Intent(this, MainActivity.class));
        });
    }


    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateUI();
            handler.postDelayed(this, 10);
        }
    };

    private void updateUI() {
        String time = getFormattedStopWatchTime(timeCounter);
        binding.timeCounter.setText(time);


    }

    private String getFormattedStopWatchTime(Long ms) {

        long millisecond = ms;
        long hours = TimeUnit.MILLISECONDS.toHours(millisecond);
        millisecond -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millisecond);
        millisecond -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millisecond);

        String string = "";
        if (hours < 10)
            string = string + "0" + hours + ":";
        else
            string = string + hours + ":";
        if (minutes < 10)
            string = string + "0" + minutes + ":";
        else string = string + minutes + ":";
        if (seconds < 10)
            string = string + "0" + seconds;
        else string = string + seconds;
        return string;

    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constant.ACTION_SEND_DATA)) {
                timeCounter = intent.getLongExtra("time", 0);
                Log.d("RA", String.valueOf(timeCounter));
            }
        }
    };

    private long calculateMoney(int code) {
        long value = 1;
        long millisecond = timeCounter;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millisecond);
        if (minutes <= 10)
            return value;
        else if (minutes <= 30) {
            value = 10000;
        } else {
            long t = (minutes - 30) / 15;
            value = 10000 + (t * 3000);
        }

        if (20 < code && code < 40)
            value = (long) (value * 1.5);
        else if (code > 40)
            value = value * 2;
        return value;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RentalActivity.this, MainActivity.class));
    }
}