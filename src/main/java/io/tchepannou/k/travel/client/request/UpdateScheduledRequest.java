package io.tchepannou.k.travel.client.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class UpdateScheduledRequest {

    @NotNull
    private Date departureDateTime;

    private Date arrivalDateTime;

    @NotNull
    @Min(1)
    private Integer capacity;

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
