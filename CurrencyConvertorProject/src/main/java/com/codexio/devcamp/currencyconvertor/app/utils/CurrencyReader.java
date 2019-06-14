package com.codexio.devcamp.currencyconvertor.app.utils;

import com.codexio.devcamp.currencyconvertor.app.domain.models.SeedCurrencyBindingModel;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CurrencyReader {
   List<SeedCurrencyBindingModel> getCurrencyNameEuroRate() throws IOException;
}
