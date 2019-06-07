package com.codexio.devcamp.currencyconvertor.app.domain.models;

import java.math.BigDecimal;

public class CurrencyServiceModel {
    private Long id;
    private String name;
    private BigDecimal euroRate;

    public CurrencyServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
