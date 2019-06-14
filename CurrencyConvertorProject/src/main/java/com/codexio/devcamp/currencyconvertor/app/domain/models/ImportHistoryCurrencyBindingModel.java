package com.codexio.devcamp.currencyconvertor.app.domain.models;

import com.codexio.devcamp.currencyconvertor.constants.Constants;
import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public class ImportHistoryCurrencyBindingModel {
    @Expose
    private String rate;
    @Expose
    private String currency;

    public ImportHistoryCurrencyBindingModel() {
    }

    @NotNull(message = Constants.NULL_CURRENCY_RATE_MESSAGE)
    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Pattern(regexp = "^[A-Z]{3}$", message = Constants.INVALID_CURRENCY_CODE_MESSAGE)
    @NotNull(message = Constants.NULL_CURRENCY_CODE_MESSAGE)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
