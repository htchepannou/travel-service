
package io.tchepannou.k.travel.client.request;

import javax.validation.constraints.NotNull;

public class CreateProductRequest {

    @NotNull
    private String name;

    @NotNull
    private String productTypeName;

    @NotNull
    private Integer travelProviderId;

    private String description;
    private Integer originCityId;
    private Integer destinationCityId;

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

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(final String productTypeName) {
        this.productTypeName = productTypeName;
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
}
