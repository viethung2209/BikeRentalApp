package com.hunglee.bikerentalapp.ultis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.hunglee.bikerentalapp.Models.BikeParkingModel;
import com.hunglee.bikerentalapp.Models.MainModel;
import com.hunglee.bikerentalapp.Models.OrdersModel;
import com.hunglee.bikerentalapp.R;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    final static String DATABASE_NAME = "rent-bike-db.db";
    final static int VERSION = 9;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS orders" +
                        "(id integer primary key autoincrement," +
                        "bike text," +
                        "quantity int," +
                        "image int," +
                        "price int," +
                        "description text," +
                        "cardholderName text," +
                        "cardNumber text," +
                        "issuingBank text," +
                        "expDate date," +
                        "securityCode int" +
                        ")"
        );

        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS bikes" +
                        "(" +
                        "id integer primary key autoincrement," +
                        "bikeName text," +
                        "image int," +
                        "price text," +
                        "description text" +
                        ")"
        );

        ContentValues values = new ContentValues();
        values.put("bikeName", "Xe thường 1");
        values.put("price", "400000");
        values.put("image", R.drawable.xe_thuong_1);
        values.put("description", "Xe đạp thường, 1 yên xe và 1 chỗ ngồi phía sau");
        sqLiteDatabase.insert("bikes", null, values);

        values.put("bikeName", "Xe thường 2");
        values.put("price", "550000");
        values.put("image", R.drawable.xe_thuong_2);
        values.put("description", "Xe đạp thường 2: Là xe đạp đôi, có 2 yên, 2 bàn đạp và 1 ghế ngồi sau");
        sqLiteDatabase.insert("bikes", null, values);

        values.put("bikeName", "Xe điện 1");
        values.put("price", "700000");
        values.put("image", R.drawable.xe_dien_1);
        values.put("description", "Xe đạp điện loại 1: giống với xe đạp thường loại 1 nhưng có motor điện giúp xe chạy nhanh hơn");
        sqLiteDatabase.insert("bikes", null, values);

        values.put("bikeName", "Xe điện 2");
        values.put("price", "1000000");
        values.put("image", R.drawable.xe_dien_2);
        values.put("description", "Xe đạp điện loại 2: giống với xe đạp thường loại 2 nhưng có motor điện giúp xe chạy nhanh hơn");
        sqLiteDatabase.insert("bikes", null, values);

        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS parkings" +
                        "(" +
                        "id integer primary key autoincrement," +
                        "parkingName text," +
                        "image int," +
                        "bikeNumber int," +
                        "description text" +
                        ")"
        );

        ContentValues parkings = new ContentValues();
        parkings.put("parkingName", "Bãi xe 1");
        parkings.put("bikeNumber", 50);
        parkings.put("image", R.drawable.bai_xe_1);
        parkings.put("description", "Bãi gửi xe số 1");
        sqLiteDatabase.insert("parkings", null, parkings);

        parkings.put("parkingName", "Bãi xe 2");
        parkings.put("bikeNumber", 60);
        parkings.put("image", R.drawable.bai_xe_2);
        parkings.put("description", "Bãi gửi xe số 2");
        sqLiteDatabase.insert("parkings", null, parkings);

        parkings.put("parkingName", "Bãi xe 3");
        parkings.put("bikeNumber", 70);
        parkings.put("image", R.drawable.bai_xe_3);
        parkings.put("description", "Bãi gửi xe số 3");
        sqLiteDatabase.insert("parkings", null, parkings);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists orders");
        sqLiteDatabase.execSQL("drop table if exists bikes");
        sqLiteDatabase.execSQL("drop table if exists parkings");
        onCreate(sqLiteDatabase);
    }

    public boolean insertBike(
            String bikeName,
            int price,
            String description
    ) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("bikeName", bikeName);
        values.put("price", price);
        values.put("description", description);
        long id = sqLiteDatabase.insert("bikes", null, values);
        return id > 0;
    }

    public boolean insertOrder(
            String bike,
            int quantity,
            int image,
            int price,
            String description,
            String cardholderName,
            String cardNumber,
            String issuingBank,
            String expDate,
            int securityCode) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("cardholderName", cardholderName);
        values.put("cardNumber", cardNumber);
        values.put("issuingBank", issuingBank);
        values.put("expDate", expDate);
        values.put("bike", bike);
        values.put("quantity", quantity);
        values.put("image", image);
        values.put("price", price);
        values.put("description", description);
        values.put("securityCode", securityCode);
        long id = sqLiteDatabase.insert("orders", null, values);
        return id > 0;
    }

    public ArrayList<OrdersModel> getOrders() {
        ArrayList<OrdersModel> list = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select id, bike, quantity, image, price from orders", null);
        if (cursor.moveToFirst()) {
            do {
                OrdersModel model = new OrdersModel();
                model.setOrderNumber(cursor.getInt(2) + "");
                model.setSoldItemName(cursor.getString(1));
                model.setOrderImage(cursor.getInt(3));
                model.setPrice(cursor.getInt(4) + "");
                list.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return list;
    }

    public ArrayList<MainModel> getBikes() {
        ArrayList<MainModel> list = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select bikeName, image, price, description from bikes", null);
        if (cursor.moveToFirst()) {
            do {
                MainModel model = new MainModel();
                model.setName(cursor.getString(0));
                model.setImage(cursor.getInt(1));
                model.setPrice(cursor.getString(2));
                model.setDescription(cursor.getString(3));
                list.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return list;
    }

    public ArrayList<BikeParkingModel> getPakings() {
        ArrayList<BikeParkingModel> list = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select parkingName, image, bikeNumber, description from parkings", null);
        if (cursor.moveToFirst()) {
            do {
                BikeParkingModel model = new BikeParkingModel();
                model.setName(cursor.getString(0));
                model.setImage(cursor.getInt(1));
                model.setBikeNumber(cursor.getInt(2));
                model.setDescription(cursor.getString(3));
                list.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return list;
    }
}
