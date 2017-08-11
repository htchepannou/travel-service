package io.tchepannou.k.travel.client.response;

public class ProductDto {
    private Integer id;
    private String name;
    private String description;
    private Integer travelProviderId;
    private Integer originCityId;
    private Integer destinationCityId;
    private ProductTypeDto productType;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
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

    public Integer getTravelProviderId() {
        return travelProviderId;
    }

    public void setTravelProviderId(final Integer travelProviderId) {
        this.travelProviderId = travelProviderId;
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

    public ProductTypeDto getProductType() {
        return productType;
    }

    public void setProductType(final ProductTypeDto productType) {
        this.productType = productType;
    }
}
