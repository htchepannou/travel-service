package io.tchepannou.k.travel.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.tchepannou.k.travel.client.request.CreateProductRequest;
import io.tchepannou.k.travel.client.response.CreateProductResponse;
import io.tchepannou.k.travel.client.request.ScheduledTimeRequest;
import io.tchepannou.k.travel.client.request.SetPriceRequest;
import io.tchepannou.k.travel.client.request.UpdateProductRequest;
import io.tchepannou.k.travel.client.response.GetProductPricesResponse;
import io.tchepannou.k.travel.client.response.GetProductResponse;
import io.tchepannou.k.travel.client.response.SetPriceResponse;
import io.tchepannou.k.travel.client.response.UpdateProductResponse;
import io.tchepannou.k.travel.service.PriceService;
import io.tchepannou.k.travel.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/travel/v1")
@Api(value = "/travel/v1", description = "Products")
public class ProductController {
    @Autowired ProductService productService;
    @Autowired PriceService priceService;


    /* ====== PRODUCT ========== */
    @RequestMapping(path = "/products", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "setPrice", notes = "Create a new product")
    public CreateProductResponse create(@RequestBody CreateProductRequest request){
        return productService.create(request);
    }

    @RequestMapping(path = "/product/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "update", notes = "Update a product")
    public UpdateProductResponse update(@PathVariable Integer id, @RequestBody UpdateProductRequest request){
        return productService.update(id, request);
    }

    @RequestMapping(path = "/product/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findById", notes = "Find a product by ID")
    public GetProductResponse findById(@PathVariable Integer id){
        return productService.findById(id);
    }

    /* ====== SCHEDULE ========== */
    @RequestMapping(path = "/product/{id}/schedule", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "schedule", notes = "Add/Update a schedule time")
    public void shedule(@PathVariable Integer id, @RequestBody @Valid ScheduledTimeRequest request) {
        productService.schedule(id, request);
    }


    @RequestMapping(path = "/product/{id}/schedule", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "unschedule", notes = "Add/Update a schedule time")
    public void unshedule(@PathVariable Integer id, @RequestBody @Valid ScheduledTimeRequest request) {
        productService.unschedule(id, request);
    }

    /* ====== PRICE ========== */
    @RequestMapping(path = "/product/{id}/prices/{priceType}", method = RequestMethod.POST)
    @ApiOperation(value = "setPrice", notes = "Create/Update a price")
    public SetPriceResponse setPrice (
            @PathVariable Integer id,
            @PathVariable String priceType,
            @RequestBody @Valid SetPriceRequest request) {
        return priceService.setPrice(id, priceType, request);
    }

    @RequestMapping(path = "/product/{id}/prices", method = RequestMethod.GET)
    @ApiOperation(value = "getPrices", notes = "Get Prices")
    public GetProductPricesResponse getPrices (@PathVariable Integer id) {
        return priceService.findByProductId(id);
    }

}
