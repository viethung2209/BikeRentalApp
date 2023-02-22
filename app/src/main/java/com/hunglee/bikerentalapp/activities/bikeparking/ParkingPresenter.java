package com.hunglee.bikerentalapp.activities.bikeparking;

import com.hunglee.bikerentalapp.App;
import com.hunglee.bikerentalapp.Models.bikeparkings.BikeParking;
import com.hunglee.bikerentalapp.Models.creditcards.Creditcard;
import com.hunglee.bikerentalapp.ultis.roomdb.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class ParkingPresenter implements ParkingContract.Presenter {
    private final AppDatabase mDb;

    public ParkingPresenter(AppDatabase mDb) {
        this.mDb = mDb;
    }

    @Override
    public List<BikeParking> getListBikeFromDb() {
        List<BikeParking> list = new ArrayList<>();
        list = mDb.bikeParkingDao().findAllBikeParkingSync();
        for (BikeParking parking :
                list) {
            parking.bikeNumber = mDb.bikeDao().getBikeByParkingId((int) parking.parkingId).size();
        }
        return list;
    }

    @Override
    public List<Creditcard> getListCreditcard() {
        List<Creditcard> list= new ArrayList<>();
        list = mDb.creditcardDao().findAllCardSync();
        return  list;
    }
}
