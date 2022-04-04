package com.assessment.flights.model;

import com.assessment.flights.utilities.InputValidation;


/***
 * Ticket object
 */
public class Ticket {

    private int ticketId;
    private String flightDate;
    private String flightNumber;
    private String seatNumber;
    private int ticketCost;


    public Ticket(int ticketId, String flightDate, String flightNumber, String seatNumber, int ticketCost){
        if (InputValidation.isTicketIdValid(ticketId)) this.ticketId = ticketId;
        if (InputValidation.isFlightDateValid(flightDate)) this.flightDate = flightDate;
        if (InputValidation.isFlightNumberSeatNumberValid("Flight Number", flightNumber)) this.flightNumber = flightNumber;
        if (InputValidation.isFlightNumberSeatNumberValid("Seat Number",seatNumber)) this.seatNumber = seatNumber;
        if (InputValidation.isTicketCostValid(ticketCost)) this.ticketCost = ticketCost;
    }

    public Ticket() {
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        if (InputValidation.isTicketIdValid(ticketId)) this.ticketId = ticketId;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate){
        if (InputValidation.isFlightDateValid(flightDate)) this.flightDate = flightDate;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        if (InputValidation.isFlightNumberSeatNumberValid("Flight number", flightNumber)) this.flightNumber = flightNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        if (InputValidation.isFlightNumberSeatNumberValid("Seat number",seatNumber)) this.seatNumber = seatNumber;
    }

    public int getTicketCost() {
        return ticketCost;
    }

    public void setTicketCost(int ticketCost) {
        if (InputValidation.isTicketCostValid(ticketCost)) this.ticketCost = ticketCost;
    }
}
