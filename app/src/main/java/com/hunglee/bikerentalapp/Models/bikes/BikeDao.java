package com.hunglee.bikerentalapp.Models.bikes;


import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BikeDao {
    @Insert(onConflict = REPLACE)
    void inserBike(Bike bike);

    @Insert(onConflict = IGNORE)
    void insertOrReplaceBike(Bike... bikes);

    @Update(onConflict = REPLACE)
    void updateBike(Bike bike);

    @Query("DELETE FROM Bike")
    void deleteAll();

    @Query("SELECT * FROM Bike")
    List<Bike> findAllBikeSync();

    @Query("SELECT * FROM Bike WHERE bike_code = :code")
    Bike getBikeByCode(String code);

    @Query("SELECT * FROM Bike WHERE parking_id = :parkingId")
    List<Bike> getBikeByParkingId(int parkingId);

}
