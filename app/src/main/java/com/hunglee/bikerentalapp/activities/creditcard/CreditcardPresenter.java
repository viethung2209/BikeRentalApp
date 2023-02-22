package com.hunglee.bikerentalapp.activities.creditcard;

import com.hunglee.bikerentalapp.Models.creditcards.Creditcard;
import com.hunglee.bikerentalapp.Models.transaction.Transaction;
import com.hunglee.bikerentalapp.ultis.roomdb.AppDatabase;

import java.util.List;

public class CreditcardPresenter implements CreditcardContract.Presenter {
    private final AppDatabase mDb;

    public CreditcardPresenter(AppDatabase mDb) {
        this.mDb = mDb;
    }


    @Override
    public Creditcard getOwnerCreditCard() {
        return mDb.creditcardDao().findAllCardSync().get(0);
    }

    @Override
    public List<Transaction> getListTransaction() {
        return mDb.transactionDao().findAllTransaction();
    }
}
