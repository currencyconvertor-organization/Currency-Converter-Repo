package com.codexio.devcamp.currencyconvertor.app.services;

import com.codexio.devcamp.currencyconvertor.app.domain.models.CurrencyServiceModel;

import java.io.IOException;
import java.util.List;


public interface CurrencyService {
void seedCurrencies() throws IOException;
List<CurrencyServiceModel> getAllCurrencyServiceModels();
}
