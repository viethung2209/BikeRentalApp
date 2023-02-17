package com.hunglee.bikerentalapp.Models.creditcards;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CreditcardDao {
    @Insert(onConflict = REPLACE)
    void insertCard(Creditcard creditcard);

    @Insert(onConflict = IGNORE)
    void insertOrderReplaceCard(Creditcard... creditcards);

    @Update(onConflict = REPLACE)
    void updateCard(Creditcard creditcard);

    @Query("DELETE FROM Creditcard")
    void deteleAll();

    @Query("SELECT * FROM Creditcard")
    List<Creditcard> findAllCardSync();
}
