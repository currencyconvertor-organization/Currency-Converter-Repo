package com.codexio.devcamp.currencyconvertor.app.domain.models;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public class CurrencyBindingModel {
    public static final int CURRENCY_NAME_LENGTH = 3;
    public static final String INVALID_CURRENCY_NAME_LENGTH_MESSAGE =
            "Currency name should be " + CURRENCY_NAME_LENGTH + " characters long!";

    private String name;
    private BigDecimal euroRate;

    @Length(min = CURRENCY_NAME_LENGTH, max = CURRENCY_NAME_LENGTH, message = INVALID_CURRENCY_NAME_LENGTH_MESSAGE)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getEuroRate() {
        return this.euroRate;
    }

    public void setEuroRate(BigDecimal euroRate) {
        this.euroRate = euroRate;
    }
}
