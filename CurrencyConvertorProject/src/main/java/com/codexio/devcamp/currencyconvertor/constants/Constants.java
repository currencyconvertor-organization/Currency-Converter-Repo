package com.codexio.devcamp.currencyconvertor.constants;

public abstract class Constants {
    public static final int CURRENCY_NAME_LENGTH = 3;
    public static final String INVALID_CURRENCY_NAME_LENGTH_MESSAGE =
            "Currency name should be " + CURRENCY_NAME_LENGTH + " characters long!";

    private Constants() {

    }
}
