package com.hunglee.bikerentalapp.ultis.roomdb.bikes;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bike {
    @PrimaryKey(autoGenerate = true)
    public long bikeId;

    @ColumnInfo(name = "bike_name")
    public String name;

    @ColumnInfo(name = "bike_des")
    public String description;

    @ColumnInfo(name = "bike_price")
    public int price;

    @ColumnInfo(name = "bike_code")
    public String code;

    @ColumnInfo(name = "bike_power")
    public Integer power = 100;

    @ColumnInfo(name = "bike_image")
    public int image;

}
