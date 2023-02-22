package com.hunglee.bikerentalapp.activities.creditcard;

import com.hunglee.bikerentalapp.Models.creditcards.Creditcard;
import com.hunglee.bikerentalapp.Models.transaction.Transaction;

import java.util.List;

public interface CreditcardContract {
    interface View {
        void initUserInforCreditcard(Creditcard creditcard);
    }

    interface Presenter {
        Creditcard getOwnerCreditCard();

        List<Transaction> getListTransaction();
    }
}
