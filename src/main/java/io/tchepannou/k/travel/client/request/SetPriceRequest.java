package io.tchepannou.k.travel.client.request;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class SetPriceRequest {
    @NotNull
    private Double amount;

    @NotNull
    private String currencyCode;

    private Date fromDateTime;

    private Date toDateTime;

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

    public Date getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(final Date fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public Date getToDateTime() {
        return toDateTime;
    }

    public void setToDateTime(final Date toDateTime) {
        this.toDateTime = toDateTime;
    }
}
