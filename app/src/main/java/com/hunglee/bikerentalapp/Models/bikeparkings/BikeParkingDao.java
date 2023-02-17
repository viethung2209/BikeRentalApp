package com.hunglee.bikerentalapp.Models.bikeparkings;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BikeParkingDao {

    @Insert(onConflict = REPLACE)
    void insertParking(BikeParking bikeParking);

    @Insert(onConflict = IGNORE)
    void insertOrReplaceBike(BikeParking... bikeParkings);

    @Update(onConflict = REPLACE)
    void updateBike(BikeParking bikeParking);

    @Query("DELETE FROM BikeParking")
    void deleteAll();

    @Query("SELECT * FROM BikeParking")
    List<BikeParking> findAllBikeParkingSync();
}
