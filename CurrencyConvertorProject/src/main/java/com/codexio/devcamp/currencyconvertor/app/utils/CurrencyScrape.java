package com.codexio.devcamp.currencyconvertor.app.utils;

import com.codexio.devcamp.currencyconvertor.app.domain.models.SeedCurrencyBindingModel;
import com.codexio.devcamp.currencyconvertor.constants.Constants;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


/**
 * Gets all currencies from the website
 * https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html
 */
public class CurrencyScrape implements CurrencyReader {
    private static final String COUNTRY_FLAG_URL = "https://www.ecb.europa.eu/shared/img/flags/%s.gif";

    @Override
    public List<SeedCurrencyBindingModel> getCurrencyNameEuroRate() throws IOException {
        List<SeedCurrencyBindingModel> currencies = new LinkedList<>();
        Elements tableRows = Jsoup.connect(Constants.CURRENCY_CONVERTER_URL)
                .get()
                .select("tbody")
                .select("tr");

        tableRows.forEach(tr -> {
            SeedCurrencyBindingModel seedCurrencyBindingModel = new SeedCurrencyBindingModel();
            String currencyCode = tr.select("td.currency").select("a").html();
            String currencyName =  tr.select("td.alignLeft").select("a").html();
            String countryFlagUrl = String.format(COUNTRY_FLAG_URL, currencyCode);
            String rate = tr.select("span.rate").html();

            SecondaryCurrencyScrape.setSeedCurrencyBindingModel(currencies, seedCurrencyBindingModel, currencyCode, currencyName, countryFlagUrl, rate);
        });
        return currencies;
    }
}
