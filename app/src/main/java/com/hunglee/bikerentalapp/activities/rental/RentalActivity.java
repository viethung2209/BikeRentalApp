package com.hunglee.bikerentalapp.activities.rental;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.hunglee.bikerentalapp.App;
import com.hunglee.bikerentalapp.Models.orders.Order;
import com.hunglee.bikerentalapp.activities.main.MainActivity;
import com.hunglee.bikerentalapp.databinding.ActivityRentalBinding;
import com.hunglee.bikerentalapp.ultis.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RentalActivity extends App implements RentalContract.View {

    ActivityRentalBinding binding;
    SharedPreferences sharedPreferences;
    RentalPresenter rentalPresenter;
    Handler handler = new Handler();
    Boolean pause = true;
    Boolean mContinue = false;
    long timeCounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRentalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        registerReceiver(broadcastReceiver, new IntentFilter(Constant.ACTION_SEND_DATA));
        sharedPreferences = this.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        rentalPresenter = new RentalPresenter(mDb);

        long mTimeCounter = sharedPreferences.getLong("timeCounter", 0);
        if (mTimeCounter != 0) {
            timeCounter = mTimeCounter;
            pause = false;
            mContinue = true;

            binding.continueBtn.setVisibility(View.VISIBLE);
            binding.pauseBtn.setVisibility(View.GONE);
        }

        runnable.run();

        List<Order> listRenting = new ArrayList<>();
        List<Order> listPause = new ArrayList<>();
        Order mOrder = new Order();

        listRenting = rentalPresenter.getAllOrderByStatus(Constant.ON_RENTING);
        listPause = rentalPresenter.getAllOrderByStatus(Constant.ON_PAUSE);
        if (!listRenting.isEmpty())
            mOrder = listRenting.get(0);
        if (!listPause.isEmpty())
            mOrder = listPause.get(0);

        Order order = mOrder;

        initView(order);

        binding.pauseBtn.setOnClickListener(view -> {
            handlePauseBtn(order);
        });

        binding.continueBtn.setOnClickListener(view -> {
            handleContinueBtn(order);
        });

        binding.giveBackBtn.setOnClickListener(view -> {
            handleGiveBackBtn(order);
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


    @Override
    public void onBackPressed() {
        startActivity(new Intent(RentalActivity.this, MainActivity.class));
    }

    @Override
    public void initView(Order order) {
        binding.detailImage.setImageResource(order.image);
        binding.detailDescription.setText(order.description);
        binding.detailName.setText(order.name);
        binding.detailPrice.setText(order.cost);
    }

    @Override
    public void handlePauseBtn(Order order) {
        if (pause) {
            binding.pauseBtn.setVisibility(View.GONE);
            binding.continueBtn.setVisibility(View.VISIBLE);
            Intent pauseAction = new Intent();
            pauseAction.setAction(Constant.ACTION_PAUSE);
            sendBroadcast(pauseAction);

            order.status = Constant.ON_PAUSE;
            mDb.orderDao().updateOrder(order);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("timeCounter", timeCounter);
            editor.putBoolean("pauseStatus", false);
            editor.putBoolean("continueStatus", true);
            editor.apply();

            pause = false;
            mContinue = true;

        } else {
            binding.pauseBtn.setVisibility(View.VISIBLE);
            binding.continueBtn.setVisibility(View.GONE);

            pause = true;
            mContinue = false;
        }
    }

    @Override
    public void handleContinueBtn(Order order) {
        if (mContinue) {
            binding.pauseBtn.setVisibility(View.VISIBLE);
            binding.continueBtn.setVisibility(View.GONE);
            Intent resumeAction = new Intent();
            resumeAction.setAction(Constant.ACTION_START);
            sendBroadcast(resumeAction);

            order.status = Constant.ON_RENTING;
            mDb.orderDao().updateOrder(order);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("timeCounter", 0);
            editor.putBoolean("pauseStatus", true);
            editor.putBoolean("continueStatus", false);
            editor.apply();

            mContinue = false;
            pause = true;

        } else {
            binding.pauseBtn.setVisibility(View.GONE);
            binding.continueBtn.setVisibility(View.VISIBLE);

            mContinue = true;
            pause = false;

        }
    }

    @Override
    public void handleGiveBackBtn(Order order) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("timeCounter", 0);
        editor.putBoolean("pauseStatus", true);
        editor.putBoolean("continueStatus", false);
        editor.apply();

        Intent stopAction = new Intent();
        stopAction.setAction(Constant.ACTION_STOP);
        sendBroadcast(stopAction);

        serverPresenter.createGiveBackBikeTransaction(order, timeCounter);

        startActivity(new Intent(this, MainActivity.class));

    }
}