package com.challenge.exchangeapi.service;

import com.challenge.exchangeapi.config.ExchangeApiConfigProperties;
import com.challenge.exchangeapi.domain.ExchangeResult;
import com.challenge.exchangeapi.repository.ExchangeResultRepository;
import com.challenge.exchangeapi.restclient.ExchangeCurrencyApiResponse;
import com.challenge.exchangeapi.restclient.ExchangeCurrencyApiRestClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExchangeServiceTests {

    @MockBean
    private ExchangeCurrencyApiRestClient exchangeCurrencyApiRestClient;

    @MockBean
    private ExchangeApiConfigProperties exchangeApiConfigProperties;

    @MockBean
    private ExchangeResultRepository repository;

    private ExchangeService exchangeService;

    @Before
    public void setup() {

        Map<String,Double> mockHistoricRates = new HashMap<>();
        mockHistoricRates.put("2018-02-01", 1.251405);
        mockHistoricRates.put("2018-02-02", 1.24641);
        mockHistoricRates.put("2018-02-03", 1.24641);
        mockHistoricRates.put("2018-02-04", 1.245015);
        mockHistoricRates.put("2018-02-05", 1.237006);
        mockHistoricRates.put("2018-02-06", 1.238391);

        ExchangeResult exchangeResult = new ExchangeResult();
        exchangeResult.setRate(1.19488d);

        exchangeService = Mockito.mock(ExchangeServiceImpl.class, Mockito.CALLS_REAL_METHODS);
        exchangeService.setExchangeCurrencyApiRestClient(exchangeCurrencyApiRestClient);
        exchangeService.setExchangeApiConfigProperties(exchangeApiConfigProperties);
        exchangeService.setExchangeResultRepository(repository);

        Mockito.when(exchangeCurrencyApiRestClient.getLatestRate()).thenReturn(new ExchangeCurrencyApiResponse(1.19488d));
        Mockito.when(exchangeCurrencyApiRestClient.getExchangeRateInDateRange(Matchers.anyString(), Matchers.anyString()))
                .thenReturn(new ExchangeCurrencyApiResponse(mockHistoricRates, true));
        Mockito.when(exchangeApiConfigProperties.getExchangeType()).thenReturn("EUR_USD");
        Mockito.when(repository.save(Matchers.any(ExchangeResult.class))).thenReturn(exchangeResult);

    }

    @Test
    public void testGetLatestRate() {

        assertThat(exchangeCurrencyApiRestClient).isNotNull();
        assertThat(exchangeApiConfigProperties).isNotNull();
        assertThat(repository).isNotNull();

        assertThat(exchangeService.getLatestRate()).isEqualTo(1.19488d);
    }

    @Test
    public void testGetHistoricalRates() {

        String startDate = "2018-02-01";
        String endDate = "2018-02-06";

        assertThat(exchangeCurrencyApiRestClient).isNotNull();
        assertThat(exchangeApiConfigProperties).isNotNull();
        assertThat(repository).isNotNull();

        assertThat(exchangeService.getHistoricalRates(startDate, endDate).isEmpty()).isFalse();
        assertThat(exchangeService.getHistoricalRates(startDate, endDate).entrySet().size()).isEqualTo(6);
        assertThat(exchangeService.getHistoricalRates(startDate, endDate).get("2018-02-01")).isEqualTo(1.251405);
        assertThat(exchangeService.getHistoricalRates(startDate, endDate).get("2018-02-02")).isEqualTo(1.24641);
        assertThat(exchangeService.getHistoricalRates(startDate, endDate).get("2018-02-03")).isEqualTo(1.24641);
        assertThat(exchangeService.getHistoricalRates(startDate, endDate).get("2018-02-04")).isEqualTo(1.245015);
        assertThat(exchangeService.getHistoricalRates(startDate, endDate).get("2018-02-05")).isEqualTo(1.237006);
        assertThat(exchangeService.getHistoricalRates(startDate, endDate).get("2018-02-06")).isEqualTo(1.238391);
    }


}
