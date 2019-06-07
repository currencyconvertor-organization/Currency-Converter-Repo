package com.codexio.devcamp.currencyconvertor;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validation;
import javax.validation.Validator;

@RunWith(SpringRunner.class)
public class ValidationsTest {
    private Validator validator;

    @Before
    public void init() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
