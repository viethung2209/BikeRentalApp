package com.hunglee.bikerentalapp.ultis.server;

import com.hunglee.bikerentalapp.Models.bikeparkings.BikeParking;
import com.hunglee.bikerentalapp.Models.bikes.Bike;
import com.hunglee.bikerentalapp.Models.creditcards.Creditcard;
import com.hunglee.bikerentalapp.Models.orders.Order;
import com.hunglee.bikerentalapp.Models.transaction.Transaction;
import com.hunglee.bikerentalapp.R;
import com.hunglee.bikerentalapp.ultis.Constant;
import com.hunglee.bikerentalapp.ultis.roomdb.AppDatabase;

import java.util.concurrent.TimeUnit;

public class ServerPresenter implements Server {
    private final AppDatabase roomDb;


    public ServerPresenter(AppDatabase roomDb) {
        this.roomDb = roomDb;
    }

    @Override
    public void QueryDbFromServer() {
        initBikeDatabase(roomDb);
        initParkingBikeDatabase(roomDb);
    }

    @Override
    public void syncCreditCardWithInterBank(String nameHolder, String accountNumber, String issuingBank, String expDate, String code) {
        Creditcard creditcard = new Creditcard();
        creditcard.name = nameHolder;
        creditcard.accountNumber = accountNumber;
        creditcard.inssingBank = issuingBank;
        creditcard.expirationDate = expDate;
        creditcard.securityCode = code;

        roomDb.creditcardDao().insertCard(creditcard);

        apiAddBalance();
    }

    @Override
    public void apiAddBalance() {
        Creditcard creditcard = roomDb.creditcardDao().findAllCardSync().get(0);
        creditcard.balance = 10000000;
    }

    @Override
    public void createRentBikeTransaction(Bike bike) {
        Order order = new Order();
        order.bikeCode = bike.code;
        order.cost = String.valueOf(bike.price);
        order.description = bike.description;
        order.name = "Thuê Xe " + bike.name;
        order.image = bike.image;
        order.status = Constant.ON_RENTING;
        roomDb.orderDao().insertOrder(order);

        Transaction transaction = new Transaction();
        transaction.description = "Đặt cọc tiền thuê xe " + bike.name;
        transaction.type = Constant.DEPOSIT;
        transaction.name = "Thuê xe " + bike.name;
        transaction.value = String.valueOf(bike.price);

        roomDb.transactionDao().insertTransaction(transaction);
    }

    @Override
    public void createGiveBackBikeTransaction(Order order, long time) {
        Transaction transaction = new Transaction();
        transaction.type = Constant.WITHDRAW;
        transaction.name = "Hoàn trả tiền đặt cọc xe";
        transaction.value = order.cost;
        transaction.description = "Hoàn trả tiền đặt cọc xe " + order.name;
        roomDb.transactionDao().insertTransaction(transaction);

        transaction.type = Constant.DEPOSIT;
        transaction.name = "Thanh toán tiền thuê xe";
        transaction.value = String.valueOf(calculateMoney(Integer.parseInt(order.bikeCode), time));
        transaction.description = "Thanh toán tiền thuê xe " + order.name;
        roomDb.transactionDao().insertTransaction(transaction);

        int money = (int) calculateMoney(Integer.parseInt(order.bikeCode), time);
        depositMoney(money, order);
    }

    @Override
    public long calculateMoney(int bikeCode, long time) {

        long value = 1;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        if (minutes <= 10)
            return value;
        else if (minutes <= 30) {
            value = 10000;
        } else {
            long t = (minutes - 30) / 15;
            value = 10000 + (t * 3000);
        }

        if (20 < bikeCode && bikeCode < 40)
            value = (long) (value * 1.5);
        else if (bikeCode > 40)
            value = value * 2;
        return value;
    }

    @Override
    public void depositMoney(int money, Order order) {
        Creditcard creditcard = new Creditcard();
        creditcard = roomDb.creditcardDao().findAllCardSync().get(0);
        creditcard.balance -= money;
        roomDb.creditcardDao().updateCard(creditcard);

        order.status = Constant.ON_STOP;
        roomDb.orderDao().updateOrder(order);
    }

    private void initBikeDatabase(AppDatabase mDb) {
        Bike bike = new Bike();
        bike.name = "Xe thường 1";
        bike.price = 400000;
        bike.image = R.drawable.xe_thuong_1;
        bike.description = "Xe đạp thường, 1 yên xe và 1 chỗ ngồi phía sau";
        bike.code = "10";
        bike.parkingId = 1;
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe thường 1";
        bike.price = 400000;
        bike.image = R.drawable.xe_thuong_1;
        bike.description = "Xe đạp thường, 1 yên xe và 1 chỗ ngồi phía sau";
        bike.code = "11";
        bike.parkingId = 2;
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe thường 1";
        bike.price = 400000;
        bike.image = R.drawable.xe_thuong_1;
        bike.description = "Xe đạp thường, 1 yên xe và 1 chỗ ngồi phía sau";
        bike.code = "12";
        bike.parkingId = 3;
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe thường 1";
        bike.price = 400000;
        bike.image = R.drawable.xe_thuong_1;
        bike.description = "Xe đạp thường, 1 yên xe và 1 chỗ ngồi phía sau";
        bike.code = "13";
        bike.parkingId = 1;
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe thường 1";
        bike.price = 400000;
        bike.image = R.drawable.xe_thuong_1;
        bike.description = "Xe đạp thường, 1 yên xe và 1 chỗ ngồi phía sau";
        bike.code = "14";
        bike.parkingId = 1;
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe thường 1";
        bike.price = 400000;
        bike.image = R.drawable.xe_thuong_1;
        bike.description = "Xe đạp thường, 1 yên xe và 1 chỗ ngồi phía sau";
        bike.code = "15";
        bike.parkingId = 2;
        mDb.bikeDao().inserBike(bike);


        bike.name = "Xe thường 2";
        bike.price = 550000;
        bike.image = R.drawable.xe_thuong_2;
        bike.description = "Xe đạp thường 2: Là xe đạp đôi, có 2 yên, 2 bàn đạp và 1 ghế ngồi sau";
        bike.code = "21";
        bike.parkingId = 1;
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe thường 2";
        bike.price = 550000;
        bike.image = R.drawable.xe_thuong_2;
        bike.description = "Xe đạp thường 2: Là xe đạp đôi, có 2 yên, 2 bàn đạp và 1 ghế ngồi sau";
        bike.code = "22";
        bike.parkingId = 2;
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe thường 2";
        bike.price = 550000;
        bike.image = R.drawable.xe_thuong_2;
        bike.description = "Xe đạp thường 2: Là xe đạp đôi, có 2 yên, 2 bàn đạp và 1 ghế ngồi sau";
        bike.code = "23";
        bike.parkingId = 3;
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe thường 2";
        bike.price = 550000;
        bike.image = R.drawable.xe_thuong_2;
        bike.description = "Xe đạp thường 2: Là xe đạp đôi, có 2 yên, 2 bàn đạp và 1 ghế ngồi sau";
        bike.code = "24";
        bike.parkingId = 1;
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe điện 1";
        bike.price = 700000;
        bike.image = R.drawable.xe_dien_1;
        bike.description = "Xe đạp điện loại 1: giống với xe đạp thường loại 1 nhưng có motor điện giúp xe chạy nhanh hơn";
        bike.code = "31";
        bike.parkingId = 2;
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe điện 1";
        bike.price = 700000;
        bike.image = R.drawable.xe_dien_1;
        bike.description = "Xe đạp điện loại 1: giống với xe đạp thường loại 1 nhưng có motor điện giúp xe chạy nhanh hơn";
        bike.code = "32";
        bike.parkingId = 1;
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe điện 1";
        bike.price = 700000;
        bike.image = R.drawable.xe_dien_1;
        bike.description = "Xe đạp điện loại 1: giống với xe đạp thường loại 1 nhưng có motor điện giúp xe chạy nhanh hơn";
        bike.code = "33";
        bike.parkingId = 2;
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe điện 1";
        bike.price = 700000;
        bike.image = R.drawable.xe_dien_1;
        bike.description = "Xe đạp điện loại 1: giống với xe đạp thường loại 1 nhưng có motor điện giúp xe chạy nhanh hơn";
        bike.code = "34";
        bike.parkingId = 3;
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe điện 1";
        bike.price = 700000;
        bike.image = R.drawable.xe_dien_1;
        bike.description = "Xe đạp điện loại 1: giống với xe đạp thường loại 1 nhưng có motor điện giúp xe chạy nhanh hơn";
        bike.code = "35";
        bike.parkingId = 1;
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe điện 2";
        bike.price = 1000000;
        bike.image = R.drawable.xe_dien_2;
        bike.description = "Xe đạp điện loại 2: giống với xe đạp thường loại 2 nhưng có motor điện giúp xe chạy nhanh hơn";
        bike.code = "41";
        bike.parkingId = 2;
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe điện 2";
        bike.price = 1000000;
        bike.image = R.drawable.xe_dien_2;
        bike.description = "Xe đạp điện loại 2: giống với xe đạp thường loại 2 nhưng có motor điện giúp xe chạy nhanh hơn";
        bike.code = "42";
        bike.parkingId = 1;
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe điện 2";
        bike.price = 1000000;
        bike.image = R.drawable.xe_dien_2;
        bike.description = "Xe đạp điện loại 2: giống với xe đạp thường loại 2 nhưng có motor điện giúp xe chạy nhanh hơn";
        bike.code = "43";
        bike.parkingId = 2;
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe điện 2";
        bike.price = 1000000;
        bike.image = R.drawable.xe_dien_2;
        bike.description = "Xe đạp điện loại 2: giống với xe đạp thường loại 2 nhưng có motor điện giúp xe chạy nhanh hơn";
        bike.code = "44";
        bike.parkingId = 3;
        mDb.bikeDao().inserBike(bike);

        bike.name = "Xe điện 2";
        bike.price = 1000000;
        bike.image = R.drawable.xe_dien_2;
        bike.description = "Xe đạp điện loại 2: giống với xe đạp thường loại 2 nhưng có motor điện giúp xe chạy nhanh hơn";
        bike.code = "45";
        bike.parkingId = 1;
        mDb.bikeDao().inserBike(bike);


    }

    private void initParkingBikeDatabase(AppDatabase mDb) {

        BikeParking bikeParking = new BikeParking();
        bikeParking.name = "Bãi xe 1";
        bikeParking.bikeNumber = 50;
        bikeParking.image = R.drawable.bai_xe_1;
        bikeParking.description = "Bãi gửi xe số 1";
        mDb.bikeParkingDao().insertParking(bikeParking);

        bikeParking.name = "Bãi xe 2";
        bikeParking.bikeNumber = 50;
        bikeParking.image = R.drawable.bai_xe_2;
        bikeParking.description = "Bãi gửi xe số 2";
        mDb.bikeParkingDao().insertParking(bikeParking);

        bikeParking.name = "Bãi xe 3";
        bikeParking.bikeNumber = 50;
        bikeParking.image = R.drawable.bai_xe_3;
        bikeParking.description = "Bãi gửi xe số 3";
        mDb.bikeParkingDao().insertParking(bikeParking);

    }


}
