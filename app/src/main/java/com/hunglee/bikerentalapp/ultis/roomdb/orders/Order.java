package com.hunglee.bikerentalapp.ultis.roomdb.orders;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.hunglee.bikerentalapp.R;
import com.hunglee.bikerentalapp.ultis.Constant;

@Entity
public class Order {
    @PrimaryKey(autoGenerate = true)
    public long orderId;

    @ColumnInfo(name = "bike_name")
    public String name;

    @ColumnInfo(name = "cost")
    public String cost;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "bike_code")
    public String bikeCode;


    @ColumnInfo(name = "image")
    public int image = R.drawable.ic_baseline_monetization_on_24;

    @ColumnInfo(name = "status")
    public int status = Constant.ON_STOP;

    @ColumnInfo(name = "time_rent")
    public long timeRentBike = 0;
}
