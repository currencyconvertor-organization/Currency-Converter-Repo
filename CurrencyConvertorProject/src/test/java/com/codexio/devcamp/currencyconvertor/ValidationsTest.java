package com.codexio.devcamp.currencyconvertor;

import com.codexio.devcamp.currencyconvertor.app.domain.models.InputCurrencyBindingModel;
import com.codexio.devcamp.currencyconvertor.app.domain.models.SeedCurrencyBindingModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Set;

@RunWith(SpringRunner.class)
public class ValidationsTest {
    private Validator validator;

    @Before
    public void init() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void seedTest1() { //TODO finish up
        SeedCurrencyBindingModel input = new SeedCurrencyBindingModel();
        input.setName("USD123");
        input.setEuroRate(new BigDecimal("100"));

        Set<ConstraintViolation<SeedCurrencyBindingModel>> errors = validator.validate(input);

        errors.forEach(e -> System.out.println(e.getMessage()));
    }
}
