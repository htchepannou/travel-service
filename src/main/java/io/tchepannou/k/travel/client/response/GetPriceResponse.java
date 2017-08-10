package io.tchepannou.k.travel.client.response;

public class GetPriceResponse {
    private PriceDto price;

    public PriceDto getPrice() {
        return price;
    }

    public void setPrice(final PriceDto price) {
        this.price = price;
    }
}
