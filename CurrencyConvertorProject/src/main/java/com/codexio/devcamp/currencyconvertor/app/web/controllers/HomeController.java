package com.codexio.devcamp.currencyconvertor.app.web.controllers;
import com.codexio.devcamp.currencyconvertor.app.domain.models.CurrencyTableViewModel;
import com.codexio.devcamp.currencyconvertor.app.domain.models.CurrencyViewModel;
import com.codexio.devcamp.currencyconvertor.app.domain.models.HistoryCurrencyBindingModel;
import com.codexio.devcamp.currencyconvertor.app.services.CurrencyService;
import com.codexio.devcamp.currencyconvertor.constants.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/")
@CrossOrigin(origins = Constants.CLIENT_URL)
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

    @GetMapping(value = "/fetch/.....", produces = "application/json")
    @ResponseBody
    public List<CurrencyViewModel> getAll() {
        return   List.of(
                this.modelMapper.map(
                        this.currencyService.getAllCurrencyServiceModels().toArray(), CurrencyViewModel[].class
                )
        );
    }

    /**
     * @return List of all currencies with code, name, euro rate, image url
     * in JSON format
     */
    @GetMapping(value = "/fetch/currencies-table", produces = "application/json")
    @ResponseBody
    public List<CurrencyTableViewModel> getAllTableCurrencies() {
        return   List.of(
                this.modelMapper.map(
                        this.currencyService.getAllCurrencyServiceModels().toArray(), CurrencyTableViewModel[].class
                )
        );
    }

    /**
     * @return List of historical currencies in determine period of time
     * in JSON format
     */
    @GetMapping(value = "/fetch/currencies-history", produces = "application/json")
    @ResponseBody
    public List<HistoryCurrencyBindingModel> getLastThreeMonthsRates() throws IOException {
        return this.currencyService.getLastThreeMonthRateBindingModels();
    }
}
