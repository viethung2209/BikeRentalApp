package com.hunglee.bikerentalapp.ultis.server;

import com.hunglee.bikerentalapp.Models.bikes.Bike;
import com.hunglee.bikerentalapp.Models.orders.Order;

public interface Server {
    void QueryDbFromServer();

    void syncCreditCardWithInterBank(
            String nameHolder,
            String accountNumber,
            String issuingBank,
            String expDate,
            String code
    );

    void apiAddBalance();

    void createRentBikeTransaction(Bike bike);

    void createGiveBackBikeTransaction(Order order, long time);

    long calculateMoney(int bikeCode, long time);

    void depositMoney(int money, Order order);

}
