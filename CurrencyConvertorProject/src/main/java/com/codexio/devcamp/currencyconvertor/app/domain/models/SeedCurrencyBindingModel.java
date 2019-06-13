package com.codexio.devcamp.currencyconvertor.app.domain.models;

import com.codexio.devcamp.currencyconvertor.app.domain.annotations.bean_validation.ValidUrl;
import com.codexio.devcamp.currencyconvertor.constants.Constants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class SeedCurrencyBindingModel {
    public static final String INVALID_FLAG_URL_MESSAGE = "Flag url is null or response is different than 200!";

    private String code;
    private String name;
    private String countryFlagUrl;
    private BigDecimal euroRate;

    @Pattern(regexp = "^[A-Z]{3}$", message = Constants.INVALID_CURRENCY_CODE_MESSAGE)
    @NotNull(message = Constants.NULL_CURRENCY_CODE_MESSAGE)
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Pattern(regexp = "^\\p{Lu}[\\p{L} '-]+$", message = Constants.INVALID_CURRENCY_NAME_MESSAGE)
    @NotNull(message = Constants.NULL_CURRENCY_NAME_MESSAGE)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ValidUrl(message = INVALID_FLAG_URL_MESSAGE)
    public String getCountryFlagUrl() {
        return countryFlagUrl;
    }

    public void setCountryFlagUrl(String countryFlagUrl) {
        this.countryFlagUrl = countryFlagUrl;
    }

    @Positive(message = Constants.INVALID_CURRENCY_EURO_RATE)
    @NotNull(message = Constants.NULL_CURRENCY_RATE_MESSAGE)
    public BigDecimal getEuroRate() {
        return this.euroRate;
    }

    public void setEuroRate(BigDecimal euroRate) {
        this.euroRate = euroRate;
    }
}
