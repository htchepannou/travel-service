package io.tchepannou.k.travel.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.tchepannou.k.travel.client.request.CreateScheduleRequest;
import io.tchepannou.k.travel.client.request.SearchScheduleRequest;
import io.tchepannou.k.travel.client.request.UpdateScheduledRequest;
import io.tchepannou.k.travel.client.response.CreateScheduleResponse;
import io.tchepannou.k.travel.client.response.GetScheduleReponse;
import io.tchepannou.k.travel.client.response.SearchScheduleResponse;
import io.tchepannou.k.travel.client.response.UpdateScheduleResponse;
import io.tchepannou.k.travel.service.ScheduleService;
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
@Api(value = "/travel/v1", description = "Travel Scheduled")
public class ScheduleController {

    @Autowired
    private ScheduleService service;

    @RequestMapping(path = "/schedules", method = RequestMethod.POST)
    @ApiOperation(value = "create", notes = "Create a new schedule")
    public CreateScheduleResponse create(@RequestBody CreateScheduleRequest request){
        return service.create(request);
    }

    @RequestMapping(path = "/schedule/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "update", notes = "Update a schedule")
    public UpdateScheduleResponse update(@PathVariable Integer id, @RequestBody UpdateScheduledRequest request){
        return service.update(id, request);
    }

    @RequestMapping(path = "/schedule/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "findById", notes = "Find a schedule")
    public GetScheduleReponse findById(@PathVariable Integer id){
        return service.findById(id);
    }


    @RequestMapping(path = "/schedules/search", method = RequestMethod.POST)
    @ApiOperation(value = "search", notes = "Search schedules")
    public SearchScheduleResponse search(@RequestBody @Valid SearchScheduleRequest request){
        return service.search(request);
    }
}
