package io.tchepannou.k.travel.client.response;

import java.util.ArrayList;
import java.util.List;

public class SearchPriceResponse {
    private List<Price> prices = new ArrayList();

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(final List<Price> prices) {
        this.prices = prices;
    }
}
