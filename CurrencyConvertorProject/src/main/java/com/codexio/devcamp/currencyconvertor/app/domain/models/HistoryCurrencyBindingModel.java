package com.codexio.devcamp.currencyconvertor.app.domain.models;

import com.google.gson.annotations.Expose;

public class HistoryCurrencyBindingModel {
    @Expose
    private ImportCurrencyBindingModel[] Cube;
    @Expose
    private String time;

    public HistoryCurrencyBindingModel() {
    }

    public ImportCurrencyBindingModel[] getCube() {
        return Cube;
    }

    public void setCube(ImportCurrencyBindingModel[] cube) {
        Cube = cube;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
