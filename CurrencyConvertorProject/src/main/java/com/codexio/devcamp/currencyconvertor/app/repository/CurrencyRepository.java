package com.codexio.devcamp.currencyconvertor.app.repository;

import com.codexio.devcamp.currencyconvertor.app.domain.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency getCurrenciesByName(String currencyName);

    @Query(value = "SELECT c FROM Currency c")
    List<Currency> getAll();

    boolean existsByCode(String code);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Currency c SET c.euroRate = :newEuroRate WHERE c.code = :currencyCode")
    void updateCurrencyRate(String currencyCode, BigDecimal newEuroRate);
}
