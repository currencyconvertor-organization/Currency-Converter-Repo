package com.codexio.devcamp.currencyconvertor.app.domain.annotations.bean_validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotated <i>String</i> must not be null and the HTTP response needs to return 200 OK
 */
@Retention(RetentionPolicy.RUNTIME) //when to be checked
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = ValidUrlImpl.class) //location of validation logic
public @interface ValidUrl {
    String message() default "Url is null or HTTP Response is different than 200.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
