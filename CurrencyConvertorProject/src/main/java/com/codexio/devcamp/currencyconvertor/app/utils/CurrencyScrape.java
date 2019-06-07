package com.codexio.devcamp.currencyconvertor.app.utils;

import com.codexio.devcamp.currencyconvertor.constants.Constants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Gets all currencies from the website
 * https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html
 */
public class CurrencyScrape implements CurrencyReader {
    @Override
    public Map<String, String> getCurrencyNameEuroRate() throws IOException {
        Map<String, String> currencies = new HashMap<>();
        Document doc = Jsoup.connect(Constants.CURRENCY_CONVERTER_URL).get();
        Elements table = doc.select("tbody");
        Elements tableRows = table.select("tr");
        tableRows.forEach(tr -> {
            Elements td = tr.select("td.currency");
            String currencyName = td.select("a").html();
            String rate = tr.select("span.rate").html();
            currencies.putIfAbsent(currencyName, rate);
        });
        return currencies;
    }
}
