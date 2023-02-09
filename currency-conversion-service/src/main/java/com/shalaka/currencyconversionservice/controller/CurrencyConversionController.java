package com.shalaka.currencyconversionservice.controller;

import com.shalaka.currencyconversionservice.model.CurrencyConversion;
import com.shalaka.currencyconversionservice.model.CurrencyExchange;
import com.shalaka.currencyconversionservice.proxy.CurrencyExchangeProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
@RequestMapping("/currency-conversion")
public class CurrencyConversionController {

    private Logger logger
            = LoggerFactory.getLogger(CurrencyConversionController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyExchangeProxy proxy;

    @GetMapping("/from/{from}/to/{to}/quantity/{quantity}")
    @CircuitBreaker(name="default", fallbackMethod = "fallbackCalculateCurrencyConversion")
    public CurrencyConversion calculateCurrencyConversion(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity) {
        logger.info(" calculateCurrencyConversion invoked {} ",from);
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from",from);
        uriVariables.put("to",to);

        CurrencyExchange currencyExchangeResponse
                = restTemplate.getForObject("http://CURRENCY-EXCHANGE-SERVICE/currency-exchange/from/{from}/to/{to}",
                CurrencyExchange.class, uriVariables);


        return new CurrencyConversion(currencyExchangeResponse.getId(),
                from, to, quantity,
                currencyExchangeResponse.getConversionMultiple(),
                quantity.multiply(currencyExchangeResponse.getConversionMultiple()),
                currencyExchangeResponse.getEnvironment());

    }

    @GetMapping("/feign/from/{from}/to/{to}/quantity/{quantity}")
    @Retry(name="calculate-currency", fallbackMethod = "fallbackCalculateCurrencyConversion")
    public CurrencyConversion calculateCurrencyConversionFeign(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity) {
        logger.info("CalculateCurrencyConversionFeign invoked ");

        CurrencyExchange currencyExchange = proxy.retrieveExchangeValue(from, to);

        return new CurrencyConversion(currencyExchange.getId(),
                from, to, quantity,
                currencyExchange.getConversionMultiple(),
                quantity.multiply(currencyExchange.getConversionMultiple()),
                currencyExchange.getEnvironment() + " " + "feign");

    }

    public CurrencyConversion fallbackCalculateCurrencyConversion(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity, Exception e) {
        return new CurrencyConversion(0l,
                from, to, quantity,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                 " Unknown " + "fallback");
    }
}
