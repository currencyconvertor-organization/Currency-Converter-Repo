package com.codexio.devcamp.currencyconvertor.app.services;

import com.codexio.devcamp.currencyconvertor.app.domain.entities.Currency;
import com.codexio.devcamp.currencyconvertor.app.repository.CurrencyRepository;
import com.codexio.devcamp.currencyconvertor.app.utils.CurrencyScrape;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        try {
            Map<String, String> rawCurrencies = this.currencyScrape.getCurrencyNameEuroRate();
            List<Currency> currencyList = new ArrayList<>();
            for (Map.Entry<String, String> stringStringEntry : rawCurrencies.entrySet()) {
                Currency currency = new Currency();
                currency.setName(stringStringEntry.getKey());
                currency.setEuroRate(new BigDecimal(stringStringEntry.getValue()));

                currencyList.add(currency);
            }

            this.currencyRepository.saveAll(currencyList);
        } catch (Exception e) {
            //TODO
        }

    }
}
