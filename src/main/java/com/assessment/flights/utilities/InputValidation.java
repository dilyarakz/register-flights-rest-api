package com.assessment.flights.utilities;

import com.assessment.flights.exceptions.CustomIllegalArgumentException;
import com.assessment.flights.model.Event;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/***
 * Helper InputValidation class
 */
public class InputValidation {

    /***
     * Checks if event object is empty
     * @param event
     * @return boolean
     * @throws  CustomIllegalArgumentException if event object is empty
     */
    public static boolean isEventValid(Event event){
        if (event == null || event.getEvent() == null){
            throw new CustomIllegalArgumentException("Event object can not be empty");
        }
        return true;
    }

    /***
     * Check if ticket id input is valid
     * @param ticketId
     * @return
     */
    public static boolean isTicketIdValid(int ticketId){
        if (ticketId == 0){
            throw new CustomIllegalArgumentException("Ticket Id can not be empty or zero");
        }
        return true;
    }

    /***
     * Check flight date is empty, if it is not empty and format is valid ("yyyy-MM-dd") returns true
     * @param variableName
     * @param date
     * @return LocalDate object
     * @throws CustomIllegalArgumentException if the date is invalid format or empty
     */
    public static LocalDate isFlightDateValid(String variableName, String date){
        LocalDate fDate;
        if (date == null || date.isBlank() || date.isEmpty()){
            throw new CustomIllegalArgumentException(variableName + " is empty");
        }
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            fDate = LocalDate.parse(date, formatter);
        }catch(Exception e){
            throw new CustomIllegalArgumentException(variableName + " format is invalid");
        }
        return fDate;
    }

    /***
     * Check flight date is empty, if it is not empty and format is valid ("yyyy-MM-dd") returns true
     * @param date
     * @return boolean
     */
    public static boolean isFlightDateValid(String date){
        LocalDate fDate;
        if (date == null || date.isBlank() || date.isEmpty()){
            throw new CustomIllegalArgumentException("date can not be empty");
        }
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            fDate = LocalDate.parse(date, formatter);
        }catch(Exception e){
            throw new CustomIllegalArgumentException("date format is invalid");
        }
        return true;
    }

    /***
     * Check if path variable dates are valid format
     * @param startDate
     * @param endDate
     * @return boolean
     * @throws CustomIllegalArgumentException if the endDate is before startDate
     */
    public static boolean isFlightDateRangeValid(LocalDate startDate, LocalDate endDate){
        if (startDate.isAfter(endDate)){
            throw new CustomIllegalArgumentException("endDate cannot be before startDate");
        }
        return true;
    }

    /***
     * Check if Flight number or Seat number is empty and is alphanumeric
     * @param flightNumber
     * @return boolean
     * @throws CustomIllegalArgumentException if the number is empty or is not alphanumeric
     */
    public static boolean isFlightNumberSeatNumberValid(String variableName, String flightNumber){
        if (flightNumber == null || flightNumber.isBlank() || flightNumber.isEmpty()){
            throw new CustomIllegalArgumentException(variableName + " can not be empty");
        }else if (!(flightNumber != null && flightNumber.matches("^[a-zA-Z0-9]*$"))){
            throw new CustomIllegalArgumentException(variableName + " must be alphanumeric");
        }
        return true;
    }

    /***
     * Check if ticket cost is not below zero, if above zero returns true
     * @param cost
     * @return boolean
     * @throws CustomIllegalArgumentException if the cost is below zero
     */
    public static boolean isTicketCostValid(int cost){
        if (cost <= 0){
            throw new CustomIllegalArgumentException("Flight cost must be more than 0");
        }
        return true;
    }


}
