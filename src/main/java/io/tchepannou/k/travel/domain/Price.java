package io.tchepannou.k.travel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table( name = "T_PRICE")
public class Price extends Persistent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="price_type_fk")
    private Integer priceTypeId;

    @Column(name="travel_product_fk")
    private Integer productId;

    private Double amount;

    @Column(name="currency_code")
    private String currencyCode;

    @Column(name="from_date")
    private Date fromDate;

    @Column(name="to_date")
    private Date toDate;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getPriceTypeId() {
        return priceTypeId;
    }

    public void setPriceTypeId(final Integer priceTypeId) {
        this.priceTypeId = priceTypeId;
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
