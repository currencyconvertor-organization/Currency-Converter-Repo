package com.codexio.devcamp.currencyconvertor.app.services;

import com.codexio.devcamp.currencyconvertor.app.domain.entities.Currency;
import com.codexio.devcamp.currencyconvertor.app.domain.models.CurrencyServiceModel;
import com.codexio.devcamp.currencyconvertor.app.domain.models.SeedCurrencyBindingModel;
import com.codexio.devcamp.currencyconvertor.app.repository.CurrencyRepository;
import com.codexio.devcamp.currencyconvertor.app.utils.CurrencyScrape;
import com.codexio.devcamp.currencyconvertor.app.utils.SecondaryCurrencyScrape;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final CurrencyScrape currencyScrape;
    private final SecondaryCurrencyScrape secondaryCurrencyScrape;
    private final ModelMapper modelMapper;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository, CurrencyScrape currencyScrape, SecondaryCurrencyScrape secondaryCurrencyScrape, ModelMapper modelMapper) {
        this.currencyRepository = currencyRepository;
        this.currencyScrape = currencyScrape;
        this.secondaryCurrencyScrape = secondaryCurrencyScrape;
        this.modelMapper = modelMapper;
    }

    @Override
    @Scheduled(cron = "0 0 0/1 1/1 * *")
    public void seedCurrencies() throws IOException {
        System.out.println("1 HOUR PASSED");
        List<SeedCurrencyBindingModel> rawCurrencies;
        try {
            rawCurrencies = this.currencyScrape.getCurrencyNameEuroRate();
        } catch (Exception e) {
            rawCurrencies = this.secondaryCurrencyScrape.getCurrencyNameEuroRate();
        }
        rawCurrencies.forEach(rawCurrency -> {
            if (this.currencyRepository.existsByCode(rawCurrency.getCode())) {
                this.currencyRepository.updateCurrencyRate(rawCurrency.getCode(), rawCurrency.getEuroRate());
            } else {
                this.currencyRepository.save(this.modelMapper.map(rawCurrency, Currency.class));
            }
        });
    }

    @Override
    public List<CurrencyServiceModel> getAllCurrencyServiceModels() {
        return List.of(this.modelMapper.map(this.currencyRepository.getAll().toArray(), CurrencyServiceModel[].class));
    }
}
