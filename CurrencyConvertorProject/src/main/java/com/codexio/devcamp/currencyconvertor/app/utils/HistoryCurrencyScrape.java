package com.codexio.devcamp.currencyconvertor.app.utils;

import org.json.JSONObject;
import org.json.XML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Gets all currencies from the website
 * https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html
 */
public class HistoryCurrencyScrape {
    private static int PRETTY_PRINT_INDENT_FACTOR = 4;
    private static final String HISTORY_CURRENCIES_URL =
            "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml?bfa4dd8dbc6e2fa7ae7241770eab7330";

    public String getLastThreeMonthsRates() throws IOException {

        Document document = Jsoup.connect(HISTORY_CURRENCIES_URL).get();
        Elements cubes = document.select("cube");
        String xmlCurrencies = cubes.get(0).html();

        JSONObject xmlJSONObj = XML.toJSONObject(xmlCurrencies);
        String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
        jsonPrettyPrintString = jsonPrettyPrintString.substring(jsonPrettyPrintString.indexOf("["), jsonPrettyPrintString.lastIndexOf("]") + 1);

        return jsonPrettyPrintString;
    }
}
