package com.hunglee.bikerentalapp.activities.main;

import com.hunglee.bikerentalapp.Models.bikes.Bike;
import com.hunglee.bikerentalapp.Models.creditcards.Creditcard;
import com.hunglee.bikerentalapp.Models.orders.Order;
import com.hunglee.bikerentalapp.ultis.Constant;
import com.hunglee.bikerentalapp.ultis.roomdb.AppDatabase;

import java.util.List;

public class MainPresenter implements MainContract.Presenter{
    private final AppDatabase mDb;

    public MainPresenter(AppDatabase mDb) {
        this.mDb = mDb;
    }


    @Override
    public List<Bike> getListBikeByParkingId(int id) {
        return mDb.bikeDao().getBikeByParkingId(id);
    }

    @Override
    public List<Order> getListOrderByStatus(int status) {
        return mDb.orderDao().findOrderWithStatus(status);
    }

    @Override
    public List<Creditcard> getUserCreditCard() {
        return mDb.creditcardDao().findAllCardSync();
    }
}
