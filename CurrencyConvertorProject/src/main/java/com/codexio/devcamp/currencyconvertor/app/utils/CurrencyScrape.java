package com.codexio.devcamp.currencyconvertor.app.utils;

import java.util.Map;

/**
 * Gets all currencies from the website
 * https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html
 */
public class CurrencyScrape implements CurrencyReader {
    @Override
    public Map<String, String> getCurrencyNameEuroRate() {
        //TODO use this: https://jsoup.org/
        return null;
    }
}
