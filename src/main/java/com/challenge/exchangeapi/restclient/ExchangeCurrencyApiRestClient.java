package com.challenge.exchangeapi.restclient;

/**
 * ExchangeCurrencyApiRestClient defines all operations allowed from external api to get
 * currency exchange rate
 */
public interface ExchangeCurrencyApiRestClient {

    ExchangeCurrencyApiResponse getLatestRate();

    ExchangeCurrencyApiResponse getExchangeRateInDateRange(String startDate, String endDate);


}
