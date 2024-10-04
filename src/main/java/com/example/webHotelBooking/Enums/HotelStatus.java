package com.example.webHotelBooking.Enums;

import lombok.Getter;

@Getter
public enum HotelStatus {
    HETPHONG("HETPHONG"),CONPHONG("CONPHONG");

    private String message;
    HotelStatus(String message) {
        this.message=message;
    }
}
