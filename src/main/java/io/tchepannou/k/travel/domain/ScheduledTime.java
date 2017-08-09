package io.tchepannou.k.travel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Time;

@Entity
@Table( name = "T_SCHEDULED_TIME")
public class ScheduledTime extends Persistent{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "travel_product_fk")
    private Integer productId;

    @Column(name = "day_of_week")
    private Integer dayOfWeek;

    @Column(name = "departure_time")
    private Time departureTime;

    @Column(name = "arrival_time")
    private Time arrivalTime;

    @Override
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

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(final Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(final Time departureTime) {
        this.departureTime = departureTime;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(final Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
