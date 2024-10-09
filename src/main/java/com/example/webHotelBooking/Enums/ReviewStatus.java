package com.example.webHotelBooking.Enums;

import lombok.Getter;

@Getter
public enum ReviewStatus {
    HIDE("ẨN"),SHOW("HIỆN");
    private String message;
    ReviewStatus(String message) {
        this.message=message;
    }
}
