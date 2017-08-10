package io.tchepannou.k.travel.client.response;

public class AssetDto {
    private Integer id;
    private AssetTypeDto assetType;
    private Integer travelProviderId;
    private String immatriculationNumber;
    private String model;
    private Integer year;
    private Integer capacity;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public AssetTypeDto getAssetType() {
        return assetType;
    }

    public void setAssetType(final AssetTypeDto assetType) {
        this.assetType = assetType;
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
