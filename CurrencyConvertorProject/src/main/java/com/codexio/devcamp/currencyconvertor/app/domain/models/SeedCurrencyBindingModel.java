package com.codexio.devcamp.currencyconvertor.app.domain.models;

import com.codexio.devcamp.currencyconvertor.constants.Constants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class SeedCurrencyBindingModel {
    public static final String INVALID_CURRENCY_EURO_RATE = "Currency rate should be a positive number!";
    public static final String NULL_CURRENCY_RATE_MESSAGE = "Currency rate can't be null!";


    private String code;
    private String name;
    private String countryFlagUrl;
    private BigDecimal euroRate;

    @Pattern(regexp = "^[A-Z]{3}$", message = Constants.INVALID_CURRENCY_CODE_MESSAGE)
    @NotNull(message = Constants.NULL_CURRENCY_NAME_MESSAGE)
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Pattern(regexp = "^[A-Z][A-Za-z\\s]+$", message = Constants.INVALID_CURRENCY_NAME_MESSAGE)
    @NotNull(message = Constants.NULL_CURRENCY_NAME_MESSAGE)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryFlagUrl() {
        return countryFlagUrl;
    }

    public void setCountryFlagUrl(String countryFlagUrl) {
        this.countryFlagUrl = countryFlagUrl;
    }

    @Positive(message = INVALID_CURRENCY_EURO_RATE)
    @NotNull(message = NULL_CURRENCY_RATE_MESSAGE)
    public BigDecimal getEuroRate() {
        return this.euroRate;
    }

    public void setEuroRate(BigDecimal euroRate) {
        this.euroRate = euroRate;
    }
}
