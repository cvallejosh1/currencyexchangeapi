package com.challenge.exchangeapi.restclient;

import com.challenge.exchangeapi.config.ExchangeApiConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class FreeCurrencyApiRestClient implements  ExchangeCurrencyApiRestClient {

    private final RestTemplate restTemplate;

    @Autowired
    private ExchangeApiConfigProperties exchangeApiConfigProperties;

    public FreeCurrencyApiRestClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    @Override
    public ExchangeCurrencyApiResponse getLatestRate() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        String exchangeType = exchangeApiConfigProperties.getExchangeType();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(exchangeApiConfigProperties.getExternalApiUrl())
                .queryParam("q", exchangeType)
                .queryParam("compact", "ultra");

        Map<String,Double> responseBody = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                new HttpEntity<>(headers) ,
                Map.class).getBody();

        return new ExchangeCurrencyApiResponse(responseBody.get(exchangeType));
    }

    @Override
    public ExchangeCurrencyApiResponse getExchangeRateInDateRange(String startDate, String endDate) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        String exchangeType = exchangeApiConfigProperties.getExchangeType();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(exchangeApiConfigProperties.getExternalApiUrl())
                .queryParam("q", exchangeType
                )
                .queryParam("compact", "ultra")
                .queryParam("date", startDate)
                .queryParam("endDate", endDate);

        Map<String, Map<String,Double>> body = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Map.class).getBody();

        return new ExchangeCurrencyApiResponse(body.get(exchangeType), true);
    }
}
