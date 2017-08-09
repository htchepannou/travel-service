
package io.tchepannou.k.travel.client.response;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ScheduledTime {

    private Integer dayOfWeek;
    private String departureTime;
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
        if ((other instanceof ScheduledTime) == false) {
            return false;
        }
        ScheduledTime rhs = ((ScheduledTime) other);
        return new EqualsBuilder().append(dayOfWeek, rhs.dayOfWeek).append(departureTime, rhs.departureTime).append(arrivalTime, rhs.arrivalTime).isEquals();
    }

}
