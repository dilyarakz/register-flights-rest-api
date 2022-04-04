package com.assessment.flights.exceptions;

/***
 * Custom exception Response
 */
public class CustomIllegalArgumentExceptionResponse {
    private String status;
    private String reason;

    public CustomIllegalArgumentExceptionResponse(String status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
