package com.codexio.devcamp.currencyconvertor.app.services;

import com.codexio.devcamp.currencyconvertor.app.domain.entities.Currency;
import com.codexio.devcamp.currencyconvertor.app.domain.models.CurrencyServiceModel;
import com.codexio.devcamp.currencyconvertor.app.domain.models.SeedCurrencyBindingModel;
import com.codexio.devcamp.currencyconvertor.app.repository.CurrencyRepository;
import com.codexio.devcamp.currencyconvertor.app.utils.CurrencyScrape;
import com.codexio.devcamp.currencyconvertor.app.utils.SecondaryCurrencyScrape;
import com.codexio.devcamp.currencyconvertor.app.utils.ValidatorUtil;
import com.codexio.devcamp.currencyconvertor.constants.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final CurrencyScrape currencyScrape;
    private final SecondaryCurrencyScrape secondaryCurrencyScrape;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;


    public CurrencyServiceImpl(CurrencyRepository currencyRepository, CurrencyScrape currencyScrape,
                               SecondaryCurrencyScrape secondaryCurrencyScrape, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.currencyRepository = currencyRepository;
        this.currencyScrape = currencyScrape;
        this.secondaryCurrencyScrape = secondaryCurrencyScrape;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    /**
     * This method is scheduled to seeds or update database of every hour.
     * Cron : 0 0 0/1 1/1 * *
     */
    @Override
    @Scheduled(cron = "* * * * * *")
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
        return List.of(this.modelMapper.map(this.currencyRepository.getAll().toArray(), CurrencyServiceModel[].class));
    }

    private void areAllCurrenciesValid(List<SeedCurrencyBindingModel> rawCurrencies) {
        rawCurrencies.forEach(rawCurrency -> {
            if (!this.validatorUtil.isValid(rawCurrency)) {
                throw new IllegalArgumentException(Constants.SCRAPPED_WRONG_DATA_MESSAGE);
            }
        });
    }
}
