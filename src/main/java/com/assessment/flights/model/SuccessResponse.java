package com.assessment.flights.model;

/***
 * SuccessResponse object to create Successful response entity
 */
public class SuccessResponse {

    private String status;

    public SuccessResponse(String status) {
        this.status = status;
    }

    public SuccessResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
