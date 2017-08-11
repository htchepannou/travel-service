package io.tchepannou.k.travel.client.response;

import java.util.Date;

public class PriceDto {
    private Integer id;
    private PriceTypeDto priceType;
    private Integer productId;
    private Double amount;
    private String currencyCode;
    private Date fromDateTime;
    private Date toDateTime;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public PriceTypeDto getPriceType() {
        return priceType;
    }

    public void setPriceType(final PriceTypeDto priceType) {
        this.priceType = priceType;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(final Integer productId) {
        this.productId = productId;
    }

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
