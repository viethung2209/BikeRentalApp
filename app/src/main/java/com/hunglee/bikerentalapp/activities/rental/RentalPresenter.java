package com.hunglee.bikerentalapp.activities.rental;

import com.hunglee.bikerentalapp.Models.orders.Order;
import com.hunglee.bikerentalapp.ultis.roomdb.AppDatabase;

import java.util.List;

public class RentalPresenter implements RentalContract.Presenter {
    private final AppDatabase mDb;

    public RentalPresenter(AppDatabase mDb) {
        this.mDb = mDb;
    }


    @Override
    public List<Order> getAllOrderByStatus(int status) {
        return mDb.orderDao().findOrderWithStatus(status);
    }
}
