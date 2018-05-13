package com.challenge.exchangeapi.service;

import com.challenge.exchangeapi.domain.ExchangeResult;
import com.challenge.exchangeapi.repository.ExchangeResultRepository;
import com.challenge.exchangeapi.config.ExchangeApiConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.challenge.exchangeapi.restclient.ExchangeCurrencyApiRestClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    @Autowired
    private ExchangeCurrencyApiRestClient exchangeCurrencyApiRestClient;

    @Autowired
    private ExchangeApiConfigProperties exchangeApiConfigProperties;

    @Autowired
    private ExchangeResultRepository repository;

    @Override
    public void setExchangeCurrencyApiRestClient(ExchangeCurrencyApiRestClient exchangeCurrencyApiRestClient) {
        this.exchangeCurrencyApiRestClient = exchangeCurrencyApiRestClient;
    }

    @Override
    public void setExchangeApiConfigProperties(ExchangeApiConfigProperties exchangeApiConfigProperties) {
        this.exchangeApiConfigProperties = exchangeApiConfigProperties;
    }

    @Override
    public void setExchangeResultRepository(ExchangeResultRepository repository) {
        this.repository = repository;
    }

    @Override
    public Double getLatestRate() {
        Double rate = exchangeCurrencyApiRestClient.getLatestRate().getValue();

        ExchangeResult exchangeResult = newInstanceExchangeResult(rate);
        repository.save(exchangeResult);

        return rate;
    }

    @Override
    public Map<String, Double> getHistoricalRates(String startDate, String endDate) {

        Map<String, Double> historicRates = exchangeCurrencyApiRestClient.getExchangeRateInDateRange(startDate, endDate).getRates();

        if (historicRates != null && !historicRates.entrySet().isEmpty()) {
            for (Map.Entry<String, Double> entry : historicRates.entrySet()) {
                System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
                ExchangeResult exchangeResult = newInstanceExchangeResult(entry.getValue(), entry.getKey(), startDate, endDate);
                repository.save(exchangeResult);
            }
        }

        return historicRates;
    }

    private ExchangeResult newInstanceExchangeResult(Double rate) {

        ExchangeResult exchangeResult = new ExchangeResult();
        exchangeResult.setCreationDate(LocalDateTime.now());
        exchangeResult.setExchangeDate(LocalDate.now());
        exchangeResult.setExchangeType(exchangeApiConfigProperties.getExchangeType());
        exchangeResult.setRate(Double.valueOf(rate));
        return exchangeResult;
    }

    private ExchangeResult newInstanceExchangeResult(Double rate, String exchangeDate, String startDate, String endDate) {

        ExchangeResult exchangeResult = new ExchangeResult();
        exchangeResult.setCreationDate(LocalDateTime.now());
        exchangeResult.setExchangeType(exchangeApiConfigProperties.getExchangeType());
        exchangeResult.setRate(Double.valueOf(rate));
        exchangeResult.setFromDate(LocalDate.parse(startDate));
        exchangeResult.setToDate(LocalDate.parse(endDate));
        exchangeResult.setExchangeDate(LocalDate.parse(exchangeDate));
        exchangeResult.setHistoric(true);

        return exchangeResult;
    }
}
