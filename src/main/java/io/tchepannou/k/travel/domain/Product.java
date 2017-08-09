package io.tchepannou.k.travel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "T_TRAVEL_PRODUCT")
public class Product extends Persistent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="travel_provider_fk")
    private Integer travelProdiverId;

    @Column(name="travel_product_type_fk")
    private Integer travelProductTypeId;

    @Column(name="origin_city_fk")
    private Integer originCityId;

    @Column(name="destination_city_fk")
    private Integer destinationCityId;

    private String name;
    private String description;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getTravelProdiverId() {
        return travelProdiverId;
    }

    public void setTravelProdiverId(final Integer travelProdiverId) {
        this.travelProdiverId = travelProdiverId;
    }

    public Integer getTravelProductTypeId() {
        return travelProductTypeId;
    }

    public void setTravelProductTypeId(final Integer travelProductTypeId) {
        this.travelProductTypeId = travelProductTypeId;
    }

    public Integer getOriginCityId() {
        return originCityId;
    }

    public void setOriginCityId(final Integer originCityId) {
        this.originCityId = originCityId;
    }

    public Integer getDestinationCityId() {
        return destinationCityId;
    }

    public void setDestinationCityId(final Integer destinationCityId) {
        this.destinationCityId = destinationCityId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
