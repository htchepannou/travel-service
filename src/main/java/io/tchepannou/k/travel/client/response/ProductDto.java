package io.tchepannou.k.travel.client.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("travelProviderId")
    private Integer travelProviderId;
    @JsonProperty("originCityId")
    private Integer originCityId;
    @JsonProperty("destinationCityId")
    private Integer destinationCityId;
    @JsonProperty("productType")
    private ProductTypeDto productType;
    @JsonProperty("schedule")
    private List<ScheduledTime> schedule = new ArrayList<>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("travelProviderId")
    public Integer getTravelProviderId() {
        return travelProviderId;
    }

    @JsonProperty("travelProviderId")
    public void setTravelProviderId(Integer travelProviderId) {
        this.travelProviderId = travelProviderId;
    }

    @JsonProperty("originCityId")
    public Integer getOriginCityId() {
        return originCityId;
    }

    @JsonProperty("originCityId")
    public void setOriginCityId(Integer originCityId) {
        this.originCityId = originCityId;
    }

    @JsonProperty("destinationCityId")
    public Integer getDestinationCityId() {
        return destinationCityId;
    }

    @JsonProperty("destinationCityId")
    public void setDestinationCityId(Integer destinationCityId) {
        this.destinationCityId = destinationCityId;
    }

    @JsonProperty("productType")
    public ProductTypeDto getProductType() {
        return productType;
    }

    @JsonProperty("productType")
    public void setProductType(ProductTypeDto productType) {
        this.productType = productType;
    }

    @JsonProperty("schedule")
    public List<ScheduledTime> getSchedule() {
        return schedule;
    }

    @JsonProperty("schedule")
    public void setSchedule(List<ScheduledTime> schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(name).append(description).append(travelProviderId).append(originCityId).append(destinationCityId).append(productType).append(
                schedule).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ProductDto) == false) {
            return false;
        }
        ProductDto rhs = ((ProductDto) other);
        return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(description, rhs.description).append(travelProviderId, rhs.travelProviderId).append(originCityId, rhs.originCityId).append(destinationCityId, rhs.destinationCityId).append(productType, rhs.productType).append(
                schedule, rhs.schedule).isEquals();
    }
}
