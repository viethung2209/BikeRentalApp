package com.hunglee.bikerentalapp.Models.transaction;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransactionDao {
    @Insert(onConflict = REPLACE)
    void insertTransaction(Transaction transaction);

    @Insert(onConflict = IGNORE)
    void insertOrReplaceTransaciton(Transaction... transactions);

    @Update(onConflict = REPLACE)
    void updateTransaction(Transaction transaction);

    @Query("SELECT * FROM `Transaction` ORDER BY transactionId DESC")
    List<Transaction> findAllTransaction();

    @Query("DELETE FROM `Transaction`")
    void deleteAll();
}
