package com.codexio.devcamp.currencyconvertor.app.utils;

import com.codexio.devcamp.currencyconvertor.app.domain.models.SeedCurrencyBindingModel;
import com.codexio.devcamp.currencyconvertor.constants.Constants;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;


/**
 * Gets all currencies from the website
 * https://www.xe.com/currencytables/?from=EUR
 */
public class SecondaryCurrencyScrape implements CurrencyReader {
    private static final String COUNTRY_FLAG_URL = "https://www.xe.com/themes/xe/images/flags/big/%s.png";

    @Override
    public List<SeedCurrencyBindingModel> getCurrencyNameEuroRate() throws IOException {
        List<SeedCurrencyBindingModel> currencies = new LinkedList<>();
        Elements tableRows = Jsoup.connect(Constants.SECONDARY_CURRENCY_CONVERTER_URL)
                .get()
                .select("tbody")
                .get(0)
                .select("tr");

        tableRows.forEach(tr -> {
            SeedCurrencyBindingModel seedCurrencyBindingModel = new SeedCurrencyBindingModel();
            String currencyCode = tr.child(0).select("a").html();
            String currencyName = tr.child(1).html();
            String countryFlagUrl = String.format(COUNTRY_FLAG_URL, currencyCode.toLowerCase());
            String rate = tr.child(2).html();

            setSeedCurrencyBindingModel(currencies, seedCurrencyBindingModel, currencyCode, currencyName, countryFlagUrl, rate);
        });
        return currencies;
    }

    static void setSeedCurrencyBindingModel(List<SeedCurrencyBindingModel> currencies, SeedCurrencyBindingModel seedCurrencyBindingModel,
                                            String currencyCode, String currencyName, String countryFlagUrl, String rate) {
        seedCurrencyBindingModel.setCode(currencyCode);
        seedCurrencyBindingModel.setName(currencyName);
        seedCurrencyBindingModel.setCountryFlagUrl(countryFlagUrl);
        seedCurrencyBindingModel.setEuroRate(new BigDecimal(rate));
        currencies.add(seedCurrencyBindingModel);
    }
}