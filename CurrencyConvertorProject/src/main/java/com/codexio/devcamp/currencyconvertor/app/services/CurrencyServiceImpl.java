package com.codexio.devcamp.currencyconvertor.app.services;

import com.codexio.devcamp.currencyconvertor.app.repository.CurrencyRepository;
import com.codexio.devcamp.currencyconvertor.app.utils.CurrencyScrape;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final CurrencyScrape currencyScrape;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository, CurrencyScrape currencyScrape) {
        this.currencyRepository = currencyRepository;
        this.currencyScrape = currencyScrape;
    }


    @Override
    @Scheduled(cron = "* * * * * ?")
    public void seedCurrencies() throws IOException {
        this.currencyScrape.getCurrencyNameEuroRate();
    }
}
