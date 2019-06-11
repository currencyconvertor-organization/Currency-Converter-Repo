package com.codexio.devcamp.currencyconvertor.app.services;

import com.codexio.devcamp.currencyconvertor.app.domain.models.CurrencyServiceModel;
import com.codexio.devcamp.currencyconvertor.app.domain.models.HistoricalCurrencyBindingModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


public interface CurrencyService {
void seedCurrencies() throws IOException;
List<CurrencyServiceModel> getAllCurrencyServiceModels();
void writeDailyCurrencyRatesIntoFile() throws IOException;
List<HistoricalCurrencyBindingModel> getAllHistoricalCurrencyBindingModels(LocalDate from,LocalDate to) throws FileNotFoundException;

}
