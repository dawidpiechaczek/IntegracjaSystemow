package com.example.dawid.visitwroclove.model;

import pl.mobiltek.paymentsmobile.dotpay.model.PaymentCardData;

public class ShopData {

    private static final String name = "Jan";
    private static final String lastName = "Janowy";
    private static final String email = "j.janowy@jan.pl";

    //    private  final String merchantId = "777777";
    private static final String merchantId = "10000";
    private static final double productPrice = 1759;

    private static final String description = "Za  tel. Sony Xperia Z3";
    private static final String currency = "PLN";
    private static final String language = "pl";

    private static final PaymentCardData paymentCard = new PaymentCardData("4444 4444 4444 4448", "448", "Jan", "Nowak", "08", "2017");


    public static String getName() {
        return name;
    }

    public static String getLastName() {
        return lastName;
    }

    public static String getEmail() {
        return email;
    }

    public static String getMerchantId() {
        return merchantId;
    }

    public static double getProductPrice() {
        return productPrice;
    }

    public static String getDescription() {
        return description;
    }

    public static String getCurrency() {
        return currency;
    }

    public static String getLanguage() {
        return language;
    }

    public static PaymentCardData getPaymentCard() {
        return paymentCard;
    }
}