package com.codexio.devcamp.currencyconvertor;

import com.codexio.devcamp.currencyconvertor.app.domain.models.SeedCurrencyBindingModel;
import com.codexio.devcamp.currencyconvertor.app.utils.*;
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
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
public class CurrencyScrapeTests {
    private CurrencyReader primaryCurrencyScrape;
    private CurrencyReader secondaryCurrencyScrape;
    private ValidatorUtil validator;
    private Validator myValidator;

    @Before
    public void init() {
        this.primaryCurrencyScrape = new CurrencyScrape();
        this.secondaryCurrencyScrape = new SecondaryCurrencyScrape();
        this.validator = new ValidatorUtilImpl();
        this.myValidator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void primaryScrape_whenGetCurrencyNameEuroRate_areValidCurrencies() throws IOException {
        List<SeedCurrencyBindingModel> scrapingResult = this.primaryCurrencyScrape.getCurrencyNameEuroRate();

        Assert.assertNotNull(scrapingResult);
        Assert.assertTrue(!scrapingResult.isEmpty());
        scrapingResult.forEach(e -> Assert.assertTrue(this.buildErrorsString(this.validator.violations(e)), this.validator.isValid(e)));
    }

    @Test
    public void secondaryScrape_whenGetCurrencyNameEuroRate_areValidCurrencies() throws IOException {
        List<SeedCurrencyBindingModel> scrapingResult = this.secondaryCurrencyScrape.getCurrencyNameEuroRate();

        Assert.assertNotNull(scrapingResult);
        Assert.assertTrue(!scrapingResult.isEmpty());
        scrapingResult.forEach(e -> Assert.assertTrue(this.buildErrorsString(this.validator.violations(e)), this.validator.isValid(e)));
    }

    /**
     *
     * @param violations
     * @return String in format "<problematic value> --> <raised exception message>"
     */
    private String buildErrorsString(Set<ConstraintViolation<Object>> violations) {
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<Object> violation : violations) {
            sb.append(violation.getInvalidValue()).append(" --> ").append(violation.getMessage()).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
