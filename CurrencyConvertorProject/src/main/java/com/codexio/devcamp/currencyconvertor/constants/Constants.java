package com.codexio.devcamp.currencyconvertor.constants;

public abstract class Constants {
    public final static String HISTORY_CURRENCIES_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/";
    public final static String HISTORY_CURRENCIES_FILE_NAME = "currencies.pdf";
    private static final int CURRENCY_CODE_LENGTH = 3;
    public static final String INVALID_CURRENCY_CODE_MESSAGE =
            "Currency code should be " + CURRENCY_CODE_LENGTH + " characters long and all upper case!";
    public static final String NULL_CURRENCY_CODE_MESSAGE = "Currency code can't be null!";

    public static final String INVALID_CURRENCY_NAME_MESSAGE =
            "Currency name should start with capital letter and contains only letters";
    public static final String INVALID_AMOUNT_MESSAGE = "Amount should be a positive number!";
    public static final String NULL_AMOUNT_MESSAGE = "Amount can't be null!";
    public static final String NULL_CURRENCY_NAME_MESSAGE = "Currency name can't be null!";
    public static final String INVALID_CURRENCY_DATE_FORMAT_MESSAGE = "Wrong currency date format.";
    public static final String NULL_CURRENCY_DATE_MESSAGE = "Currency date can't be null!";
    public static final String CURRENCY_CONVERTER_URL = "https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html";
    public static final String SECONDARY_CURRENCY_CONVERTER_URL = "https://www.xe.com/currencytables/?from=EUR";
    public static final String CLIENT_URL = "http://localhost:3000";
    public static final String SCRAPPED_WRONG_DATA_MESSAGE = "Something went wrong with the data which has been scrapped!";

    public static final String INVALID_CURRENCY_EURO_RATE = "Currency rate should be a positive number!";
    public static final String NULL_CURRENCY_RATE_MESSAGE = "Currency rate can't be null!";

    private Constants() {

    }
}
