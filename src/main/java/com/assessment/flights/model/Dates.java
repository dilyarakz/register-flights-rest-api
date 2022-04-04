package com.assessment.flights.model;

import java.util.List;

/***
 * Dates object to store FlightDate objects
 */
public class Dates {

    private List <FlightDate> dates;

    public Dates(List<FlightDate> dates) {
        this.dates = dates;
    }

    public Dates() {
    }

    public List<FlightDate> getDates() {
        return dates;
    }

    public void setDates(List<FlightDate> dates) {
        this.dates = dates;
    }
}
