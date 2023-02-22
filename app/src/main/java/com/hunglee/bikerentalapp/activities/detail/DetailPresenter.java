package com.hunglee.bikerentalapp.activities.detail;

import com.hunglee.bikerentalapp.Models.creditcards.Creditcard;
import com.hunglee.bikerentalapp.Models.orders.Order;
import com.hunglee.bikerentalapp.ultis.roomdb.AppDatabase;

import java.util.List;

public class DetailPresenter implements DetailContract.Presenter {
    private final AppDatabase mDb;

    public DetailPresenter(AppDatabase mDb) {
        this.mDb = mDb;
    }


    @Override
    public List<Creditcard> getUserCreditcard() {
        return mDb.creditcardDao().findAllCardSync();
    }

    @Override
    public List<Order> getListOrderByStatus(int status) {
        return mDb.orderDao().findOrderWithStatus((status));
    }
}
