package com.codexio.devcamp.currencyconvertor.app.domain.models;

import java.math.BigDecimal;

public class CurrencyServiceModel {
    private Long id;
    private String code;
    private String name;
    private BigDecimal euroRate;
    private String countryFlagUrl;

    public CurrencyServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
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
        return this.countryFlagUrl;
    }

    public void setCountryFlagUrl(String countryFlagUrl) {
        this.countryFlagUrl = countryFlagUrl;
    }
}
