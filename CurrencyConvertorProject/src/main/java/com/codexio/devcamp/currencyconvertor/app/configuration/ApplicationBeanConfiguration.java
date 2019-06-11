package com.codexio.devcamp.currencyconvertor.app.configuration;

import com.codexio.devcamp.currencyconvertor.app.utils.CurrencyScrape;
import com.codexio.devcamp.currencyconvertor.app.utils.SecondaryCurrencyScrape;
import com.codexio.devcamp.currencyconvertor.app.utils.ValidatorUtil;
import com.codexio.devcamp.currencyconvertor.app.utils.ValidatorUtilImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public CurrencyScrape getCurrencyScrape() {
        return new CurrencyScrape();
    }

    @Bean
    public SecondaryCurrencyScrape getSecondaryCurrencyScrape() {
        return new SecondaryCurrencyScrape();
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ValidatorUtil validatorUtil() {
        return new ValidatorUtilImpl();
    }

    @Bean
    public Gson getGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    }

}
