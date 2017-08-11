package io.tchepannou.k.travel.client.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class CreateScheduleRequest {
    @NotNull
    private Integer productId;

    @NotNull
    private Integer assetId;

    @NotNull
    private Date departureDateTime;

    private Date arrivalDateTime;

    @NotNull
    @Min(1)
    private Integer capacity;

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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(final Integer capacity) {
        this.capacity = capacity;
    }
}
