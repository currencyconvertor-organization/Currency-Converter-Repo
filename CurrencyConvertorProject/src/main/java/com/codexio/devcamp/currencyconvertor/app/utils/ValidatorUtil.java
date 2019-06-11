package com.codexio.devcamp.currencyconvertor.app.utils;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface ValidatorUtil {
    <T> boolean isValid(T object);
    <T> Set<ConstraintViolation<T>> violations(T object);
}