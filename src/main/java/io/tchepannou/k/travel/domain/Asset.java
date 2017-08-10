package io.tchepannou.k.travel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "T_TRAVEL_ASSET")
public class Asset extends Persistent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="travel_asset_type_fk")
    private Integer assetTypeId;

    @Column(name="travel_provider_fk")
    private Integer travelProviderId;

    @Column(name="immatriculation_number")
    private String immatriculationNumber;
    private String model;
    private Integer year;
    private Integer capacity;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(final Integer assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    public Integer getTravelProviderId() {
        return travelProviderId;
    }

    public void setTravelProviderId(final Integer travelProviderId) {
        this.travelProviderId = travelProviderId;
    }

    public String getImmatriculationNumber() {
        return immatriculationNumber;
    }

    public void setImmatriculationNumber(final String immatriculationNumber) {
        this.immatriculationNumber = immatriculationNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(final Integer year) {
        this.year = year;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(final Integer capacity) {
        this.capacity = capacity;
    }
}
