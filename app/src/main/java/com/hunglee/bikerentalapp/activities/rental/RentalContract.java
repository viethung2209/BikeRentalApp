package com.hunglee.bikerentalapp.activities.rental;

import com.hunglee.bikerentalapp.Models.orders.Order;

import java.util.List;

public interface RentalContract {
    interface View {
        void initView(Order order);

        void handlePauseBtn(Order order);

        void handleContinueBtn(Order order);

        void handleGiveBackBtn(Order order);
    }

    interface Presenter {
        List<Order> getAllOrderByStatus(int status);

    }
}
