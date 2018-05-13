package com.challenge.exchangeapi.service;

import com.challenge.exchangeapi.config.ExchangeApiConfigProperties;
import com.challenge.exchangeapi.repository.ExchangeResultRepository;
import com.challenge.exchangeapi.restclient.ExchangeCurrencyApiRestClient;

import java.util.Map;

public interface ExchangeService {

    void setExchangeCurrencyApiRestClient(ExchangeCurrencyApiRestClient exchangeCurrencyApiRestClient);

    void setExchangeApiConfigProperties(ExchangeApiConfigProperties exchangeApiConfigProperties);

    void setExchangeResultRepository(ExchangeResultRepository repository);

    Double getLatestRate();

    Map<String, Double> getHistoricalRates(String startDate, String endDate);
}
