package io.tchepannou.k.travel.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.tchepannou.k.travel.client.request.CreateAssetRequest;
import io.tchepannou.k.travel.client.request.UpdateAssetRequest;
import io.tchepannou.k.travel.client.response.CreateAssetResponse;
import io.tchepannou.k.travel.client.response.GetAssetResponse;
import io.tchepannou.k.travel.client.response.UpdateAssetResponse;
import io.tchepannou.k.travel.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/travel/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/travel/v1", description = "Assets")
public class AssetController {

    @Autowired
    private AssetService service;

    @RequestMapping(path = "/assets", method = RequestMethod.POST)
    @ApiOperation(value = "create", notes = "Create a new travel asset")
    public CreateAssetResponse create(@RequestBody CreateAssetRequest request){
        return service.create(request);
    }

    @RequestMapping(path = "/asset/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "update", notes = "Update a travel asset")
    public UpdateAssetResponse update(@PathVariable Integer id, @RequestBody UpdateAssetRequest request){
        return service.update(id, request);
    }

    @RequestMapping(path = "/asset/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "findById", notes = "Find a travel asset by ID")
    public GetAssetResponse findById(@PathVariable Integer id){
        return service.findById(id);
    }


}
