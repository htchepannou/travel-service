
package io.tchepannou.k.travel.client.request;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ScheduledTimeRequest {

    @NotNull
    @Min(value = 0)
    @Max(6)
    private Integer dayOfWeek;

    @NotNull
    private String departureTime;

    @NotNull
    private String arrivalTime;

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(dayOfWeek).append(departureTime).append(arrivalTime).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ScheduledTimeRequest) == false) {
            return false;
        }
        ScheduledTimeRequest rhs = ((ScheduledTimeRequest) other);
        return new EqualsBuilder().append(dayOfWeek, rhs.dayOfWeek).append(departureTime, rhs.departureTime).append(arrivalTime, rhs.arrivalTime).isEquals();
    }
}
