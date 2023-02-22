package com.hunglee.bikerentalapp.activities.bikeparking;

import com.hunglee.bikerentalapp.Models.bikeparkings.BikeParking;
import com.hunglee.bikerentalapp.Models.creditcards.Creditcard;

import java.util.List;

public interface ParkingContract {
    interface View {
        void ErrParkingMsg();

        void ErrCreditcardMsg();

    }

    interface Presenter {
        List<BikeParking> getListBikeFromDb();

        List<Creditcard> getListCreditcard();
    }
}
