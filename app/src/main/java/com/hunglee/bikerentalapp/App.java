package com.hunglee.bikerentalapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hunglee.bikerentalapp.ultis.roomdb.AppDatabase;
import com.hunglee.bikerentalapp.ultis.server.ServerPresenter;

public class App extends AppCompatActivity {

    public AppDatabase mDb;
    public ServerPresenter serverPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        mDb = AppDatabase.getInMemoryDatabase(getApplicationContext());
        serverPresenter = new ServerPresenter(mDb);
        if (mDb.bikeDao().findAllBikeSync().isEmpty() || mDb.bikeParkingDao().findAllBikeParkingSync().isEmpty()) {
            serverPresenter.QueryDbFromServer();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}