package io.tchepannou.k.travel.client.response;

import java.sql.Date;

public class PriceDto {
    private Integer id;
    private PriceTypeDto priceType;
    private Integer productId;
    private Double amount;
    private String currencyCode;
    private Date fromDate;
    private Date toDate;

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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(final Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(final Date toDate) {
        this.toDate = toDate;
    }
}
