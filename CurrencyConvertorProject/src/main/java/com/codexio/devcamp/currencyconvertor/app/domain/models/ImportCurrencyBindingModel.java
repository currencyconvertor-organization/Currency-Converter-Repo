package com.codexio.devcamp.currencyconvertor.app.domain.models;

import com.google.gson.annotations.Expose;

public class ImportCurrencyBindingModel {
    @Expose
    private String rate;
    @Expose
    private String currency;

    public ImportCurrencyBindingModel() {
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
