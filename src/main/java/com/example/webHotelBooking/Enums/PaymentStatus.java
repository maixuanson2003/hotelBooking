package com.example.webHotelBooking.Enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    SUCCESS("SUCCESS"),REFUND("REFUND");

    private String message;
    PaymentStatus(String message) {
        this.message=message;
    }
}
