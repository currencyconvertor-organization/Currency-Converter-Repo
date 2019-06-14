package com.codexio.devcamp.currencyconvertor.app.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidatorUtilImpl implements ValidatorUtil {
    private Validator validator;

    public ValidatorUtilImpl() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    @Override
    public <T> boolean isValid(T object) {
        return this.validator.validate(object).size()==0;
    }

    @Override
    public <T> Set<ConstraintViolation<T>> violations(T object) {
        return this.validator.validate(object);
    }
}
