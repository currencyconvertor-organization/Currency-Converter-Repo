package com.codexio.devcamp.currencyconvertor.app.domain.models;

import com.google.gson.annotations.Expose;
import java.math.BigDecimal;

public class HistoricalCurrencyBindingModel {
    @Expose
    private String code;
    @Expose
    private String name;
    @Expose
    private BigDecimal euroRate;
    @Expose
    private String countryFlagUrl;
    @Expose
    private String date;

    public HistoricalCurrencyBindingModel() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getEuroRate() {
        return euroRate;
    }

    public void setEuroRate(BigDecimal euroRate) {
        this.euroRate = euroRate;
    }

    public String getCountryFlagUrl() {
        return countryFlagUrl;
    }

    public void setCountryFlagUrl(String countryFlagUrl) {
        this.countryFlagUrl = countryFlagUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
