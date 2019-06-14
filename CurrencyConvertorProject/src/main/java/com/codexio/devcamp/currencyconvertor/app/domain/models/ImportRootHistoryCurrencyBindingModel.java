package com.codexio.devcamp.currencyconvertor.app.domain.models;

import com.codexio.devcamp.currencyconvertor.constants.Constants;
import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ImportRootHistoryCurrencyBindingModel {
    @Expose
    private ImportHistoryCurrencyBindingModel[] Cube;
    @Expose
    private String time;

    public ImportRootHistoryCurrencyBindingModel() {
    }

    public ImportHistoryCurrencyBindingModel[] getCube() {
        return Cube;
    }

    public void setCube(ImportHistoryCurrencyBindingModel[] cube) {
        Cube = cube;
    }

    @Pattern(regexp = "^[\\d]{4}-[\\d]{2}-[\\d]{2}$",message = Constants.INVALID_CURRENCY_DATE_FORMAT_MESSAGE)
    @NotNull(message = Constants.NULL_CURRENCY_DATE_MESSAGE)
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
