package com.codexio.devcamp.currencyconvertor.app.web.controllers;

import com.codexio.devcamp.currencyconvertor.app.domain.models.CurrencyTableViewModel;
import com.codexio.devcamp.currencyconvertor.app.domain.models.CurrencyViewModel;
import com.codexio.devcamp.currencyconvertor.app.domain.models.ImportRootHistoryCurrencyBindingModel;
import com.codexio.devcamp.currencyconvertor.app.services.CurrencyService;
import com.codexio.devcamp.currencyconvertor.constants.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/")
@CrossOrigin(origins = Constants.CLIENT_URL)
public class HomeController {
    private final static String ATTACHMENT_FILENAME = "attachment; filename=";
    private final static String CONTENT_DISPOSITION = "Content-Disposition";
    private final static String APPLICATION_PDF = "application/pdf";
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
        return List.of(
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
        return List.of(
                this.modelMapper.map(
                        this.currencyService.getAllCurrencyServiceModels().toArray(), CurrencyTableViewModel[].class
                )
        );
    }

    /**
     * @return List of historical currencies for last three months
     * in JSON format
     */
    @GetMapping(value = "/fetch/currencies-history", produces = "application/json")
    @ResponseBody
    public List<ImportRootHistoryCurrencyBindingModel> getLastThreeMonthsRates() throws IOException {
        return this.currencyService.getLastThreeMonthRateBindingModels();
    }


    @GetMapping("/download-currencies")
    public void downloadPDFResource(HttpServletResponse response) {
        String dataDirectory = Constants.HISTORY_CURRENCIES_FILE_PATH;
        Path file = Paths.get(dataDirectory, Constants.HISTORY_CURRENCIES_FILE_NAME);
        if (Files.exists(file)) {
            response.setContentType(APPLICATION_PDF);
            response.addHeader(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + Constants.HISTORY_CURRENCIES_FILE_NAME);
            try {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
