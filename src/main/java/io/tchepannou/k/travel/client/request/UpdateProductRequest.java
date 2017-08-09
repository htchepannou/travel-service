
package io.tchepannou.k.travel.client.request;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "description",
    "originCityId",
    "destinationCityId"
})
public class UpdateProductRequest {

    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("originCityId")
    private Integer originCityId;
    @JsonProperty("destinationCityId")
    private Integer destinationCityId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(description).append(originCityId).append(destinationCityId).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UpdateProductRequest) == false) {
            return false;
        }
        UpdateProductRequest rhs = ((UpdateProductRequest) other);
        return new EqualsBuilder().append(name, rhs.name).append(description, rhs.description).append(originCityId, rhs.originCityId).append(destinationCityId, rhs.destinationCityId).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
