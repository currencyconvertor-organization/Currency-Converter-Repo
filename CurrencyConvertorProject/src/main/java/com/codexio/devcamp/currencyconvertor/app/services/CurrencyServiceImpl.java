package com.codexio.devcamp.currencyconvertor.app.services;

import com.codexio.devcamp.currencyconvertor.app.domain.entities.Currency;
import com.codexio.devcamp.currencyconvertor.app.domain.models.CurrencyServiceModel;
import com.codexio.devcamp.currencyconvertor.app.domain.models.HistoryCurrencyBindingModel;
import com.codexio.devcamp.currencyconvertor.app.domain.models.SeedCurrencyBindingModel;
import com.codexio.devcamp.currencyconvertor.app.repository.CurrencyRepository;
import com.codexio.devcamp.currencyconvertor.app.utils.CurrencyScrape;
import com.codexio.devcamp.currencyconvertor.app.utils.HistoryCurrencyScrape;
import com.codexio.devcamp.currencyconvertor.app.utils.SecondaryCurrencyScrape;
import com.codexio.devcamp.currencyconvertor.app.utils.ValidatorUtil;
import com.codexio.devcamp.currencyconvertor.constants.Constants;
import com.google.gson.Gson;
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
    private final HistoryCurrencyScrape historyCurrencyScrape;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final Gson gson;


    public CurrencyServiceImpl(CurrencyRepository currencyRepository, CurrencyScrape currencyScrape,
                               SecondaryCurrencyScrape secondaryCurrencyScrape, HistoryCurrencyScrape historyCurrencyScrape, ModelMapper modelMapper, ValidatorUtil validatorUtil, Gson gson) {
        this.currencyRepository = currencyRepository;
        this.currencyScrape = currencyScrape;
        this.secondaryCurrencyScrape = secondaryCurrencyScrape;
        this.historyCurrencyScrape = historyCurrencyScrape;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
    }

    /**
     * This method is scheduled to seeds or update database of every hour.
     * Cron : 0 0 0/1 1/1 * *
     */
    @Override
    @Scheduled(cron = "0 0 0/1 1/1 * *")
    public void seedCurrencies() {
        List<SeedCurrencyBindingModel> rawCurrencies;
        try {
            rawCurrencies = this.currencyScrape.getCurrencyNameEuroRate();
            areAllCurrenciesValid(rawCurrencies);
        } catch (Exception e) {
            try {
                rawCurrencies = this.secondaryCurrencyScrape.getCurrencyNameEuroRate();
                areAllCurrenciesValid(rawCurrencies);
            } catch (Exception ex) {
                rawCurrencies = null;
            }
        }
        if (rawCurrencies == null) {
            throw new NullPointerException();
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
        return List.of(
                this.modelMapper.map(
                        this.currencyRepository.getAll().toArray(), CurrencyServiceModel[].class
                )
        );
    }

    /**
     * This method is scheduled to write once a day currency rates into file.
     * Cron : 0 0 8 * * ?
     */
    @Override
    @Scheduled(cron = "* * * * * ?")
    public List<HistoryCurrencyBindingModel> getLastThreeMonthRateBindingModels() throws IOException {
        String jsonCurrencyHistory = this.historyCurrencyScrape.getLastThreeMonthsRates();
        List<HistoryCurrencyBindingModel> test =List.of(this.gson.fromJson(jsonCurrencyHistory, HistoryCurrencyBindingModel[].class));
                return List.of(this.gson.fromJson(jsonCurrencyHistory, HistoryCurrencyBindingModel[].class));
    }

    private void areAllCurrenciesValid(List<SeedCurrencyBindingModel> rawCurrencies) {
        rawCurrencies.forEach(rawCurrency -> {
            if (!this.validatorUtil.isValid(rawCurrency)) {
                throw new IllegalArgumentException(Constants.SCRAPPED_WRONG_DATA_MESSAGE);
            }
        });
    }
}
