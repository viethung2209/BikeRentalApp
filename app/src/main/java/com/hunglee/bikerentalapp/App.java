package com.hunglee.bikerentalapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hunglee.bikerentalapp.service.MyService;
import com.hunglee.bikerentalapp.ultis.roomdb.AppDatabase;
import com.hunglee.bikerentalapp.ultis.roomdb.bikeparkings.BikeParking;
import com.hunglee.bikerentalapp.ultis.roomdb.bikes.Bike;

public class App extends AppCompatActivity {

    public AppDatabase mDb;
    private IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        mDb = AppDatabase.getInMemoryDatabase(getApplicationContext());
        if (mDb.bikeDao().findAllBikeSync().isEmpty()) {
            initBikeDatabase();
        }
        if (mDb.bikeParkingDao().findAllBikeParkingSync().isEmpty()) {
            initParkingBikeDatabase();
        }



    }


    private void initBikeDatabase() {
        Bike bike = new Bike();
        bike.name = "Xe thường 1";
        bike.price = 400000;
        bike.image = R.drawable.xe_thuong_1;
        bike.description = "Xe đạp thường, 1 yên xe và 1 chỗ ngồi phía sau";
        bike.code = "10";
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe thường 1";
        bike.price = 400000;
        bike.image = R.drawable.xe_thuong_1;
        bike.description = "Xe đạp thường, 1 yên xe và 1 chỗ ngồi phía sau";
        bike.code = "11";
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe thường 1";
        bike.price = 400000;
        bike.image = R.drawable.xe_thuong_1;
        bike.description = "Xe đạp thường, 1 yên xe và 1 chỗ ngồi phía sau";
        bike.code = "12";
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe thường 1";
        bike.price = 400000;
        bike.image = R.drawable.xe_thuong_1;
        bike.description = "Xe đạp thường, 1 yên xe và 1 chỗ ngồi phía sau";
        bike.code = "13";
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe thường 1";
        bike.price = 400000;
        bike.image = R.drawable.xe_thuong_1;
        bike.description = "Xe đạp thường, 1 yên xe và 1 chỗ ngồi phía sau";
        bike.code = "14";
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe thường 1";
        bike.price = 400000;
        bike.image = R.drawable.xe_thuong_1;
        bike.description = "Xe đạp thường, 1 yên xe và 1 chỗ ngồi phía sau";
        bike.code = "15";
        mDb.bikeDao().inserBike(bike);


        bike.name = "Xe thường 2";
        bike.price = 550000;
        bike.image = R.drawable.xe_thuong_2;
        bike.description = "Xe đạp thường 2: Là xe đạp đôi, có 2 yên, 2 bàn đạp và 1 ghế ngồi sau";
        bike.code = "21";
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe thường 2";
        bike.price = 550000;
        bike.image = R.drawable.xe_thuong_2;
        bike.description = "Xe đạp thường 2: Là xe đạp đôi, có 2 yên, 2 bàn đạp và 1 ghế ngồi sau";
        bike.code = "22";
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe thường 2";
        bike.price = 550000;
        bike.image = R.drawable.xe_thuong_2;
        bike.description = "Xe đạp thường 2: Là xe đạp đôi, có 2 yên, 2 bàn đạp và 1 ghế ngồi sau";
        bike.code = "23";
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe thường 2";
        bike.price = 550000;
        bike.image = R.drawable.xe_thuong_2;
        bike.description = "Xe đạp thường 2: Là xe đạp đôi, có 2 yên, 2 bàn đạp và 1 ghế ngồi sau";
        bike.code = "24";
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe điện 1";
        bike.price = 700000;
        bike.image = R.drawable.xe_dien_1;
        bike.description = "Xe đạp điện loại 1: giống với xe đạp thường loại 1 nhưng có motor điện giúp xe chạy nhanh hơn";
        bike.code = "31";
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe điện 1";
        bike.price = 700000;
        bike.image = R.drawable.xe_dien_1;
        bike.description = "Xe đạp điện loại 1: giống với xe đạp thường loại 1 nhưng có motor điện giúp xe chạy nhanh hơn";
        bike.code = "32";
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe điện 1";
        bike.price = 700000;
        bike.image = R.drawable.xe_dien_1;
        bike.description = "Xe đạp điện loại 1: giống với xe đạp thường loại 1 nhưng có motor điện giúp xe chạy nhanh hơn";
        bike.code = "33";
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe điện 1";
        bike.price = 700000;
        bike.image = R.drawable.xe_dien_1;
        bike.description = "Xe đạp điện loại 1: giống với xe đạp thường loại 1 nhưng có motor điện giúp xe chạy nhanh hơn";
        bike.code = "34";
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe điện 1";
        bike.price = 700000;
        bike.image = R.drawable.xe_dien_1;
        bike.description = "Xe đạp điện loại 1: giống với xe đạp thường loại 1 nhưng có motor điện giúp xe chạy nhanh hơn";
        bike.code = "35";
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe điện 2";
        bike.price = 1000000;
        bike.image = R.drawable.xe_dien_2;
        bike.description = "Xe đạp điện loại 2: giống với xe đạp thường loại 2 nhưng có motor điện giúp xe chạy nhanh hơn";
        bike.code = "41";
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe điện 2";
        bike.price = 1000000;
        bike.image = R.drawable.xe_dien_2;
        bike.description = "Xe đạp điện loại 2: giống với xe đạp thường loại 2 nhưng có motor điện giúp xe chạy nhanh hơn";
        bike.code = "42";
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe điện 2";
        bike.price = 1000000;
        bike.image = R.drawable.xe_dien_2;
        bike.description = "Xe đạp điện loại 2: giống với xe đạp thường loại 2 nhưng có motor điện giúp xe chạy nhanh hơn";
        bike.code = "43";
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe điện 2";
        bike.price = 1000000;
        bike.image = R.drawable.xe_dien_2;
        bike.description = "Xe đạp điện loại 2: giống với xe đạp thường loại 2 nhưng có motor điện giúp xe chạy nhanh hơn";
        bike.code = "44";
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe điện 2";
        bike.price = 1000000;
        bike.image = R.drawable.xe_dien_2;
        bike.description = "Xe đạp điện loại 2: giống với xe đạp thường loại 2 nhưng có motor điện giúp xe chạy nhanh hơn";
        bike.code = "45";
        mDb.bikeDao().inserBike(bike);


    }

    private void initParkingBikeDatabase() {

        BikeParking bikeParking = new BikeParking();
        bikeParking.name = "Bãi xe 1";
        bikeParking.bikeNumber = 50;
        bikeParking.image = R.drawable.bai_xe_1;
        bikeParking.description = "Bãi gửi xe số 1";
        mDb.bikeParkingDao().insertParking(bikeParking);

        bikeParking.name = "Bãi xe 2";
        bikeParking.bikeNumber = 50;
        bikeParking.image = R.drawable.bai_xe_2;
        bikeParking.description = "Bãi gửi xe số 2";
        mDb.bikeParkingDao().insertParking(bikeParking);

        bikeParking.name = "Bãi xe 3";
        bikeParking.bikeNumber = 50;
        bikeParking.image = R.drawable.bai_xe_3;
        bikeParking.description = "Bãi gửi xe số 3";
        mDb.bikeParkingDao().insertParking(bikeParking);

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