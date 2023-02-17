package com.hunglee.bikerentalapp.Models.transaction;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    long transactionId;

    @ColumnInfo(name = "transaction_name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "value")
    public String value;

    @ColumnInfo(name = "type")
    public int type;

}
