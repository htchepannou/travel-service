package io.tchepannou.k.travel.client.response;

import java.util.ArrayList;
import java.util.List;

public class SearchPriceResponse {
    private List<PriceDto> prices = new ArrayList();

    public List<PriceDto> getPrices() {
        return prices;
    }

    public void setPrices(final List<PriceDto> prices) {
        this.prices = prices;
    }
}
