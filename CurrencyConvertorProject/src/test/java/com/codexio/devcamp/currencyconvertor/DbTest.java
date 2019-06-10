package com.codexio.devcamp.currencyconvertor;

import com.codexio.devcamp.currencyconvertor.app.domain.entities.Currency;
import com.codexio.devcamp.currencyconvertor.app.repository.CurrencyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest //slows down tests, but allows Spring features such as Dependency Injection
@RunWith(SpringRunner.class)
public class DbTest {
    @Autowired
    CurrencyRepository currencyRepository;

    @Test
    public void printAll() {
        System.out.println("Hello!");
        List<Currency> allCurrencies = currencyRepository.getAll();

        allCurrencies.forEach(c -> {
            System.out.printf("Name: %s | Euro Rate: %s%n", c.getCode(), c.getEuroRate());
        });
    }
}
