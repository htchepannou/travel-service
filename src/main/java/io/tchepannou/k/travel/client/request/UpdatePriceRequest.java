package io.tchepannou.k.travel.client.request;

import javax.validation.constraints.NotNull;

public class UpdatePriceRequest {
    @NotNull
    private Double amount;

    @NotNull
    private String currencyCode;

    private String fromDate;

    private String toDate;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(final String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(final String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(final String toDate) {
        this.toDate = toDate;
    }
}
