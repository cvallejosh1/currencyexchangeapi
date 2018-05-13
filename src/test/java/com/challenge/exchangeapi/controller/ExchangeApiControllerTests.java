package com.challenge.exchangeapi.controller;

import com.challenge.exchangeapi.service.ExchangeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ExchangeApiController.class)
public class ExchangeApiControllerTests {


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ExchangeService exchangeService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetLatestRate() throws Exception {
        Double rate = 1.19488d;

        assertThat(this.exchangeService).isNotNull();
        Mockito.when(exchangeService.getLatestRate()).thenReturn(rate);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/exchangeapi/rate/latest"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();


        assertThat(new ObjectMapper().readValue(result.getResponse().getContentAsString(), Double.class)).isEqualTo(rate);
    }

    @Test
    public void testGetHistoricalRate() throws Exception {

        Map<String,Double> mockHistoricRates = new HashMap<>();
        mockHistoricRates.put("2018-02-01", 1.251405);
        mockHistoricRates.put("2018-02-02", 1.24641);
        mockHistoricRates.put("2018-02-03", 1.24641);
        mockHistoricRates.put("2018-02-04", 1.245015);
        mockHistoricRates.put("2018-02-05", 1.237006);
        mockHistoricRates.put("2018-02-06", 1.238391);

        assertThat(this.exchangeService).isNotNull();
        Mockito.when(exchangeService.getHistoricalRates(Matchers.anyString(), Matchers.anyString())).thenReturn(mockHistoricRates);

        String startDate = "2018-02-01";
        String endDate = "2018-02-06";
        String url = "/exchangeapi/rate/historic?startDate=" + startDate + "&endDate=" + endDate;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();

        Map<String,Double> historicRates = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Map.class);

        assertThat(historicRates.isEmpty()).isFalse();
        assertThat(historicRates.entrySet().size()).isEqualTo(6);
        assertThat(historicRates.get("2018-02-01")).isEqualTo(1.251405);
        assertThat(historicRates.get("2018-02-02")).isEqualTo(1.24641);
        assertThat(historicRates.get("2018-02-03")).isEqualTo(1.24641);
        assertThat(historicRates.get("2018-02-04")).isEqualTo(1.245015);
        assertThat(historicRates.get("2018-02-05")).isEqualTo(1.237006);
        assertThat(historicRates.get("2018-02-06")).isEqualTo(1.238391);

    }

}
