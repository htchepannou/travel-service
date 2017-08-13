
package io.tchepannou.k.travel.client.request;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class SearchScheduleRequest {
    @NotNull
    private Date departureDateTime;

    @NotNull
    private Integer originCityId;

    @NotNull
    private Integer destinationCityId;

    private Integer travelProviderId;

    private String assetTypeName;

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

    public Integer getTravelProviderId() {
        return travelProviderId;
    }

    public void setTravelProviderId(final Integer travelProviderId) {
        this.travelProviderId = travelProviderId;
    }

    public Date getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(final Date departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public String getAssetTypeName() {
        return assetTypeName;
    }

    public void setAssetTypeName(final String assetTypeName) {
        this.assetTypeName = assetTypeName;
    }
}
