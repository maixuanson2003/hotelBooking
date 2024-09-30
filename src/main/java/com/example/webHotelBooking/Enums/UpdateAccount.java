package com.example.webHotelBooking.Enums;

import lombok.Getter;

@Getter
public enum UpdateAccount {
    SUCCESS("SUCCESS"),FAILED("FAILED");
    private String message;
    UpdateAccount(String message) {
        this.message=message;
    }
}
