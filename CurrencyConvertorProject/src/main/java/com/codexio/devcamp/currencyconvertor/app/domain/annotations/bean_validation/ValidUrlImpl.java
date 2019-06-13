package com.codexio.devcamp.currencyconvertor.app.domain.annotations.bean_validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ValidUrlImpl implements ConstraintValidator<ValidUrl, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            URL url = new URL(value);

            // We want to check the current URL
            HttpURLConnection.setFollowRedirects(false);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // We don't need to get data
            httpURLConnection.setRequestMethod("HEAD");

            // Some websites don't like programmatic access so pretend to be a browser
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
            int responseCode = httpURLConnection.getResponseCode();

            // We only accept response code 200
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            return false;
        }
    }
}
