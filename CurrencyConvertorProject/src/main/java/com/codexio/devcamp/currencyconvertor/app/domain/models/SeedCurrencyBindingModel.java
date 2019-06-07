package com.codexio.devcamp.currencyconvertor.app.domain.models;

import com.codexio.devcamp.currencyconvertor.constants.Constants;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class SeedCurrencyBindingModel {
    public static final String INVALID_CURRENCY_EURO_RATE = "Euro rate should be a positive number!";

    private String name;
    private BigDecimal euroRate;

    @Length(min = Constants.CURRENCY_NAME_LENGTH, max = Constants.CURRENCY_NAME_LENGTH, message = Constants.INVALID_CURRENCY_NAME_LENGTH_MESSAGE)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Positive(message = INVALID_CURRENCY_EURO_RATE)
    public BigDecimal getEuroRate() {
        return this.euroRate;
    }

    public void setEuroRate(BigDecimal euroRate) {
        this.euroRate = euroRate;
    }
}
