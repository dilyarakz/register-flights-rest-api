package com.assessment.flights.model;

/***
 * Event object to store Ticket object
 */
public class Event {

    private Ticket event;

    public Event(Ticket event) {
        this.event = event;
    }

    public Event() {
    }

    public Ticket getEvent() {
        return event;
    }

    public void setEvent(Ticket event) {
        this.event = event;
    }
}
