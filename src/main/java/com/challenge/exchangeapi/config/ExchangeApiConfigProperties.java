package com.challenge.exchangeapi.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "exchangeapi")
public class ExchangeApiConfigProperties {

    private String externalApiUrl;
    private String exchangeType;

    public String getExternalApiUrl() {
        return externalApiUrl;
    }

    public void setExternalApiUrl(String externalApiUrl) {
        this.externalApiUrl = externalApiUrl;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }
}
