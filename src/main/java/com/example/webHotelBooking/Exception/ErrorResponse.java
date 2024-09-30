package com.example.webHotelBooking.Exception;

public class ErrorResponse {
    private String message;
    private int statusCode;

    public ErrorResponse(String message) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
