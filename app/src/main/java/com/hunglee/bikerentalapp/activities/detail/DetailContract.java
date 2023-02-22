package com.hunglee.bikerentalapp.activities.detail;

import com.hunglee.bikerentalapp.Models.bikes.Bike;
import com.hunglee.bikerentalapp.Models.creditcards.Creditcard;
import com.hunglee.bikerentalapp.Models.orders.Order;

import java.util.List;

public interface DetailContract {
    interface View {
        void initViewDetail(Bike bike);

        void errRentBikeMsg();

        void errCreditcardMsg();

        void onClickInsertButton(Bike bike);
    }

    interface Presenter {
        List<Creditcard> getUserCreditcard();

        List<Order> getListOrderByStatus(int status);
    }
}
