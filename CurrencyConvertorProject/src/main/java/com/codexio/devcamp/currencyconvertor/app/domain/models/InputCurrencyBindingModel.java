package com.codexio.devcamp.currencyconvertor.app.domain.models;


import com.codexio.devcamp.currencyconvertor.constants.Constants;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class InputCurrencyBindingModel {
    public static final String INVALID_AMOUNT_MESSAGE = "Amount should be a positive number!";
    public static final String NULL_AMOUNT_MESSAGE = "Amount should be a positive number!";

    private String name;
    private BigDecimal amount;

    @Pattern(regexp = "^[A-Z]{3}$", message = Constants.INVALID_CURRENCY_NAME_MESSAGE)
    @NotNull(message = Constants.NULL_CURRENCY_NAME_MESSAGE)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Positive(message = INVALID_AMOUNT_MESSAGE)
    @NotNull(message = NULL_AMOUNT_MESSAGE)
    public BigDecimal getEuroRate() { //TODO wrong getter method!
        return this.amount;
    }

    public void setEuroRate(BigDecimal euroRate) {
        this.amount = euroRate;
    }
}
