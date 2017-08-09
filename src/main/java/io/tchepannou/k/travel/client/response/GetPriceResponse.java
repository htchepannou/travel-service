package io.tchepannou.k.travel.client.response;

public class GetPriceResponse {
    private PriceDTO price;

    public PriceDTO getPrice() {
        return price;
    }

    public void setPrice(final PriceDTO price) {
        this.price = price;
    }
}
