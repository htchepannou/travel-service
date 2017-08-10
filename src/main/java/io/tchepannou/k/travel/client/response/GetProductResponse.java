
package io.tchepannou.k.travel.client.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "product"
})
public class GetProductResponse {

    @JsonProperty("product")
    private ProductDto product;

    @JsonProperty("product")
    public ProductDto getProduct() {
        return product;
    }

    @JsonProperty("product")
    public void setProduct(final ProductDto product) {
        this.product = product;
    }
}
