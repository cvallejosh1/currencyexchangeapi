package com.challenge.exchangeapi.domain;

import com.challenge.exchangeapi.util.LocalDateConverter;
import com.challenge.exchangeapi.util.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class ExchangeResult {

    @Id
    @GeneratedValue
    private Long id;
    private Double rate;

    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime creationDate;

    @Convert(converter = LocalDateConverter.class)
    private LocalDate exchangeDate;

    private String exchangeType;

    @Convert(converter = LocalDateConverter.class)
    private LocalDate fromDate;

    @Convert(converter = LocalDateConverter.class)
    private LocalDate toDate;

    private boolean historic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public boolean isHistoric() {
        return historic;
    }

    public void setHistoric(boolean historic) {
        this.historic = historic;
    }

    public LocalDate getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(LocalDate exchangeDate) {
        this.exchangeDate = exchangeDate;
    }


}

