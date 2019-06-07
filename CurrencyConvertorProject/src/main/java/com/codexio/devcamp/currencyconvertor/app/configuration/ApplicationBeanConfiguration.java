package com.codexio.devcamp.currencyconvertor.app.configuration;

import com.codexio.devcamp.currencyconvertor.app.utils.CurrencyScrape;
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
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }

}
