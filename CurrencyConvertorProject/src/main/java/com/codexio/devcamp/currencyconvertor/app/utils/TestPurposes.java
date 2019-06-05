package com.codexio.devcamp.currencyconvertor.app.utils;

import java.util.HashMap;
import java.util.Map;

public class TestPurposes implements CurrencyReader {
    @Override
    public Map<String, String> getCurrencyNameEuroRate() {
        Map<String, String> result = new HashMap<>();
        result.put("USD", "1.1257");
        result.put("BGN", "1.9558");
        result.put("GBP", "0.88631");

        return result;
    }
}
