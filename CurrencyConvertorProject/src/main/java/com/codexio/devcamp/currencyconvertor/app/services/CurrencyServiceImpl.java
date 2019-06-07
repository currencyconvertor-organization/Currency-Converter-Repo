package com.codexio.devcamp.currencyconvertor.app.services;

import com.codexio.devcamp.currencyconvertor.app.domain.models.CurrencyServiceModel;
import com.codexio.devcamp.currencyconvertor.app.domain.models.CurrencyViewModel;
import com.codexio.devcamp.currencyconvertor.app.repository.CurrencyRepository;
import com.codexio.devcamp.currencyconvertor.app.utils.CurrencyScrape;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final CurrencyScrape currencyScrape;
    private final ModelMapper modelMapper;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository, CurrencyScrape currencyScrape, ModelMapper modelMapper) {
        this.currencyRepository = currencyRepository;
        this.currencyScrape = currencyScrape;
        this.modelMapper = modelMapper;
    }


    @Override
    @Scheduled(cron = "* * * * * ?")
    public void seedCurrencies() throws IOException {
        this.currencyScrape.getCurrencyNameEuroRate();
    }

    @Override
    public List<CurrencyServiceModel> getAllCurrencyServiceModels() {
        return  List.of(this.modelMapper.map(this.currencyRepository.getAll().toArray(),CurrencyServiceModel[].class));
    }


}
