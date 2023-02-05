package com.hunglee.bikerentalapp.ultis.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hunglee.bikerentalapp.ultis.roomdb.bikeparkings.BikeParking;
import com.hunglee.bikerentalapp.ultis.roomdb.bikeparkings.BikeParkingDao;
import com.hunglee.bikerentalapp.ultis.roomdb.bikes.Bike;
import com.hunglee.bikerentalapp.ultis.roomdb.bikes.BikeDao;
import com.hunglee.bikerentalapp.ultis.roomdb.creditcards.Creditcard;
import com.hunglee.bikerentalapp.ultis.roomdb.creditcards.CreditcardDao;
import com.hunglee.bikerentalapp.ultis.roomdb.orders.Order;
import com.hunglee.bikerentalapp.ultis.roomdb.orders.OrderDao;
import com.hunglee.bikerentalapp.ultis.roomdb.transaction.Transaction;
import com.hunglee.bikerentalapp.ultis.roomdb.transaction.TransactionDao;

@Database(entities = {Bike.class, BikeParking.class, Order.class, Creditcard.class, Transaction.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract BikeDao bikeDao();

    public abstract BikeParkingDao bikeParkingDao();

    public abstract OrderDao orderDao();

    public abstract CreditcardDao creditcardDao();

    public abstract TransactionDao transactionDao();

    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                    // To simplify the codelab, allow queries on the main thread.
                    // Don't do this on a real app! See PersistenceBasicSample for an example.
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
