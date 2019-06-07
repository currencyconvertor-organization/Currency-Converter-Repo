package com.codexio.devcamp.currencyconvertor.app.utils;

import java.io.IOException;
import java.util.Map;

public interface CurrencyReader {
    Map<String, String> getCurrencyNameEuroRate() throws IOException;
}
