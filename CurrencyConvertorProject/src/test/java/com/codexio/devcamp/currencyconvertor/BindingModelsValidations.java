package com.codexio.devcamp.currencyconvertor;

import com.codexio.devcamp.currencyconvertor.app.domain.models.SeedCurrencyBindingModel;
import com.codexio.devcamp.currencyconvertor.constants.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
public class BindingModelsValidations {
    private static final String CORRECT_CURRENCY_NAME = "BGN";
    private static final BigDecimal CORRECT_CURRENCY_RATE = new BigDecimal("2.5");

    private Validator validator;
    SeedCurrencyBindingModel seedCurrencyBindingModel;

    @Before
    public void init() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.seedCurrencyBindingModel = new SeedCurrencyBindingModel();

        this.seedCurrencyBindingModel.setCode(CORRECT_CURRENCY_NAME);
        this.seedCurrencyBindingModel.setEuroRate(CORRECT_CURRENCY_RATE);
    }

    @Test
    public void seedCurrencyBindingModel_whenValidSeedCurrency_noExceptions() {
        Assert.assertTrue(getErrorMessages(this.seedCurrencyBindingModel).isEmpty());
    }

    @Test
    public void seedCurrencyBindingModel_whenNullName_expectNullNameErrorMessage() {
        this.seedCurrencyBindingModel.setCode(null);

        this.assertOnlyOneErrorMessageThrown(this.seedCurrencyBindingModel, Constants.NULL_CURRENCY_NAME_MESSAGE);
    }

    @Test
    public void seedCurrencyBindingModel_whenShorterThanExpectedName_expectInvalidNameErrorMessage() {
        this.seedCurrencyBindingModel.setCode("BG");

        this.assertOnlyOneErrorMessageThrown(this.seedCurrencyBindingModel, Constants.INVALID_CURRENCY_NAME_MESSAGE);
    }

    @Test
    public void seedCurrencyBindingModel_whenLongerThanExpectedName_expectInvalidNameErrorMessage() {
        this.seedCurrencyBindingModel.setCode("BGNN");

        this.assertOnlyOneErrorMessageThrown(this.seedCurrencyBindingModel, Constants.INVALID_CURRENCY_NAME_MESSAGE);
    }

    @Test
    public void seedCurrencyBindingModel_whenNameIsLowerCaseLetters_expectInvalidNameErrorMessage() {
        this.seedCurrencyBindingModel.setCode("bgn");

        this.assertOnlyOneErrorMessageThrown(this.seedCurrencyBindingModel, Constants.INVALID_CURRENCY_NAME_MESSAGE);
    }

    @Test
    public void seedCurrencyBindingModel_whenNullCurrencyRate_expectNullCurrencyRateErrorMessage() {
        this.seedCurrencyBindingModel.setEuroRate(null);

        this.assertOnlyOneErrorMessageThrown(this.seedCurrencyBindingModel, SeedCurrencyBindingModel.NULL_CURRENCY_RATE_MESSAGE);
    }

    @Test
    public void seedCurrencyBindingModel_whenZeroRate_expectInvalidRateErrorMessage() {
        this.seedCurrencyBindingModel.setEuroRate(new BigDecimal("0"));

        this.assertOnlyOneErrorMessageThrown(this.seedCurrencyBindingModel, SeedCurrencyBindingModel.INVALID_CURRENCY_EURO_RATE);
    }

    @Test
    public void seedCurrencyBindingModel_whenNegativeEuroRate_expectInvalidRateErrorMessage() {
        this.seedCurrencyBindingModel.setEuroRate(new BigDecimal("-1"));

        this.assertOnlyOneErrorMessageThrown(this.seedCurrencyBindingModel, SeedCurrencyBindingModel.INVALID_CURRENCY_EURO_RATE);
    }

    //TODO InputCurrencyBindingModel tests


    private Set<String> getErrorMessages(Object bindingModel) {
        return this.validator.validate(bindingModel).stream().map(e -> e.getMessage()).collect(Collectors.toSet());
    }

    private void assertOnlyOneErrorThrown(Object bindingModel) {
        Assert.assertEquals(1, this.getErrorMessages(bindingModel).size());
    }

    private void assertCorrectMessageThrown(Object bindingModel, String expectedErrorMessage) {
        Assert.assertTrue(this.getErrorMessages(bindingModel).contains(expectedErrorMessage));
    }

    private void assertOnlyOneErrorMessageThrown(Object bindingModel, String expectedErrorMessage) {
        this.assertOnlyOneErrorThrown(bindingModel);
        this.assertCorrectMessageThrown(bindingModel, expectedErrorMessage);
    }
}
