package io.tchepannou.k.travel.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.tchepannou.k.travel.client.request.CreateScheduleRequest;
import io.tchepannou.k.travel.client.request.SearchPriceRequest;
import io.tchepannou.k.travel.client.request.UpdateScheduledRequest;
import io.tchepannou.k.travel.client.response.CreateScheduleResponse;
import io.tchepannou.k.travel.client.response.GetSchedulesReponse;
import io.tchepannou.k.travel.client.response.SearchScheduleResponse;
import io.tchepannou.k.travel.client.response.UpdateScheduleResponse;
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

    @RequestMapping(path = "/schedules", method = RequestMethod.POST)
    @ApiOperation(value = "create", notes = "Create a new schedule")
    public CreateScheduleResponse create(@RequestBody CreateScheduleRequest request){
        return create(request);
    }

    @RequestMapping(path = "/schedule/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "update", notes = "Update a schedule")
    public UpdateScheduleResponse update(@PathVariable Integer id, @RequestBody UpdateScheduledRequest request){
        return null;
    }

    @RequestMapping(path = "/schedule/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "findById", notes = "Find a schedule")
    public GetSchedulesReponse findById(@PathVariable Integer id){
        return null;
    }


    @RequestMapping(path = "/schedules/search", method = RequestMethod.POST)
    @ApiOperation(value = "search", notes = "Search schedules")
    public SearchScheduleResponse findById(@RequestBody @Valid SearchPriceRequest request){
        return null;
    }
}
