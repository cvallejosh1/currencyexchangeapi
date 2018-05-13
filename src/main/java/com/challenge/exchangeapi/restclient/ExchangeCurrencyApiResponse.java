package com.challenge.exchangeapi.restclient;

import java.util.Map;

/**
 *
 * Response from Fixer API
 */
public class ExchangeCurrencyApiResponse {

    private Double value;
    private Map<String,Double> rates;
    private boolean historical;

    public ExchangeCurrencyApiResponse (Double value) {
        this.value = value;
    }

    public ExchangeCurrencyApiResponse (Map<String, Double> rates, boolean historical) {
        this.rates = rates;
        this.historical = historical;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    public boolean isHistorical() {
        return historical;
    }

    public void setHistorical(boolean historical) {
        this.historical = historical;
    }
}
