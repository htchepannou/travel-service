
package io.tchepannou.k.travel.client.request;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class SearchScheduleRequest {
    @NotNull
    private List<Integer> assetIds;

    @NotNull
    private Integer travelProviderId;

    @NotNull
    private Integer productId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    public List<Integer> getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(final List<Integer> assetIds) {
        this.assetIds = assetIds;
    }

    public Integer getTravelProviderId() {
        return travelProviderId;
    }

    public void setTravelProviderId(final Integer travelProviderId) {
        this.travelProviderId = travelProviderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(final Integer productId) {
        this.productId = productId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }
}
