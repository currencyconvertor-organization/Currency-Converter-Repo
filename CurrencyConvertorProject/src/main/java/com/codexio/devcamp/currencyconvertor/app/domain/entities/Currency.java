package com.codexio.devcamp.currencyconvertor.app.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "currencies")
@Entity
public class Currency {
    private Long id;
    private String name;
    private BigDecimal euroRate;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "euro_rate", nullable = false)
    public BigDecimal getEuroRate() {
        return this.euroRate;
    }

    public void setEuroRate(BigDecimal euroRate) {
        this.euroRate = euroRate;
    }
}
