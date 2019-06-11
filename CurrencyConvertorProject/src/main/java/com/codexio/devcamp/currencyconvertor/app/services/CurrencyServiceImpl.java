package com.codexio.devcamp.currencyconvertor.app.services;

import com.codexio.devcamp.currencyconvertor.app.domain.entities.Currency;
import com.codexio.devcamp.currencyconvertor.app.domain.models.CurrencyServiceModel;
import com.codexio.devcamp.currencyconvertor.app.domain.models.HistoricalCurrencyBindingModel;
import com.codexio.devcamp.currencyconvertor.app.domain.models.SeedCurrencyBindingModel;
import com.codexio.devcamp.currencyconvertor.app.repository.CurrencyRepository;
import com.codexio.devcamp.currencyconvertor.app.utils.CurrencyScrape;
import com.codexio.devcamp.currencyconvertor.app.utils.SecondaryCurrencyScrape;
import com.codexio.devcamp.currencyconvertor.app.utils.ValidatorUtil;
import com.codexio.devcamp.currencyconvertor.constants.Constants;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private static final String CURRENCY_RATES_HISTORY_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/files/CurrencyRatesHistory";
    private final CurrencyRepository currencyRepository;
    private final CurrencyScrape currencyScrape;
    private final SecondaryCurrencyScrape secondaryCurrencyScrape;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final Gson gson;


    public CurrencyServiceImpl(CurrencyRepository currencyRepository, CurrencyScrape currencyScrape,
                               SecondaryCurrencyScrape secondaryCurrencyScrape, ModelMapper modelMapper, ValidatorUtil validatorUtil, Gson gson) {
        this.currencyRepository = currencyRepository;
        this.currencyScrape = currencyScrape;
        this.secondaryCurrencyScrape = secondaryCurrencyScrape;
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
        return List.of(this.modelMapper.map(this.currencyRepository.getAll().toArray(), CurrencyServiceModel[].class));
    }

    /**
     * This method is scheduled to write once a day currency rates into file.
     * Cron : 0 0 8 * * ?
     */
    @Override
    @Scheduled(cron = "0 0 8 * * ?")
    public void writeDailyCurrencyRatesIntoFile() throws IOException {
        File file = new File(CURRENCY_RATES_HISTORY_FILE_PATH);
        FileWriter fileWriter = new FileWriter(file, true);
        List<HistoricalCurrencyBindingModel> historicalCurrencyBindingModels = setCurrentDate();
        String json = this.gson.toJson(historicalCurrencyBindingModels);
        fileWriter.write(json);
        fileWriter.close();
    }

    @Override
    public List<HistoricalCurrencyBindingModel> getAllHistoricalCurrencyBindingModels(LocalDate fromDate, LocalDate toDate) throws FileNotFoundException {
        InputStream stream = new FileInputStream(CURRENCY_RATES_HISTORY_FILE_PATH);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        return List.of(this.gson.fromJson(bufferedReader, HistoricalCurrencyBindingModel[].class))
                .stream()
                .filter(historicalCurrencyBindingModel -> {
                    LocalDate date = LocalDate.parse(historicalCurrencyBindingModel.getDate(), DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
                    return date.isAfter(fromDate) && date.isBefore(toDate);
                })
                .collect(Collectors.toList());
    }


    private List<HistoricalCurrencyBindingModel> setCurrentDate() {
        List<HistoricalCurrencyBindingModel> historicalCurrencyBindingModels = List.of(this.modelMapper.map(
                this.currencyRepository.findAll().toArray(), HistoricalCurrencyBindingModel[].class
        ));
        historicalCurrencyBindingModels.forEach(historicalCurrencyBindingModel -> {
            String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
            historicalCurrencyBindingModel.setDate(formattedDate);
        });
        return historicalCurrencyBindingModels;
    }

    private void areAllCurrenciesValid(List<SeedCurrencyBindingModel> rawCurrencies) {
        rawCurrencies.forEach(rawCurrency -> {
            if (!this.validatorUtil.isValid(rawCurrency)) {
                throw new IllegalArgumentException(Constants.SCRAPPED_WRONG_DATA_MESSAGE);
            }
        });
    }
}
