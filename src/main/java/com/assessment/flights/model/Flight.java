package com.assessment.flights.model;
import org.springframework.stereotype.Component;

import java.util.List;


/***
 * Flight object to store flight information
 */
@Component
public class Flight {


    private String flightNumber;
    private int revenue;
    private List<String> occupiedSeats;


    public Flight(String flightNumber, int revenue, List<String> occupiedSeats) {
        this.flightNumber = flightNumber;
        this.revenue = revenue;
        this.occupiedSeats = occupiedSeats;
    }

    public Flight() {
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public List<String> getOccupiedSeats() {
        return occupiedSeats;
    }

    public void setOccupiedSeats(List<String> occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
    }
}
