package com.codexio.devcamp.currencyconvertor.constants;

public abstract class Constants {
    public static final int CURRENCY_NAME_LENGTH = 3;
    public static final String INVALID_CURRENCY_NAME_MESSAGE =
            "Currency name should be " + CURRENCY_NAME_LENGTH + " characters long and all lower case!";
    public static final String NULL_CURRENCY_NAME_MESSAGE = "Currency name can't be null!";

    public static  final String CURRENCY_CONVERTER_URL = "https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html";

    public static final String CLIENT_URL = "http://localhost:3001";

    private Constants() {

    }
}
