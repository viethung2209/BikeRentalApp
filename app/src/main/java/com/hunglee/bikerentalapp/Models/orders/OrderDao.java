package com.hunglee.bikerentalapp.Models.orders;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert(onConflict = REPLACE)
    void insertOrder(Order order);

    @Insert(onConflict = IGNORE)
    void insertOrReplaceOrder(Order... orders);

    @Update(onConflict = REPLACE)
    void updateOrder(Order order);

    @Query("DELETE FROM `Order`")
    void deleteAll();

    @Query("SELECT * FROM `Order`")
    List<Order> findAllOrderSync();

    @Query("SELECT * FROM `Order` WHERE status = :status")
    List<Order> findOrderWithStatus(int status);
}
