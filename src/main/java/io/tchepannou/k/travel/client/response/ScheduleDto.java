package io.tchepannou.k.travel.client.response;

import java.util.Date;

public class ScheduleDto {
    private Integer id;
    private Integer productId;
    private Integer assetId;
    private Date departureDateTime;
    private Date arrivalDateTime;
    private Integer capacity;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(final Integer productId) {
        this.productId = productId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(final Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(final Integer capacity) {
        this.capacity = capacity;
    }

    public Date getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(final Date departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public Date getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(final Date arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }
}
