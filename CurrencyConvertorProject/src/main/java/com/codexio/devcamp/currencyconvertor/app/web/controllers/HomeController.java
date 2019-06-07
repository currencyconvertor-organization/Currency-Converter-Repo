package com.codexio.devcamp.currencyconvertor.app.web.controllers;

import com.codexio.devcamp.currencyconvertor.app.domain.models.CurrencyViewModel;
import com.codexio.devcamp.currencyconvertor.app.services.CurrencyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private final CurrencyService currencyService;
    private final ModelMapper modelMapper;

    public HomeController(CurrencyService currencyService, ModelMapper modelMapper) {
        this.currencyService = currencyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String getIndexPage() {
        return "index";
    }

    @GetMapping(value = "/fetch/training-programs-all", produces = "application/json")
    @ResponseBody
    public List<CurrencyViewModel> getAll() {
        return List.of(
                this.modelMapper.map(
                        this.currencyService.getAllCurrencyServiceModels().toArray(), CurrencyViewModel[].class
                )
        );
    }
}
