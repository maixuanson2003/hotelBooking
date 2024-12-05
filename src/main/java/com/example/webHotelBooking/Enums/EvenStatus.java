package com.example.webHotelBooking.Enums;

import lombok.Getter;

@Getter
public enum EvenStatus {
    DAKETTHUC(" DAKETTHUC"),SAPDIENRA("SAPDIENRA");
    private String message;
    EvenStatus(String message) {
        this.message=message;
    }
}

