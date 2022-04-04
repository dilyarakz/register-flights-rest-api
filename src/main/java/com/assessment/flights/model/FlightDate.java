package com.assessment.flights.model;

import java.util.Date;
import java.util.List;

/***
 * FlightDate object to store the date and the list of Flight objects
 */
public class FlightDate {

    private String date;
    private List<Flight> flights;

    public FlightDate(Date String, List<Flight> flights) {
        this.date = date;
        this.flights = flights;
    }

    public FlightDate() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
}
