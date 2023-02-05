package com.hunglee.bikerentalapp.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.hunglee.bikerentalapp.ultis.Constant;

public class MyService extends Service {
    private String LOG_TAG = "MyService";
    private Receiver receiver = new Receiver();
    private Boolean isRunning = true;
    private IntentFilter intentFilter = new IntentFilter();
    private Handler handler = new Handler();
    private long timeCounter;

    @Override
    public void onCreate() {
        super.onCreate();
        intentFilter.addAction(Constant.ACTION_STOP);
        intentFilter.addAction(Constant.ACTION_PAUSE);
        intentFilter.addAction(Constant.ACTION_START);
        intentFilter.addAction(Constant.ACTION_RESUME);
        intentFilter.addAction(Constant.ACTION_SEND_DATA);
        timeCounter = 0;
        registerReceiver(receiver, intentFilter);
//        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOG_TAG, "onStartCommand MyService");
        //Tạo một Thread sau 2 giây gửi data đến Broadcast cùng với intent tương ứng.
//        runnable.run();
        runnable.run();
        //flag này có tác dụng khi android bị kill hoặc bộ nhớ thấp, hệ thống sẽ start lại và gửi kết quả lần nữa.
        return START_REDELIVER_INTENT;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if (isRunning) {
                timeCounter += 1000;
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(Constant.ACTION_SEND_DATA);
                broadcastIntent.putExtra("time", timeCounter);
                sendBroadcast(broadcastIntent);
                Log.d(LOG_TAG, "Time Counter: " + timeCounter);
            }
            handler.postDelayed(this, 1000);

        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG, "In onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        stopSelf();
        super.onDestroy();
        Log.i(LOG_TAG, "In onDestroy");
    }

    class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constant.ACTION_START:
                    Log.d(LOG_TAG, "ACTION START");
                    isRunning = true;
                    break;
                case Constant.ACTION_PAUSE:
                    isRunning = false;
                    Log.d(LOG_TAG, "ACTION PAUSE");

                    break;
                case Constant.ACTION_STOP:
                    isRunning = false;
                    Log.d(LOG_TAG, "ACTION STOP");
                    stopSelf();
                    break;
                case Constant.ACTION_RESUME:
                    Log.d(LOG_TAG, "ACTION RESUME");
                    isRunning = true;
                    break;
                default:
                    break;
            }
        }
    }
}