package com.codexio.devcamp.currencyconvertor.app.domain.models;

import java.math.BigDecimal;

public class CurrencyViewModel {
    private String name;
    private BigDecimal euroRate;

    public CurrencyViewModel() {
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
}
