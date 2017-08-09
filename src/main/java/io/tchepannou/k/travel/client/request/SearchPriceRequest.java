
package io.tchepannou.k.travel.client.request;

import javax.validation.constraints.NotNull;
import java.util.List;

public class SearchPriceRequest {
    @NotNull
    private List<Integer> productIds;

    @NotNull
    private List<String> priceTypeNames;

    @NotNull
    private String departureDate;

    public List<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(final List<Integer> productIds) {
        this.productIds = productIds;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(final String departureDate) {
        this.departureDate = departureDate;
    }

    public List<String> getPriceTypeNames() {
        return priceTypeNames;
    }

    public void setPriceTypeNames(final List<String> priceTypeNames) {
        this.priceTypeNames = priceTypeNames;
    }
}
