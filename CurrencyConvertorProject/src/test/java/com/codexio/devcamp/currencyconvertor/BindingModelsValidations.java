package com.codexio.devcamp.currencyconvertor;

import com.codexio.devcamp.currencyconvertor.app.domain.models.ImportHistoryCurrencyBindingModel;
import com.codexio.devcamp.currencyconvertor.app.domain.models.ImportRootHistoryCurrencyBindingModel;
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
    private static final String CORRECT_CURRENCY_NAME_LATIN_LETTERS = "British Pound";
    private static final String CORRECT_CURRENCY_NAME_CYRILLIC_LETTERS = "Български лев";
    private static final String CORRECT_CURRENCY_NAME_DASH_AND_APOSTROPHE = "Ni-Vanuatu Pa'anga";
    private static final String CORRECT_CURRENCY_NAME_DOUBLE_ENCODING = "Bàlivian Bolíviano";
    private static final String CORRECT_CURRENCY_CODE = "BGN";
    private static final BigDecimal CORRECT_CURRENCY_RATE = new BigDecimal("2.5");
    private static final String CORRECT_FLAG_URL = "https://www.google.com/";

    private static final String CORRECT_CURRENCY_RATE_STRING = "2.50";
    private static final String CORRECT_TIME_STRING = "2019-06-13";

    private Validator validator;
    private SeedCurrencyBindingModel validSeedCurrencyBindingModel;
    private ImportHistoryCurrencyBindingModel validImportHistoryCurrencyBindingModel;
    private ImportRootHistoryCurrencyBindingModel validImportRootHistoryCurrencyBindingModel;

    @Before
    public void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();

        validSeedCurrencyBindingModel = new SeedCurrencyBindingModel();
        validSeedCurrencyBindingModel.setName(CORRECT_CURRENCY_NAME_LATIN_LETTERS);
        validSeedCurrencyBindingModel.setCode(CORRECT_CURRENCY_CODE);
        validSeedCurrencyBindingModel.setEuroRate(CORRECT_CURRENCY_RATE);
        validSeedCurrencyBindingModel.setCountryFlagUrl(CORRECT_FLAG_URL);

        validImportHistoryCurrencyBindingModel = new ImportHistoryCurrencyBindingModel();
        validImportHistoryCurrencyBindingModel.setCurrency(CORRECT_CURRENCY_CODE);
        validImportHistoryCurrencyBindingModel.setRate(CORRECT_CURRENCY_RATE_STRING);

        validImportRootHistoryCurrencyBindingModel = new ImportRootHistoryCurrencyBindingModel();
        validImportRootHistoryCurrencyBindingModel.setTime("2019-06-13");

        validImportRootHistoryCurrencyBindingModel = new ImportRootHistoryCurrencyBindingModel();
        validImportRootHistoryCurrencyBindingModel.setTime(CORRECT_TIME_STRING);
    }

    @Test
    public void whenValidSeedCurrency_noExceptions() {
        Assert.assertTrue(getErrorMessages(this.validSeedCurrencyBindingModel).isEmpty());
    }

    @Test
    public void seedCurrencyBindingModel_whenCyrillicLettersName_noExceptions() {
        this.validSeedCurrencyBindingModel.setName(CORRECT_CURRENCY_NAME_DASH_AND_APOSTROPHE);
        Assert.assertTrue(getErrorMessages(this.validSeedCurrencyBindingModel).isEmpty());
    }

    @Test
    public void seedCurrencyBindingModel_whenDashAndApostropheName_noExceptions() {
        this.validSeedCurrencyBindingModel.setName(CORRECT_CURRENCY_NAME_CYRILLIC_LETTERS);
        Assert.assertTrue(getErrorMessages(this.validSeedCurrencyBindingModel).isEmpty());
    }

    @Test
    public void seedCurrencyBindingModel_whenDoubleEncodingName_noExceptions() {
        this.validSeedCurrencyBindingModel.setName(CORRECT_CURRENCY_NAME_DOUBLE_ENCODING);
        Assert.assertTrue(getErrorMessages(this.validSeedCurrencyBindingModel).isEmpty());
    }

    @Test
    public void seedCurrencyBindingModel_whenNullName_expectNullNameErrorMessage() {
        this.validSeedCurrencyBindingModel.setName(null);

        this.assertOnlyOneErrorMessageThrown(this.validSeedCurrencyBindingModel, Constants.NULL_CURRENCY_NAME_MESSAGE);
    }

    @Test
    public void seedCurrencyBindingModel_whenNullCode_expectNullCodeErrorMessage() {
        this.validSeedCurrencyBindingModel.setCode(null);

        this.assertOnlyOneErrorMessageThrown(this.validSeedCurrencyBindingModel, Constants.NULL_CURRENCY_CODE_MESSAGE);
    }

    @Test
    public void seedCurrencyBindingModel_whenShorterThanExpectedCode_expectInvalidNameErrorMessage() {
        this.validSeedCurrencyBindingModel.setCode("BG");

        this.assertOnlyOneErrorMessageThrown(this.validSeedCurrencyBindingModel, Constants.INVALID_CURRENCY_CODE_MESSAGE);
    }

    @Test
    public void seedCurrencyBindingModel_whenLongerThanExpectedCode_expectInvalidNameErrorMessage() {
        this.validSeedCurrencyBindingModel.setCode("BGNN");

        this.assertOnlyOneErrorMessageThrown(this.validSeedCurrencyBindingModel, Constants.INVALID_CURRENCY_CODE_MESSAGE);
    }

    @Test
    public void seedCurrencyBindingModel_whenCodeIsLowerCaseLetters_expectInvalidNameErrorMessage() {
        this.validSeedCurrencyBindingModel.setCode("bgn");

        this.assertOnlyOneErrorMessageThrown(this.validSeedCurrencyBindingModel, Constants.INVALID_CURRENCY_CODE_MESSAGE);
    }

    @Test
    public void seedCurrencyBindingModel_whenNullCurrencyRate_expectNullCurrencyRateErrorMessage() {
        this.validSeedCurrencyBindingModel.setEuroRate(null);

        this.assertOnlyOneErrorMessageThrown(this.validSeedCurrencyBindingModel, Constants.NULL_CURRENCY_RATE_MESSAGE);
    }

    @Test
    public void seedCurrencyBindingModel_whenZeroRate_expectInvalidRateErrorMessage() {
        this.validSeedCurrencyBindingModel.setEuroRate(new BigDecimal("0"));

        this.assertOnlyOneErrorMessageThrown(this.validSeedCurrencyBindingModel, Constants.INVALID_CURRENCY_EURO_RATE);
    }

    @Test
    public void seedCurrencyBindingModel_whenNegativeEuroRate_expectInvalidRateErrorMessage() {
        this.validSeedCurrencyBindingModel.setEuroRate(new BigDecimal("-1"));

        this.assertOnlyOneErrorMessageThrown(this.validSeedCurrencyBindingModel, Constants.INVALID_CURRENCY_EURO_RATE);
    }

    @Test
    public void seedCurrencyBindingModel_whenNullFlagUrl_expectInvalidFlagUrlMessage() {
        this.validSeedCurrencyBindingModel.setCountryFlagUrl(null);
        Set<String> errors = this.getErrorMessages(this.validSeedCurrencyBindingModel);

        this.assertOnlyOneErrorMessageThrown(this.validSeedCurrencyBindingModel, SeedCurrencyBindingModel.INVALID_FLAG_URL_MESSAGE);
    }

    @Test
    public void seedCurrencyBindingModel_whenInvalidUrlFlagUrl_expectInvalidFlagUrlMessage() {
        this.validSeedCurrencyBindingModel.setCountryFlagUrl("https://www.youtube123.com/");

        this.assertOnlyOneErrorMessageThrown(this.validSeedCurrencyBindingModel, SeedCurrencyBindingModel.INVALID_FLAG_URL_MESSAGE);
    }

    //===================================================================================

    @Test
    public void whenValidImportHistory_noExceptions() {
        Assert.assertTrue(getErrorMessages(this.validImportHistoryCurrencyBindingModel).isEmpty());
    }

    @Test
    public void importHistoryCurrencyBindingModel_whenNullRate_expectNullRateMessage() {
        this.validImportHistoryCurrencyBindingModel.setRate(null);

        this.assertOnlyOneErrorMessageThrown(this.validImportHistoryCurrencyBindingModel,
                Constants.NULL_CURRENCY_RATE_MESSAGE);
    }

    @Test
    public void importHistoryCurrencyBindingModel_whenNullCode_expectNullCodeMessage() {
        this.validImportHistoryCurrencyBindingModel.setCurrency(null);

        this.assertOnlyOneErrorMessageThrown(this.validImportHistoryCurrencyBindingModel,
                Constants.NULL_CURRENCY_CODE_MESSAGE);
    }

    @Test
    public void importHistoryCurrencyBindingModel_whenShortCode_expectInvalidCodeMessage() {
        this.validImportHistoryCurrencyBindingModel.setCurrency("BG");

        this.assertOnlyOneErrorMessageThrown(this.validImportHistoryCurrencyBindingModel,
                Constants.INVALID_CURRENCY_CODE_MESSAGE);
    }

    @Test
    public void importHistoryCurrencyBindingModel_whenLongCode_expectInvalidCodeMessage() {
        this.validImportHistoryCurrencyBindingModel.setCurrency("BGNN");

        this.assertOnlyOneErrorMessageThrown(this.validImportHistoryCurrencyBindingModel,
                Constants.INVALID_CURRENCY_CODE_MESSAGE);
    }

    @Test
    public void importHistoryCurrencyBindingModel_whenLowerCaseCode_expectInvalidCodeMessage() {
        this.validImportHistoryCurrencyBindingModel.setCurrency("bgn");

        this.assertOnlyOneErrorMessageThrown(this.validImportHistoryCurrencyBindingModel,
                Constants.INVALID_CURRENCY_CODE_MESSAGE);
    }

    //==================================================================================

    @Test
    public void whenValidImportRootHistoryCurrencyBindingModel_noExceptions() {
        Assert.assertTrue(getErrorMessages(validImportHistoryCurrencyBindingModel).isEmpty());
    }

    @Test
    public void importRootHistoryCurrencyBindingModel_whenNullTime_expectTimeNullMessage() {
        validImportRootHistoryCurrencyBindingModel.setTime(null);

        assertOnlyOneErrorMessageThrown(validImportRootHistoryCurrencyBindingModel,
                Constants.NULL_CURRENCY_DATE_MESSAGE);
    }

    @Test
    public void importRootHistoryCurrencyBindingModel_whenInvalidTimeFormat_expectTimeNullMessage() {
        validImportRootHistoryCurrencyBindingModel.setTime("2019-6-13");

        assertOnlyOneErrorMessageThrown(validImportRootHistoryCurrencyBindingModel,
                Constants.INVALID_CURRENCY_DATE_FORMAT_MESSAGE);
    }

    //==================================================================================

    private Set<String> getErrorMessages(Object bindingModel) {
        return this.validator.validate(bindingModel).stream().map(e -> e.getMessage()).collect(Collectors.toSet());
    }

    private void assertOnlyOneErrorThrown(Object bindingModel) {
        Assert.assertEquals("Expected only 1 error!", 1, this.getErrorMessages(bindingModel).size());
    }

    private void assertCorrectMessageThrown(Object bindingModel, String expectedErrorMessage) {
        Assert.assertTrue("Wrong exception message thrown!", this.getErrorMessages(bindingModel).contains(expectedErrorMessage));
    }

    private void assertOnlyOneErrorMessageThrown(Object bindingModel, String expectedErrorMessage) {
        this.assertOnlyOneErrorThrown(bindingModel);
        this.assertCorrectMessageThrown(bindingModel, expectedErrorMessage);
    }
}
