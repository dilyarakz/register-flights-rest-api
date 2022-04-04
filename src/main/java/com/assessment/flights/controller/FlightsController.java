package com.assessment.flights.controller;

import com.assessment.flights.model.*;
import com.assessment.flights.servers.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class FlightsController {

    @Autowired
    private FlightsService flightsService;

    @GetMapping("api/flights")
    @ResponseStatus(HttpStatus.OK)
    public  Dates getFlightDates(@RequestParam(required = false) String startDate,
                             @RequestParam(required = false) String endDate){
        return flightsService.getFlights(startDate, endDate);
    }

    @PostMapping("/api/tickets")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addTicket(@Valid @RequestBody Event event){
        return flightsService.addTicket(event);
    }




}
