package com.hunglee.bikerentalapp.activities.main;

import com.hunglee.bikerentalapp.Models.bikes.Bike;
import com.hunglee.bikerentalapp.Models.creditcards.Creditcard;
import com.hunglee.bikerentalapp.Models.orders.Order;

import java.util.List;

public interface MainContract {
    interface View {
        void errCreditCardMsg();
        void errRentBikeMsg();
        void emptyOrderMsg();
        void popupRentByCode();
        void popupCreditCard();
    }

    interface Presenter {
        List<Bike> getListBikeByParkingId(int id);
        List<Order> getListOrderByStatus(int status);
        List<Creditcard> getUserCreditCard();

    }
}
