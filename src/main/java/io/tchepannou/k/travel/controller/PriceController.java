package io.tchepannou.k.travel.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.tchepannou.k.travel.client.request.SearchPriceRequest;
import io.tchepannou.k.travel.client.response.GetPriceResponse;
import io.tchepannou.k.travel.client.response.SearchPriceResponse;
import io.tchepannou.k.travel.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value="/travel/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/travel/v1", description = "Pricing")
public class PriceController {

    @Autowired PriceService priceService;

    @RequestMapping(path = "/price/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "findById", notes = "Find a price by ID")
    public GetPriceResponse findById(@PathVariable Integer id){
        return priceService.findById(id);
    }

    @RequestMapping(path = "/prices/search", method = RequestMethod.POST)
    @ApiOperation(value = "search", notes = "Search prices")
    public SearchPriceResponse search (@RequestBody @Valid SearchPriceRequest request){
        return priceService.search(request);
    }
}
