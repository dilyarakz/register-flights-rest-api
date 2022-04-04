package com.assessment.flights.servers;

import com.assessment.flights.exceptions.CustomIllegalArgumentException;
import com.assessment.flights.model.*;
import com.assessment.flights.utilities.InputValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class FlightsService {

    //Map to store tickets
    private Map <Integer, Ticket> tickets = new HashMap <Integer, Ticket>();

    //<Flight number, Date of flight>
    private Map <String, String> flightDates = new HashMap <String, String>();

    //<Date, List of Flight Numbers>
    private Map <String, ArrayList<String>> dateFlights = new HashMap<String, ArrayList<String>>();

    //<Flight number, Flight object>
    private Map <String, Flight> flightsData = new HashMap<String, Flight>();

    /***
     * Adds Ticket to system
     * @param event
     * @return ResponseEntity
     * @throws CustomIllegalArgumentException if ticket object is invalid, event object is empty,
     * ticketId already exists, seatNumber already taken
     */
    public ResponseEntity<Object> addTicket(Event event){

        InputValidation.isEventValid(event);
        Ticket ticket = event.getEvent();

        String fnum = ticket.getFlightNumber();
        String ticketDate = ticket.getFlightDate();
        int ticketId = ticket.getTicketId();
        String seatNumber= ticket.getSeatNumber();
        int ticketCost = ticket.getTicketCost();

        if (tickets.containsKey((ticketId))){
            throw new CustomIllegalArgumentException("ticketId already exists");
        }
        tickets.put(ticketId, ticket);

        if (flightDates.containsKey(fnum)){

            //If the date is the same
            if(flightDates.get(fnum).equalsIgnoreCase(ticketDate)){
                if (flightsData.get(fnum).getOccupiedSeats().contains(seatNumber)){
                    tickets.remove(ticketId);
                    throw new CustomIllegalArgumentException("seatNumber already taken");
                }
                if (flightsData.get(fnum).getOccupiedSeats().size() >= 300 ){
                    throw new CustomIllegalArgumentException("Flight number " + fnum + " is full!");
                }

                int rev = flightsData.get(fnum).getRevenue() + ticketCost;
                List<String> seats = flightsData.get(fnum).getOccupiedSeats();

                seats.add(seatNumber);
                Collections.sort(seats);
                flightsData.put(fnum, new Flight(fnum, rev, seats));
            }else{ //If the date changed update dates and reference
                if (flightsData.get(fnum).getOccupiedSeats().contains(seatNumber)){
                    tickets.remove(ticketId);
                    throw new CustomIllegalArgumentException("seatNumber already taken");
                }

                String oldDate = flightDates.get(fnum);
                String newDate = ticket.getFlightDate();

                //Updates flight date
                flightDates.put(fnum, newDate);

                ArrayList <String> flightNumbersList = dateFlights.get(oldDate);

                dateFlights.remove(oldDate);

                dateFlights.put(newDate, flightNumbersList);

                //Update the flights Data with new revenue and seats
                int revenue = flightsData.get(fnum).getRevenue() + ticketCost;
                List<String> seats = flightsData.get(fnum).getOccupiedSeats();
                seats.add(seatNumber);
                Collections.sort(seats);
                flightsData.put(fnum, new Flight(fnum, revenue, seats));
            }
        }else{//If the flight number is new, add to the flight date, date flight and flights data

            flightDates.put(fnum, ticketDate);

            //Check if the date exists in data flight
            if (dateFlights.containsKey(ticketDate)){
                dateFlights.get(ticketDate).add(fnum);
                List<String> seats = new ArrayList<String>();
                seats.add(seatNumber);
                Collections.sort(seats);
                flightsData.put(fnum, new Flight(fnum, ticketCost, seats));
            }else{ //If the date does not exists add new date
                ArrayList<String> flightNumbers = new ArrayList<String>();

                flightNumbers.add(fnum);

                dateFlights.put(ticketDate, flightNumbers);

                List<String> seats = new ArrayList<String>();

                seats.add(seatNumber);

                flightsData.put(fnum, new Flight(fnum, ticketCost, seats));
            }
        }

        SuccessResponse success = new SuccessResponse("success");

        return new ResponseEntity(success, HttpStatus.OK);
    }

    /***
     * Method provides information on flights within specified range
     * @param startDate
     * @param endDate
     * @return Dates object
     * @throws CustomIllegalArgumentException if the startDate and endDate are empty or invalid format
     */
    public Dates getFlights(String startDate, String endDate){

        LocalDate fStartDate = InputValidation.isFlightDateValid("startDate", startDate);
        LocalDate fEndDate = InputValidation.isFlightDateValid("endDate", endDate);

        InputValidation.isFlightDateRangeValid(fStartDate, fEndDate);

        Dates dates = new Dates();
        List <FlightDate> flightDatesList = new ArrayList<FlightDate>();

        for(LocalDate i = fStartDate; !i.equals(fEndDate.plusDays(1)); i=i.plusDays(1)){
            //Array of flights
            ArrayList<Flight> finalFlights = new ArrayList<Flight>();

            //Object of Flight date that has a date and a list of flights
            FlightDate flightDate = new FlightDate();

            //Set the date object to Flight date
            flightDate.setDate(i.toString());

            //Check if current date is in dateFlights Map
            if (dateFlights.containsKey(i.toString())){
                //Get the date key and access the list of flight numbers
                //Add to the finalFlights Flights objects by mapping using flight number to flightsData Map
                dateFlights.get(i.toString())
                        .forEach(v -> finalFlights
                                .add(flightsData.get(v)));
            }

            //Set to object flightDate List of Flights
            flightDate.setFlights(finalFlights);

            //Add to flightDatesList
            flightDatesList.add(flightDate);

        }

        dates.setDates(flightDatesList);
        return dates;
    }
}
