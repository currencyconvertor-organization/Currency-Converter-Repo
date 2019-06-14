package com.codexio.devcamp.currencyconvertor.app.services;

import com.codexio.devcamp.currencyconvertor.app.domain.entities.Currency;
import com.codexio.devcamp.currencyconvertor.app.domain.models.CurrencyServiceModel;
import com.codexio.devcamp.currencyconvertor.app.domain.models.ImportRootHistoryCurrencyBindingModel;
import com.codexio.devcamp.currencyconvertor.app.domain.models.SeedCurrencyBindingModel;
import com.codexio.devcamp.currencyconvertor.app.repository.CurrencyRepository;
import com.codexio.devcamp.currencyconvertor.app.utils.CurrencyScrape;
import com.codexio.devcamp.currencyconvertor.app.utils.HistoryCurrencyScrape;
import com.codexio.devcamp.currencyconvertor.app.utils.SecondaryCurrencyScrape;
import com.codexio.devcamp.currencyconvertor.app.utils.ValidatorUtil;
import com.codexio.devcamp.currencyconvertor.constants.Constants;
import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;
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

    @Override
    public List<CurrencyServiceModel> getAllCurrencyServiceModels() {
        return List.of(
                this.modelMapper.map(
                        this.currencyRepository.getAll().toArray(), CurrencyServiceModel[].class
                )
        );
    }

    @Override
    public List<ImportRootHistoryCurrencyBindingModel> getLastThreeMonthRateBindingModels() throws IOException {
        String jsonCurrencyHistory = this.historyCurrencyScrape.getLastThreeMonthsRates();
        List<ImportRootHistoryCurrencyBindingModel> importRootHistoryCurrencyBindingModels =
                List.of(this.gson.fromJson(jsonCurrencyHistory, ImportRootHistoryCurrencyBindingModel[].class));
        areAllCurrenciesValid(importRootHistoryCurrencyBindingModels);
        StringBuilder sb = new StringBuilder();
        stringFormatCurrencies(importRootHistoryCurrencyBindingModels, sb);
        try {
            Document document = new Document();
            OutputStream file = new FileOutputStream(new File(Constants.HISTORY_CURRENCIES_FILE_PATH + Constants.HISTORY_CURRENCIES_FILE_NAME));
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
            document.add(new Paragraph(sb.toString()));
            document.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return importRootHistoryCurrencyBindingModels;
    }

    /**
     * This method is scheduled to seeds or update database of every hour.
     * Cron : 0 0 0/1 1/1 * *
     */
    @Scheduled(cron = "0 0 0/1 1/1 * *")
    private void seedCurrencies() {
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
    private void stringFormatCurrencies(List<ImportRootHistoryCurrencyBindingModel> rootImportModels, StringBuilder sb) {
        rootImportModels.forEach(rootModel->{
            sb.append(rootModel.getTime())
                    .append(System.lineSeparator());
            Arrays.stream(rootModel.getCube())
                    .forEach(model->{
                        sb.append(model.getCurrency())
                                .append(" - ").append(model.getRate())
                                .append(System.lineSeparator());
                    });
            sb.append(System.lineSeparator());
        });
    }

    private <T> void areAllCurrenciesValid(List<T> rawCurrencies) {
        rawCurrencies.forEach(rawCurrency -> {
            if (!this.validatorUtil.isValid(rawCurrency)) {
                throw new IllegalArgumentException(Constants.SCRAPPED_WRONG_DATA_MESSAGE);
            }
        });
    }
}
