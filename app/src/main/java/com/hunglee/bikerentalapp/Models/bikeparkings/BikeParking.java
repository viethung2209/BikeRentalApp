package com.hunglee.bikerentalapp.Models.bikeparkings;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BikeParking {
    @PrimaryKey(autoGenerate = true)
    public long parkingId;

    @ColumnInfo(name = "parking_name")
    public String name;

    @ColumnInfo(name = "parking_description")
    public String description;


    @ColumnInfo(name = "parking_numberbike")
    public int bikeNumber;

    @ColumnInfo(name = "parking_image")
    public int image;
}
