package com.codexio.devcamp.currencyconvertor;

import com.codexio.devcamp.currencyconvertor.app.domain.models.SeedCurrencyBindingModel;
import com.codexio.devcamp.currencyconvertor.app.utils.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
public class CurrencyScrapeTests {
    private CurrencyReader primaryCurrencyScrape;
    private CurrencyReader secondaryCurrencyScrape;
    private ValidatorUtil validator;
    private Validator myValidator;
    private HistoryCurrencyScrape historyCurrencyScrape;

    @Before
    public void init() {
        this.primaryCurrencyScrape = new CurrencyScrape();
        this.secondaryCurrencyScrape = new SecondaryCurrencyScrape();
        this.validator = new ValidatorUtilImpl();
        this.myValidator = Validation.buildDefaultValidatorFactory().getValidator();
        this.historyCurrencyScrape = new HistoryCurrencyScrape();
    }

    @Test
    public void primaryScrape_whenGetCurrencyNameEuroRate_areValidCurrencies() throws IOException {
        List<SeedCurrencyBindingModel> scrapingResult = this.primaryCurrencyScrape.getCurrencyNameEuroRate();

        Assert.assertNotNull(scrapingResult);
        Assert.assertTrue(!scrapingResult.isEmpty());

        String allErrors = this.buildAllErrorsString(scrapingResult);
        Assert.assertTrue(allErrors, allErrors.equals(""));
    }

    @Test
    public void secondaryScrape_whenGetCurrencyNameEuroRate_areValidCurrencies() throws IOException {
        List<SeedCurrencyBindingModel> scrapingResult = this.secondaryCurrencyScrape.getCurrencyNameEuroRate();

        Assert.assertNotNull(scrapingResult);
        Assert.assertTrue(!scrapingResult.isEmpty());

        String allErrors = this.buildAllErrorsString(scrapingResult);
        Assert.assertTrue(allErrors, allErrors.equals(""));
    }

    private String buildAllErrorsString(List<SeedCurrencyBindingModel> seedCurrencyBindingModels) {
        StringBuilder sb = new StringBuilder();
        for (SeedCurrencyBindingModel currentBindingModel : seedCurrencyBindingModels) {
            Set<ConstraintViolation<Object>> violations = this.validator.violations(currentBindingModel);

            if (violations.isEmpty())
                continue;

            sb.append(this.buildErrorsString(violations)).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    /**
     * @return String in format <br>"problematic value --> raised exception message"
     */
    private String buildErrorsString(Set<ConstraintViolation<Object>> violations) {
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<Object> violation : violations) {
            sb.append(violation.getInvalidValue()).append(" --> ").append(violation.getMessage()).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
