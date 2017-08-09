package io.tchepannou.k.travel.client.response;

import java.util.ArrayList;
import java.util.List;

public class SearchPriceResponse {
    private List<PriceDTO> prices = new ArrayList();

    public List<PriceDTO> getPrices() {
        return prices;
    }

    public void setPrices(final List<PriceDTO> prices) {
        this.prices = prices;
    }
}
