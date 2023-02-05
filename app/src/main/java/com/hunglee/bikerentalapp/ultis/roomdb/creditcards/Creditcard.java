package com.hunglee.bikerentalapp.ultis.roomdb.creditcards;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Creditcard {
    @PrimaryKey(autoGenerate = true)
    public long cardId;

    @ColumnInfo(name = "account_number")
    public String accountNumber;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "expiration_date")
    public String expirationDate;

    @ColumnInfo(name = "security_code")
    public String securityCode;

    @ColumnInfo(name = "issuing_bank")
    public String inssingBank;

    @ColumnInfo(name = "balance")
    public int balance = 10000000;


}
