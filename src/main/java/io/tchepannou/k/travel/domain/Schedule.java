package io.tchepannou.k.travel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table( name = "T_SCHEDULED_TRANSPORTATION")
public class Schedule {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "travel_product_fk")
    private Integer productId;

    @Column(name="travel_asset_fk")
    private Integer assetId;

    @Column(name="departure_datetime")
    private Timestamp departureDateTime;

    @Column(name="arrival_datetime")
    private Timestamp arrivalDateTime;

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

    public Timestamp getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(final Timestamp departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public Timestamp getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(final Timestamp arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(final Integer capacity) {
        this.capacity = capacity;
    }
}
