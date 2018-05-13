package com.challenge.exchangeapi.controller;

import com.challenge.exchangeapi.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/exchangeapi")
public class ExchangeApiController {

    @Autowired
    private ExchangeService exchangeService;

    @GetMapping(value = "/rate/latest", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Double> getLatestRate() {
        Double rate = exchangeService.getLatestRate();
        if (rate == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(rate, HttpStatus.OK);
    }

    @GetMapping(value = "/rate/historic", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String,Double>> getHistoricalRate(@RequestParam(name = "startDate") String startDate,
                                                                @RequestParam(name = "endDate") String endDate) {
        Map<String,Double> historicRates = exchangeService.getHistoricalRates(startDate, endDate);
        if (CollectionUtils.isEmpty(historicRates)) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(historicRates, HttpStatus.OK);
    }


}
