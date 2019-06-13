package com.codexio.devcamp.currencyconvertor.app.services;

import com.codexio.devcamp.currencyconvertor.app.domain.models.CurrencyServiceModel;
import com.codexio.devcamp.currencyconvertor.app.domain.models.ImportRootHistoryCurrencyBindingModel;

import java.io.IOException;
import java.util.List;


public interface CurrencyService {
List<CurrencyServiceModel> getAllCurrencyServiceModels();
List<ImportRootHistoryCurrencyBindingModel> getLastThreeMonthRateBindingModels() throws IOException;
}
